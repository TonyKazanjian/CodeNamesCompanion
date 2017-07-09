package com.tonykazanjian.codenamescompanion.listeners;

import android.view.View;

/**
 * @author Tony Kazanjian
 */

public class WordInputListener implements View.OnFocusChangeListener {


    @Override
    public void onFocusChange(View view, boolean b) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
    }
}
