package com.tonykazanjian.codenamescompanion.main;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class SettingsPresenter {

    SettingsView mSettingsView;

    public SettingsPresenter(SettingsView settingsView) {
        mSettingsView = settingsView;
    }

    public void pickTime(int pickedTime){
        mSettingsView.onBaseTimePicked(pickedTime);
    }
}
