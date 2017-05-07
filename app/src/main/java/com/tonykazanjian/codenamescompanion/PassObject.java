package com.tonykazanjian.codenamescompanion;

import android.view.View;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class PassObject {

    public View view;
    public WordCard mWordCard;
    public List<WordCard> mSourceList;

    public PassObject(View view, WordCard wordCard, List<WordCard> sourceList) {
        this.view = view;
        mWordCard = wordCard;
        mSourceList = sourceList;
    }
}
