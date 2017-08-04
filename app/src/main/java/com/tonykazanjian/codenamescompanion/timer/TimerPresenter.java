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

    public void startTimer() {
        mTimerView.onTimerStarted();
    }

    public void resumeTimer() {
        mTimerView.onTimerResumed();
    }

    public void pauseTimer() {
        mTimerView.onTimerPaused();
    }

    public void resetTimer() {
        mTimerView.onTimerReset();
    }
}
