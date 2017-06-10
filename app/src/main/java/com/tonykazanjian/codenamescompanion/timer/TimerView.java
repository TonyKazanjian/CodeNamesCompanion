package com.tonykazanjian.codenamescompanion.timer;

/**
 * @author Tony Kazanjian
 */

public interface TimerView {
    void onTimerSet();
    boolean onTimerStarted();
    boolean onTimerStopped();
    void onTimerReset();
}
