package com.tonykazanjian.codenamescompanion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class UserPreferences {

    private static String BLUE_POINTS_KEY = "blue_points_key";
    private static String RED_POINTS_KEY = "red_points_key";

    public static String CODE_PANEL_1_WORDS_KEY = "code_panel_1_words_key";
    public static String CODE_PANEL_2_WORDS_KEY = "code_panel_2_words_key";
    public static String CODE_PANEL_3_WORDS_KEY = "code_panel_3_words_key";
    public static String CODE_PANEL_4_WORDS_KEY = "code_paneL_4_words_key";
    public static String GRID_WORDS_KEY = "grid_word_key";

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
