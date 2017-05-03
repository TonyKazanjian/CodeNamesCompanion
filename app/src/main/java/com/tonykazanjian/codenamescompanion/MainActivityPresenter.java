package com.tonykazanjian.codenamescompanion;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class MainActivityPresenter {

    private MainActivityView mMainActivityView;
    private List<WordCard> mWordCards;

    public MainActivityPresenter(MainActivityView mainActivityView) {
        mMainActivityView = mainActivityView;
    }

    public void showCards(List<WordCard> cards){
        mWordCards = cards;

        for (int i = 0; i <= 8; i++) {
            WordCard card = new WordCard();
            cards.add(card);
        }
        mMainActivityView.onCardsDisplayed(cards);
    }

    public int getCardCount() {
        return mWordCards.size();
    }
}
