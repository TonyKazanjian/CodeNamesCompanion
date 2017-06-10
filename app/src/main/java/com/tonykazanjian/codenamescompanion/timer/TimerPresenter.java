package com.tonykazanjian.codenamescompanion.timer;

/**
 * @author Tony Kazanjian
 */

public class TimerPresenter {

    TimerView mTimerView;

    public TimerPresenter(TimerView timerView) {
        mTimerView = timerView;
    }

    public void setTimer(){
        mTimerView.onTimerSet();
    }

    public boolean startTimer() {
        return !mTimerView.onTimerStarted();
    }
}
