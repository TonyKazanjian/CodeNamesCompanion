package com.tonykazanjian.codenamescompanion.timer;

/**
 * @author Tony Kazanjian
 */

public interface TimerView {
    void onTimerSet(long timeRemaining);
    void onTimerStarted();
    void onTimerResumed();
    void onTimerPaused();
    void onTimerReset();
}
