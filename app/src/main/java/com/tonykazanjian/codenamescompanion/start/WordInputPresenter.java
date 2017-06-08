package com.tonykazanjian.codenamescompanion.start;

import com.tonykazanjian.codenamescompanion.WordCard;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class WordInputPresenter {

    private WordInputView mWordInputView;
    private List<WordCard> mWordCards;

    public WordInputPresenter(List<WordCard> wordCards, WordInputView wordInputView) {
        mWordCards = wordCards;
        mWordInputView = wordInputView;
    }

    public void setWordText(List<String> stringList) {

        for (int i = 0; i < stringList.size(); i++) {
            mWordCards.add(i, new WordCard());
            mWordCards.get(i).setWord(stringList.get(i));
        }
    }

    public void startGame() {
        mWordInputView.onStartBtnPressed();
    }

    public List<WordCard> getWordCards(){
        return mWordCards;
    }

    public boolean isGameReady() {
        //TODO - always change back to 8
        return mWordCards.size() >= 0;
    }

    public void clearWords(){
        mWordCards.clear();
    }
}
