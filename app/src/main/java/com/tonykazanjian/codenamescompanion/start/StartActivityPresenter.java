package com.tonykazanjian.codenamescompanion.start;

import com.tonykazanjian.codenamescompanion.WordCard;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class StartActivityPresenter {

    private List<WordCard> mWordCards;
    private StartActivityView mStartActivityView;

    public StartActivityPresenter(List<WordCard> wordCards, StartActivityView startActivityView) {
        mWordCards = wordCards;
        mStartActivityView = startActivityView;
    }

    public void setWordText(List<String> stringList) {
        for (int i = 0; i < stringList.size(); i++) {
            mWordCards.get(i).setWord(stringList.get(i));
        }
    }
}
