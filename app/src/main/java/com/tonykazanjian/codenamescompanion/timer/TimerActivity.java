package com.tonykazanjian.codenamescompanion.timer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bcgdv.asia.lib.ticktock.TickTockView;
import com.tonykazanjian.codenamescompanion.R;

import java.util.Calendar;

/**
 * @author Tony Kazanjian
 */

public class TimerActivity extends AppCompatActivity implements TimerView {

    TickTockView mTimer;
    TimerPresenter mTimerPresenter;
    Button mStartButton;
    Button mPauseButton;
    Button mResetButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTimer = (TickTockView)findViewById(R.id.timerView);
        mStartButton = (Button) findViewById(R.id.start_btn);
        mPauseButton = (Button) findViewById(R.id.pause_btn);
        mResetButton = (Button) findViewById(R.id.reset_btn);

        mTimerPresenter = new TimerPresenter(this);

        mTimerPresenter.setTimer();

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimerPresenter.startTimer();
            }
        });
    }



    @Override
    public void onTimerSet() {
        mTimer.setOnTickListener(new TickTockView.OnTickListener() {
            @Override
            public String getText(long timeRemaining) {
                int seconds = (int) (timeRemaining / 1000) % 60;
                int minutes = (int) ((timeRemaining / (1000 * 60)) % 60);
                int hours = (int) ((timeRemaining / (1000 * 60 * 60)) % 24);
                int days = (int) (timeRemaining / (1000 * 60 * 60 * 24));
                boolean hasDays = days > 0;
                return String.format("%1$02d%4$s %2$02d%5$s %3$02d%6$s",
                        hasDays ? days : hours,
                        hasDays ? hours : minutes,
                        hasDays ? minutes : seconds,
                        hasDays ? "d" : "h",
                        hasDays ? "h" : "m",
                        hasDays ? "m" : "s");
            }
        });
    }

    @Override
    public boolean onTimerStarted() {
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MINUTE, 4);
        end.add(Calendar.SECOND, 5);

        Calendar start = Calendar.getInstance();
        start.add(Calendar.MINUTE, -1);
        mTimer.start(start, end);
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
