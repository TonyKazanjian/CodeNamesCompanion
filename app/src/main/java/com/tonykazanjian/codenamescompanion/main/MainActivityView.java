package com.tonykazanjian.codenamescompanion.main;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public interface MainActivityView {

    void onCardsDisplayed(List<?> cards);
    void onEditModeInit(int item);
    void onEditStopItemClicked();
    void onRemoveBtnClicked();
}
