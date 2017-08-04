package com.tonykazanjian.codenamescompanion.timer;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.UserPreferences;

import java.util.concurrent.TimeUnit;

import static com.tonykazanjian.codenamescompanion.timer.TimerService.sIsFinished;
import static com.tonykazanjian.codenamescompanion.timer.TimerService.sIsStarted;
import static com.tonykazanjian.codenamescompanion.timer.TimerService.sIsTicking;

/**
 * @author Tony Kazanjian
 */

public class TimerActivity extends AppCompatActivity implements TimerView {

    public static final String EXTRA_REBIND_SERVICE = "EXTRA_REBIND_SERVICE";
    private static final String EXTRA_TIME_LEFT = "EXTRA_TIME_LEFT";

    TextView mTimerText;
    TimerPresenter mTimerPresenter;
    ImageButton mStartPauseButton;
    ImageButton mResetButton;
    ProgressWheel mTimerProgress;

    TimerService mTimerService;
    TimerTickReceiver mTimerTickReceiver;
    TimerFinishedReceiver mTimerFinishedReceiver;

    long mTimeLeft;

    //TODO - see if we need to rebind
    private boolean mIsTimeServiceBound = false;
    private boolean mRebindingService = false;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TimerService.TimerBinder binder = (TimerService.TimerBinder) iBinder;
            mTimerService = binder.getService();
            Log.i(this.getClass().getCanonicalName(), "service connected");

