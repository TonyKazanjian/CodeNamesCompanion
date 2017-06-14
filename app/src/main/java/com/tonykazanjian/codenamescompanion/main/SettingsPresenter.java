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

    public void pickTime(List<Integer> timeList){
        mSettingsView.onBaseTimePicked(timeList);
    }

    public void pickCardNumber(){
        mSettingsView.onCardNumberPicked();
    }
}
