package com.tonykazanjian.codenamescompanion;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Tony Kazanjian
 */

public class WordCard implements Parcelable {

    private String mWord;

    public WordCard() {}

    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        mWord = word;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mWord);
    }

    protected WordCard(Parcel in) {
        this.mWord = in.readString();
    }

    public static final Parcelable.Creator<WordCard> CREATOR = new Parcelable.Creator<WordCard>() {
        @Override
        public WordCard createFromParcel(Parcel source) {
            return new WordCard(source);
        }

        @Override
        public WordCard[] newArray(int size) {
            return new WordCard[size];
        }
    };
}
