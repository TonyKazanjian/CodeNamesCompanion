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
//    private boolean mIsTimeServiceBound = false;
//    private boolean mRebindingService = false;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TimerService.TimerBinder binder = (TimerService.TimerBinder) iBinder;
            mTimerService = binder.getService();
            Log.i(this.getClass().getCanonicalName(), "service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mTimerService = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTimerText = (TextView)findViewById(R.id.timer_text);
        mStartPauseButton = (ImageButton) findViewById(R.id.start_pause_btn);
        mResetButton = (ImageButton) findViewById(R.id.reset_btn);
        mTimerProgress = (ProgressWheel) findViewById(R.id.timer_progress);

//        mRebindingService = getIntent().getExtras().getBoolean(EXTRA_REBIND_SERVICE, false);

        mTimerPresenter = new TimerPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TimerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        Log.i(this.getClass().getCanonicalName(), "Service is bound");;
    }

    @Override
    protected void onResume(){
        super.onResume();
        mTimerTickReceiver = new TimerTickReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mTimerTickReceiver, new IntentFilter(TimerService.TIMER_BROADCAST_EVENT));
        mTimerFinishedReceiver = new TimerFinishedReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mTimerFinishedReceiver, new IntentFilter(TimerService.TIMER_FINISHED_INTENT_FILTER));

        if (!sIsStarted || !sIsTicking) {
            mTimerPresenter.setTimer(UserPreferences.getBaseTime(this));
        } else {
            mTimerProgress.setInstantProgress(mTimeLeft);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO - save state of time left
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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
    public void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        Log.i(this.getClass().getSimpleName(), "Service is unbound");
    }

    @Override
    public void onTimerSet(long timeRemaining) {
        setTimerText(UserPreferences.getBaseTime(this));
        mStartPauseButton.setImageDrawable(getDrawable(R.drawable.ic_start_timer));
        mTimerProgress.setLinearProgress(true);
        // setting up timer progress when activity is built
        mTimerProgress.setInstantProgress(1);

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
        mTimerPresenter.setTimer(UserPreferences.getBaseTime(this));
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
                case TimerService.NOTIFICATION_PLAY_MSG:
                    mTimeLeft = intent.getLongExtra(TimerService.TIME_LEFT_EXTRA, 0);
                    Log.i(this.getClass().getSimpleName(), String.valueOf(mTimeLeft));
                    mTimerProgress.setProgress(mTimeLeft / (float) UserPreferences.getBaseTime(context));
                    setTimerText(mTimeLeft -1);
                    setButtonDrawable();
                    break;
                case TimerService.NOTIFICATION_PAUSE_MSG:
                    setButtonDrawable();
                    break;
                case TimerService.NOTIFICATION_RESET_MSG:
                    mTimerPresenter.setTimer(UserPreferences.getBaseTime(getApplicationContext()));
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
