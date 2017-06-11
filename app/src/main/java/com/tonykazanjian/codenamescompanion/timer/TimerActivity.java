package com.tonykazanjian.codenamescompanion.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bcgdv.asia.lib.ticktock.TickTockView;
import com.tonykazanjian.codenamescompanion.R;

import java.util.Calendar;

/**
 * @author Tony Kazanjian
 */

public class TimerActivity extends AppCompatActivity implements TimerView {

    TextView mTimerText;
    TimerPresenter mTimerPresenter;
    Button mStartButton;
    Button mPauseButton;
    Button mResetButton;

    CountDownTimer mCountDownTimer;

    int mMinutes;
    int mSeconds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTimerText = (TextView)findViewById(R.id.timer_text);
        mStartButton = (Button) findViewById(R.id.start_btn);
        mPauseButton = (Button) findViewById(R.id.pause_btn);
        mResetButton = (Button) findViewById(R.id.reset_btn);

        //TODO - get start time from shared prefs
        mTimerPresenter = new TimerPresenter(this, 10000);

        mTimerPresenter.setTimer();

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimerPresenter.startTimer();
            }
        });

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
//        mTimer.setOnTickListener(new TickTockView.OnTickListener() {
//            @Override
//            public String getText(long timeRemaining) {
//                int seconds = (int) (timeRemaining / 1000) % 60;
//                int minutes = (int) ((timeRemaining / (1000 * 60)) % 60);
//                int hours = (int) ((timeRemaining / (1000 * 60 * 60)) % 24);
//                int days = (int) (timeRemaining / (1000 * 60 * 60 * 24));
//                boolean hasDays = days > 0;
//                return String.format("%1$02d%4$s %2$02d%5$s %3$02d%6$s",
//                        hasDays ? days : hours,
//                        hasDays ? hours : minutes,
//                        hasDays ? minutes : seconds,
//                        hasDays ? "d" : "h",
//                        hasDays ? "h" : "m",
//                        hasDays ? "m" : "s");
//            }
//        });
    }

    @Override
    public boolean onTimerStarted() {
        mCountDownTimer = new CountDownTimer(40000, 1000) {
            @Override
            public void onTick(long l) {
                mSeconds = (int) (l/1000);
                mTimerText.setText(String.format("%02d:%02d", mMinutes, mSeconds));
            }

            @Override
            public void onFinish() {

            }
        }.start();
        return true;
    }

    @Override
    public boolean onTimerStopped() {
        return false;
    }

    @Override
    public void onTimerReset() {

    }
}
