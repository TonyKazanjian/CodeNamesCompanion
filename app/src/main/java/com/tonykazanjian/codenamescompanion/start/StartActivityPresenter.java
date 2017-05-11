package com.tonykazanjian.codenamescompanion.start;

import com.tonykazanjian.codenamescompanion.WordCard;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class StartActivityPresenter {

    private StartActivityView mStartActivityView;
    private List<WordCard> mWordCards;

    public StartActivityPresenter(List<WordCard> wordCards, StartActivityView startActivityView) {
        mWordCards = wordCards;
        mStartActivityView = startActivityView;
    }

    public void setWordText(List<String> stringList) {


        for (int i = 0; i < stringList.size(); i++) {
            mWordCards.add(i, new WordCard());
            mWordCards.get(i).setWord(stringList.get(i));
        }
    }

    public void startGame() {
        mStartActivityView.onStartBtnPressed();
    }

    public boolean checkReadiness() {
        return mWordCards.size() >= 7;
    }

    public List<WordCard> getWordCards(){
        return mWordCards;
    }
}
