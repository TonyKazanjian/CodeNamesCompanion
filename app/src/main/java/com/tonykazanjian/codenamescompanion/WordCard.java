package com.tonykazanjian.codenamescompanion;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author Tony Kazanjian
 */

public class WordCard implements Serializable{

    private String mWord;

    public WordCard() {}

    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        mWord = word;
    }
}
