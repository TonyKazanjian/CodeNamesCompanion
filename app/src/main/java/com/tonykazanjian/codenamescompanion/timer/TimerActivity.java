package com.tonykazanjian.codenamescompanion.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
    Button mStartButton;
    Button mPauseButton;
    Button mResetButton;

    MyCountDownTimer mCountDownTimer;

    // TODO - should be received in broadcast from service
    long mMinutes;
    long mSeconds;
    long mTimeLeft;

    // TODO - service variable
    public boolean sIsStarted = false;

    //TODO - should come from SharedPreferences.
    int setMinutes = (2 * 1000) * 60; // 2 minutes
    int setSeconds = (20 * 1000); // 20 seconds

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTimerText = (TextView)findViewById(R.id.timer_text);
        mStartButton = (Button) findViewById(R.id.start_btn);
        mPauseButton = (Button) findViewById(R.id.pause_btn);
        mResetButton = (Button) findViewById(R.id.reset_btn);

        mTimerPresenter = new TimerPresenter(this, (setMinutes + setSeconds));

        mTimerPresenter.setTimer();

        mStartButton.setOnClickListener(new StartPauseClickListener());

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }
            }
        });
    }

    @Override
    public void onTimerSet(long timeRemaining) {
        mSeconds = (int) (timeRemaining / 1000) % 60;
        mMinutes = (int) ((timeRemaining / (1000 * 60)) % 60);

        mTimerText.setText(String.format("%02d:%02d", mMinutes, mSeconds));
    }

    @Override
    public void onTimerStarted() {
        mCountDownTimer = new MyCountDownTimer(setMinutes + setSeconds-1, 1000);
        mCountDownTimer.start();
        sIsStarted = true;
    }

    @Override
    public void onTimerResumed() {
        mCountDownTimer = new MyCountDownTimer(mTimeLeft, 1000);
        mCountDownTimer.start();
    }

    @Override
    public void onTimerStopped() {

    }

    @Override
    public void onTimerReset() {

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

        }
    }

    private class StartPauseClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (!sIsStarted) {
                mTimerPresenter.startTimer();
            } else {
                mTimerPresenter.resumeTimer();
            }
        }
    }
}
