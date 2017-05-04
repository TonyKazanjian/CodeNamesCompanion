package com.tonykazanjian.codenamescompanion;

import android.view.View;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class PassObject {

    View view;
    WordCard mWordCard;
    List<WordCard> mWordCardList;

    public PassObject(View view, WordCard wordCard, List<WordCard> wordCardList) {
        this.view = view;
        mWordCard = wordCard;
        mWordCardList = wordCardList;
    }
}
