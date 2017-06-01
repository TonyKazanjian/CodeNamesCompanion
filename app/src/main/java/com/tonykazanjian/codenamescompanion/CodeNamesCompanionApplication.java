package com.tonykazanjian.codenamescompanion;

import android.app.Application;
import android.content.Context;

/**
 * @author Tony Kazanjian
 */

public class CodeNamesCompanionApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
