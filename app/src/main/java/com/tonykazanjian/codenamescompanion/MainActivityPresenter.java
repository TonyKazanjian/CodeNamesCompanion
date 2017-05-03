package com.tonykazanjian.codenamescompanion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class MainActivityPresenter {

    private MainActivityView mMainActivityView;
    private int mCardCount;

    public MainActivityPresenter(MainActivityView mainActivityView) {
        mMainActivityView = mainActivityView;
    }

    public void showCards(List<WordCard> cards){
        WordCard card = new WordCard();

        for (int i = 0; i < 9; i++) {
            cards.add(card);
            getCardCount();
        }
        mMainActivityView.displayCards(cards);
    }

    public int getCardCount() {
        mCardCount++;
        return mCardCount;
    }
}
