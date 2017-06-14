package com.tonykazanjian.codenamescompanion.timer;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.UserPreferences;

import java.util.concurrent.TimeUnit;

/**
 * @author Tony Kazanjian
 */

public class TimerActivity extends AppCompatActivity implements TimerView {

    TextView mTimerText;
    TimerPresenter mTimerPresenter;
    Button mStartPauseButton;
    Button mResetButton;

    TimerService mTimerService;
    TimerTickReceiver mTimerTickReceiver;
    TimerFinishedReceiver mTimerFinishedReceiver;

    // TODO - service variables
    public boolean sIsTicking = false;
    public boolean sIsStarted = false;

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
        mStartPauseButton = (Button) findViewById(R.id.start_btn);
        mResetButton = (Button) findViewById(R.id.reset_btn);

        mTimerPresenter = new TimerPresenter(this, (UserPreferences.getBaseTime(this)));

        mTimerPresenter.setTimer();

        mStartPauseButton.setOnClickListener(new StartPauseClickListener());
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimerPresenter.resetTimer();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TimerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        Log.i(this.getClass().getCanonicalName(), "Service is bound");
        mTimerTickReceiver = new TimerTickReceiver();
        registerReceiver(mTimerTickReceiver, new IntentFilter(TimerService.TIMER_TICK_INTENT_FILTER));
        mTimerFinishedReceiver = new TimerFinishedReceiver();
        registerReceiver(mTimerFinishedReceiver, new IntentFilter(TimerService.TIMER_FINISHED_INTENT_FILTER));
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        Log.i(this.getClass().getSimpleName(), "Service is unbound");
        if (mTimerTickReceiver != null) {
            unregisterReceiver(mTimerTickReceiver);
        }
        if (mTimerFinishedReceiver != null) {
            unregisterReceiver(mTimerFinishedReceiver);
        }
    }

    @Override
    public void onTimerSet(long timeRemaining) {
        setTimerText(UserPreferences.getBaseTime(this));
        Intent timerIntent = new Intent(this, TimerService.class);
        timerIntent.setAction(TimerService.ACTION_RESET);
        startService(timerIntent);
        mStartPauseButton.setText("Start");
    }

    @Override
    public void onTimerStarted() {
        sIsTicking = true;
        sIsStarted = true;
        setButtonText();
        Intent timerIntent = new Intent(this, TimerService.class);
        timerIntent.setAction(TimerService.ACTION_START);
        startService(timerIntent);
    }

    //TODO - these send intents to the service
    @Override
    public void onTimerResumed() {
        sIsTicking = true;
        setButtonText();
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
        setButtonText();
    }

    @Override
    public void onTimerReset() {
        mTimerPresenter.setTimer();
        sIsStarted = false;
        sIsTicking = false;
        setButtonText();
    }

    private void setButtonText() {
        if (sIsTicking) {
            mStartPauseButton.setText("Pause");
        } else {
            mStartPauseButton.setText("Start");
        }
    }


    private class StartPauseClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (!sIsStarted) {
                mTimerPresenter.startTimer();
            } else if (!sIsTicking) {
                mTimerPresenter.resumeTimer();
            } else {
                mTimerPresenter.pauseTimer();
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
            long timeLeft = intent.getLongExtra(TimerService.TIME_LEFT_EXTRA, 0);
            setTimerText(timeLeft-1);
        }
    }

    private class TimerFinishedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mTimerPresenter.resetTimer();
        }
    }
}
