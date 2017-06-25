package com.tonykazanjian.codenamescompanion.start;

import com.tonykazanjian.codenamescompanion.CodeNamesCompanionApplication;
import com.tonykazanjian.codenamescompanion.UserPreferences;
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

    public boolean isGameReady(int setAmount) {
        return mWordCards.size() == setAmount;
    }

    public int getWordAmountPrefs(){
        return mWordInputView.onWordAmountSet();
    }

    public void clearWords(){
        mWordCards.clear();
    }
}
