package com.tonykazanjian.codenamescompanion.timer;

/**
 * @author Tony Kazanjian
 */

public interface TimerView {
    void onTimerSet();
    void onTimerStarted();
    void onTimerResumed();
    void onTimerPaused();
    void onTimerReset();
}
