package com.tonykazanjian.codenamescompanion;

import android.view.View;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class PassObject {

    public View view;
    public WordCard mWordCard;
    public List<WordCard> mWordCardList;

    public PassObject(View view, WordCard wordCard, List<WordCard> wordCardList) {
        this.view = view;
        mWordCard = wordCard;
        mWordCardList = wordCardList;
    }
}