            if (mRebindingService) {
                onRebindService();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mTimerService = null;
        }
    };

    private void onRebindService() {
        if (mIsTimeServiceBound && mTimerService != null) {

            // this check needs to be here to ensure the play/pause state is restored when you have
            // the app waiting in recents/multitasking, and you use the service to toggle the play/pause state
            // e.g. Start playing the class with the app open, then hit multitasking. While multitasking is still open,
            // pull down the notification shade and pause the class. Now touch the app in multitasking, and resume it.
            // The pause state should be showing in the app UI.
            if (!sIsTicking) {
                mTimerProgress.setInstantProgress(mTimeLeft/ (float) UserPreferences.getBaseTime(this));
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTimerText = (TextView)findViewById(R.id.timer_text);
        mStartPauseButton = (ImageButton) findViewById(R.id.start_pause_btn);
        mResetButton = (ImageButton) findViewById(R.id.reset_btn);
        mTimerProgress = (ProgressWheel) findViewById(R.id.timer_progress);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mRebindingService = getIntent().getExtras().getBoolean(EXTRA_REBIND_SERVICE, false);


            if (extras.getBoolean(TimerService.EXTRA_IS_UI_PAUSED, false)) {
                // need to set the progress wheel
                mTimeLeft = extras.getLong(TimerService.TIME_LEFT_EXTRA, 0);
                mTimerProgress.setInstantProgress(mTimeLeft / (float) UserPreferences.getBaseTime(this));
                mTimerText.setText(TimerService.getTimerTextFormat(mTimeLeft));
                sIsTicking = false;

                setButtonDrawable();
            } else {
                sIsTicking = true;
                setButtonDrawable();
            }
        }

        mTimerPresenter = new TimerPresenter(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TimerService.class);
        if (!mIsTimeServiceBound) {
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            mIsTimeServiceBound = true;
        }
        Log.i(this.getClass().getCanonicalName(), "Service is bound");;
    }

    @Override
    protected void onResume(){
        super.onResume();
        mTimerTickReceiver = new TimerTickReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mTimerTickReceiver, new IntentFilter(TimerService.TIMER_BROADCAST_EVENT));
        mTimerFinishedReceiver = new TimerFinishedReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mTimerFinishedReceiver, new IntentFilter(TimerService.TIMER_FINISHED_INTENT_FILTER));

        mTimerPresenter.setTimer();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mTimerTickReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mTimerTickReceiver);
        }
        if (mTimerFinishedReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mTimerFinishedReceiver);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
        mIsTimeServiceBound = false;
        mRebindingService = true;
        Log.i(this.getClass().getSimpleName(), "Service is unbound");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTimerSet() {
        mStartPauseButton.setImageDrawable(getDrawable(R.drawable.ic_start_timer));
        mTimerProgress.setLinearProgress(true);
        if (!sIsStarted) {
            setTimerText(UserPreferences.getBaseTime(this));
            // setting up timer progress when activity is built
            mTimerProgress.setInstantProgress(1);
        }
        else {
            setTimerText(mTimeLeft);
            mTimerProgress.setInstantProgress(mTimeLeft/ (float) UserPreferences.getBaseTime(this));
        }

        mStartPauseButton.setOnClickListener(new StartPauseClickListener());
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimerPresenter.resetTimer();
                mTimerProgress.setInstantProgress(1);
            }
        });
    }

    @Override
    public void onTimerStarted() {
        sIsTicking = true;
        sIsStarted = true;
        setButtonDrawable();
        Intent timerIntent = new Intent(this, TimerService.class);
        timerIntent.setAction(TimerService.ACTION_START);
        startService(timerIntent);
    }

    @Override
    public void onTimerResumed() {
        setButtonDrawable();
        Intent pauseIntent = new Intent(this, TimerService.class);
        pauseIntent.setAction(TimerService.ACTION_RESUME);
        startService(pauseIntent);
    }

    @Override
    public void onTimerPaused() {
        Intent pauseIntent = new Intent(this, TimerService.class);
        pauseIntent.setAction(TimerService.ACTION_PAUSE);
        startService(pauseIntent);
        sIsTicking = false;
        setButtonDrawable();
    }

    @Override
    public void onTimerReset() {
        mTimerPresenter.setTimer();
        Intent resetIntent = new Intent(this, TimerService.class);
        resetIntent.setAction(TimerService.ACTION_RESET);
        startService(resetIntent);
        setButtonDrawable();
    }

    private void setButtonDrawable() {
        Drawable start = getDrawable(R.drawable.ic_start_timer);
        Drawable pause = getDrawable(R.drawable.ic_pause_24dp);
        if (sIsTicking) {
            mStartPauseButton.setImageDrawable(pause);
        } else {
            mStartPauseButton.setImageDrawable(start);
        }
    }


    private class StartPauseClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (!sIsFinished) {
                if (!sIsStarted) {
                    mTimerPresenter.startTimer();
                } else if (!sIsTicking) {
                    mTimerPresenter.resumeTimer();
                } else {
                    mTimerPresenter.pauseTimer();
                }
            } else {
                mTimerPresenter.resumeTimer();
                sIsFinished = false;
            }

        }
    }

    private void setTimerText(long timeLeft){
        mTimerText.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(timeLeft) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeLeft)),
                TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft))));
    }

    private class TimerTickReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(TimerService.EXTRA_TIMER_BROADCAST_MSG);

            switch (message) {
                case TimerService.TIMER_TICK_BROADCAST_MSG:
                    mTimeLeft = intent.getLongExtra(TimerService.EXTRA_TIME_UNTIL_FINISHED, 0);
                    mTimerProgress.setInstantProgress(mTimeLeft / (float) UserPreferences.getBaseTime(context));
                    setTimerText(mTimeLeft -1);
                case TimerService.NOTIFICATION_PLAY_MSG:
                    setButtonDrawable();
                    break;
                case TimerService.NOTIFICATION_PAUSE_MSG:
                    setButtonDrawable();
                    break;
                case TimerService.NOTIFICATION_RESET_MSG:
                    mTimerPresenter.setTimer();
                    Intent resetIntent = new Intent(TimerService.RESET_TIMER_INTENT_FILTER);
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(resetIntent);
                    break;
            }
        }
    }

    private class TimerFinishedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mTimerProgress.setProgress(0);
            mStartPauseButton.setOnClickListener(null);
        }
    }
}
