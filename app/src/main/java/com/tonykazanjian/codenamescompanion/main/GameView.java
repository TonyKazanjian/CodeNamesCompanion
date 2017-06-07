package com.tonykazanjian.codenamescompanion.main;

import com.tonykazanjian.codenamescompanion.WordCard;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public interface GameView {

    void onCardsDisplayed(List<WordCard> cards);
    void showEmptyState();
    void removeEmptyState();
}
