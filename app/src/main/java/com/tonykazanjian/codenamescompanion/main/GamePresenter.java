package com.tonykazanjian.codenamescompanion.main;

import com.tonykazanjian.codenamescompanion.WordCard;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class GamePresenter {

    private GameView mGameView;
    private List<WordCard> mWordCards;

    public GamePresenter(GameView gameView) {
        mGameView = gameView;
    }

    public void showGridCards(List<WordCard> cards){
        mWordCards = cards;

//        WordCard card1 = new WordCard();
//        card1.setWord("Dog");
//        cards.add(card1);
//        WordCard card2 = new WordCard();
//        card2.setWord("Car");
//        cards.add(card2);
//        WordCard card3 = new WordCard();
//        card3.setWord("Donkey");
//        cards.add(card3);

//        WordCard card4 = new WordCard();
//        card4.setWord("Keyboard");
//        cards.add(card4);
//        WordCard card5 = new WordCard();
//        card5.setWord("Sky");
//        cards.add(card5);
//        WordCard card6 = new WordCard();
//        card6.setWord("Orange");
//        cards.add(card6);
////
////        WordCard card7 = new WordCard();
////        card7.setWord("Apple");
////        wordCards.add(card7);
////        WordCard card8 = new WordCard();
////        card8.setWord("Cloud");
////        wordCards.add(card8);
////        WordCard card9 = new WordCard();
////        card9.setWord("Street");
//        wordCards.add(card9);
        mGameView.onGridCardsDisplayed(cards);
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

    public boolean addItemToGrid(List<WordCard> l, WordCard it) {
        mGameView.removeEmptyState();
        return l.add(it);
    }

    public boolean removeItemFromGrid(List<WordCard> l, WordCard it) {
        if (l.size() == 1) {
            mGameView.showEmptyState();
        }
        return l.remove(it);
    }

    public boolean isViewBGdark() {
        return false;
    }

}
