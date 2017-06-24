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
    private static String BASE_TIME_KEY = "base_time_key";
    private static String SPINNER_POSITION = "spinner_position";
    public static String CARD_NUMBER = "card_number";
    private static String CHECKED_BUTTON = "checked_button";

    public static String CODE_PANEL_1_WORDS_KEY = "code_panel_1_words_key";
    public static String CODE_PANEL_2_WORDS_KEY = "code_panel_2_words_key";
    public static String CODE_PANEL_3_WORDS_KEY = "code_panel_3_words_key";
    public static String CODE_PANEL_4_WORDS_KEY = "code_paneL_4_words_key";
    public static String GRID_WORDS_KEY = "grid_word_key";

    public static void setBlueScore(Context context, int bluePoints){
        getSharedPreferences(context).edit().putInt(BLUE_POINTS_KEY, bluePoints).apply();
    }

    public static void setRedScore(Context context, int redPoints){
        getSharedPreferences(context).edit().putInt(RED_POINTS_KEY, redPoints).apply();
    }

    public static int getBlueScore(Context context){
        return getSharedPreferences(context).getInt(BLUE_POINTS_KEY, 0);
    }

    public static int getRedScore(Context context){
        return getSharedPreferences(context).getInt(RED_POINTS_KEY, 0);
    }

    public static void setBaseTime(Context context, int time){
        getSharedPreferences(context).edit().putInt(BASE_TIME_KEY, time).apply();
    }

    public static int getBaseTime(Context context) {
        return getSharedPreferences(context).getInt(BASE_TIME_KEY, 0);
    }

    public static void setSpinnerPosition(Context context, int spinnerPosition) {
        getSharedPreferences(context).edit().putInt(SPINNER_POSITION, spinnerPosition).apply();
    }

    public static int getSpinnerPosition(Context context){
        return getSharedPreferences(context).getInt(SPINNER_POSITION, 0);
    }

    public void setCardNumber(Context context, int cardNumber) {
        getSharedPreferences(context).edit().putInt(CARD_NUMBER, cardNumber).apply();
    }

    public static int getCardNumber(Context context){
        return getSharedPreferences(context).getInt(CARD_NUMBER, 0);
    }

    public static void setCheckedButton(Context context, int buttonId){
        getSharedPreferences(context).edit().putInt(CHECKED_BUTTON, buttonId).apply();
    }

    public static int getCheckedButton(Context context) {
        return getSharedPreferences(context).getInt(CHECKED_BUTTON, 0);
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
