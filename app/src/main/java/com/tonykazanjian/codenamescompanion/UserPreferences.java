package com.tonykazanjian.codenamescompanion;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.prefs.Preferences;

/**
 * @author Tony Kazanjian
 */

public class UserPreferences {

    public static String BLUE_POINTS_KEY = "blue_points_key";
    public static String RED_POINTS_KEY = "red_points_key";

    public static void setBlueScore(Context context, int bluePoints){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putInt(BLUE_POINTS_KEY, bluePoints).apply();
    }

    public static void setRedScore(Context context, int redPoints){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putInt(RED_POINTS_KEY, redPoints).apply();
    }

    public static int getBlueScore(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(BLUE_POINTS_KEY, 0);
    }

    public static int getRedScore(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(RED_POINTS_KEY, 0);
    }
}
