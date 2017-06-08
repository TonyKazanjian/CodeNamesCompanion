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

    public static String DEST_WORD_CARDS_KEY = "dest_word_cards_key";
    public static String SRC_WORD_CARDS_KEY = "src_word_cards_key";
    public static String GRID_WORD_CARDS_KEY = "grid_word_cards_key";

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

    public static void setDestWordList(List<WordCard> wordCards) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DEST_WORD_CARDS_KEY, (ArrayList<? extends Parcelable>) wordCards);
    }

    public static void setSrcWordList(List<WordCard> wordCards) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(SRC_WORD_CARDS_KEY, (ArrayList<? extends Parcelable>) wordCards);
    }

    public static void setGridWordList(Bundle bundle, List<WordCard> wordCards) {
        bundle.putParcelableArrayList(GRID_WORD_CARDS_KEY, (ArrayList<? extends Parcelable>) wordCards);
    }

    public static List<WordCard> getDestWordList(Bundle bundle) {
        return bundle.getParcelableArrayList(DEST_WORD_CARDS_KEY);
    }

    public static List<WordCard> getSrcWordList(Bundle bundle) {
        return bundle.getParcelableArrayList(SRC_WORD_CARDS_KEY);
    }

    public static List<WordCard> getGridWordList(Bundle bundle) {
        return bundle.getParcelableArrayList(GRID_WORD_CARDS_KEY);
    }
}
