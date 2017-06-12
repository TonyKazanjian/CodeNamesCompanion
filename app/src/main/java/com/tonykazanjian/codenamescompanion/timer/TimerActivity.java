package com.tonykazanjian.codenamescompanion.timer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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

import java.util.concurrent.TimeUnit;

/**
 * @author Tony Kazanjian
 */

public class TimerActivity extends AppCompatActivity implements TimerView {

    TextView mTimerText;
    TimerPresenter mTimerPresenter;
    Button mStartPauseButton;
    Button mResetButton;

    MyCountDownTimer mCountDownTimer;
    TimerService mTimerService;

    // TODO - should be received in broadcast from service
    long mMinutes;
    long mSeconds;
    long mTimeLeft;

    // TODO - service variables
    public boolean sIsTicking = false;
    public boolean sIsStarted = false;

    //TODO - should come from SharedPreferences.
    int setMinutes = 0;
    int setSeconds = (10 * 1000); // 20 seconds

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

        mTimerPresenter = new TimerPresenter(this, (10000));

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        Log.i(this.getClass().getSimpleName(), "Service is unbound");
    }

    @Override
    public void onTimerSet(long timeRemaining) {
        mSeconds = (int) (timeRemaining / 1000) % 60;
        mMinutes = (int) ((timeRemaining / (1000 * 60)) % 60);

        mTimerText.setText(String.format("%02d:%02d", mMinutes, mSeconds));
        mStartPauseButton.setText("Start");
    }

    @Override
    public void onTimerStarted() {
        mCountDownTimer = new MyCountDownTimer(setMinutes + setSeconds-1, 1000);
        mCountDownTimer.start();
        sIsTicking = true;
        sIsStarted = true;
        setButtonText();
        startService(new Intent(this, TimerService.class));
    }

    @Override
    public void onTimerResumed() {
        mCountDownTimer = new MyCountDownTimer(mTimeLeft, 1000);
        mCountDownTimer.start();
        sIsTicking = true;
        setButtonText();
    }

    @Override
    public void onTimerPaused() {
        mCountDownTimer.cancel();
        sIsTicking = false;
        setButtonText();
    }

    @Override
    public void onTimerReset() {
        mCountDownTimer.cancel();
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

    private class MyCountDownTimer extends android.os.CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTimeLeft = millisUntilFinished;
            mTimerText.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(mTimeLeft) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(mTimeLeft)),
                    TimeUnit.MILLISECONDS.toSeconds(mTimeLeft) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mTimeLeft))));
        }

        @Override
        public void onFinish() {
            mTimerPresenter.resetTimer();
            //TODO - notify user
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
}
