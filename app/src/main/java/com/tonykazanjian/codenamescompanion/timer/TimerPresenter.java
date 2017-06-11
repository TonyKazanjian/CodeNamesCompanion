package com.tonykazanjian.codenamescompanion.timer;

/**
 * @author Tony Kazanjian
 */

public class TimerPresenter {

    TimerView mTimerView;
    int mStartTime;

    public TimerPresenter(TimerView timerView, int startTime) {
        mTimerView = timerView;
        mStartTime = startTime;
    }

    public void setTimer(){
        mTimerView.onTimerSet(mStartTime);
    }

    public void startTimer() {
        mTimerView.onTimerStarted();
    }

    public void resumeTimer() {
        mTimerView.onTimerResumed();
    }
}
