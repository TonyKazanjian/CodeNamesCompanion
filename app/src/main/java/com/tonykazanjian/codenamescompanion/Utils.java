package com.tonykazanjian.codenamescompanion;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Tony Kazanjian
 */

public class Utils {

    /**
     * Handles the logic for overriding a hardware keyboard's return key, and a software keyboard's
     * done key.
     *
     * @param editText; the EditText we're using to listen for keyboard events on
     * @param keyboardInterface;
     */
    public static void setKeyboardDoneAction(final TextInputEditText editText, final KeyboardInterface keyboardInterface, final Context context)
            throws NullPointerException {

        if(editText != null && keyboardInterface != null) {
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))
                            || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        keyboardInterface.keyboardDoneAction();
                        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        return true;
                    } else return false;
                }
            });
        }
        else {
            throw new NullPointerException("Must pass a non-null EditText, and KeyboardInterface");
        }
    }

    /**
     * Provides an interface for handling keyboard events
     */
    public interface KeyboardInterface {
        void keyboardDoneAction();
    }

    public static int dp2Pixel(int sizeInDp, Context context) {
        float d = context.getResources().getDisplayMetrics().density;
        return (int)(sizeInDp * d); // dp to pixels
    }

    public static class Constants {
        public static int MAX_SCORE = 10;
    }

    public static class TimeUtil {
        public static final long SECOND = 1000;
        public static final long MINUTE = 1000 * 60;

        public static long getFiftySeconds(){
            return SECOND * 5;
        }

        public static long getSeventyFiveSeconds(){
            return SECOND * 75;
        }

        public static long getNinetySeconds(){
            return SECOND * 90;
        }

        public static long getTwoMinutes() {
            return MINUTE * 2;
        }

        public static long getThreeMinutes() {
            return MINUTE * 3;
        }
    }
}
