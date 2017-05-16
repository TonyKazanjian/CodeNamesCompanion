package com.tonykazanjian.codenamescompanion.main;

import com.tonykazanjian.codenamescompanion.WordCard;

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

//        //TODO - change to 8
//        for (int i = 0; i <= 5; i++) {
//            WordCard card = new WordCard();
//            cards.add(card);
//        }
        mMainActivityView.onCardsDisplayed(cards);
    }

    public int getCardCount() {
        return mWordCards.size();
    }

    public void editCards(int item) {
//        mMainActivityView.onEditModeInit(item);
    }

    public void turnOffEditMode(){
//        mMainActivityView.onEditStopItemClicked();
    }
}
