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

        WordCard card1 = new WordCard();
        card1.setWord("Dog");
        cards.add(card1);
        WordCard card2 = new WordCard();
        card2.setWord("Car");
        cards.add(card2);
        WordCard card3 = new WordCard();
        card3.setWord("Donkey");
        cards.add(card3);

//        WordCard card4 = new WordCard();
//        card4.setWord("Keyboard");
//        wordCards.add(card4);
//        WordCard card5 = new WordCard();
//        card5.setWord("Sky");
//        wordCards.add(card5);
//        WordCard card6 = new WordCard();
//        card6.setWord("Orange");
//        wordCards.add(card6);
//
//        WordCard card7 = new WordCard();
//        card7.setWord("Apple");
//        wordCards.add(card7);
//        WordCard card8 = new WordCard();
//        card8.setWord("Cloud");
//        wordCards.add(card8);
//        WordCard card9 = new WordCard();
//        card9.setWord("Street");
//        wordCards.add(card9);
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

    public boolean removeItemFromList(List<WordCard> l, WordCard it){
        return l.remove(it);
    }

}
