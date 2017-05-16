package com.tonykazanjian.codenamescompanion.main;

import com.tonykazanjian.codenamescompanion.WordCard;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public interface MainActivityView {

    void onCardsDisplayed(List<WordCard> cards);
//    void onEditModeInit(int item);
//    void onEditStopItemClicked();
    void onRemoveBtnClicked();
}
