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
        mMainActivityView.onCardsDisplayed(cards);
    }

    public int getCardCount() {
        return mWordCards.size();
    }

    public List<WordCard> getWordCards (){
        return mWordCards;
    }

    public boolean addItemToList(List<WordCard> l, WordCard it){
        return l.add(it);
    }

    public boolean removeItemToList(List<WordCard> l, WordCard it){
        return l.remove(it);
    }

}
