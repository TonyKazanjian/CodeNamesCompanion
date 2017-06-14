package com.tonykazanjian.codenamescompanion.main;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public interface SettingsView {
    void onBaseTimePicked(List<Integer> timeList);
    void onCardNumberPicked();
}
