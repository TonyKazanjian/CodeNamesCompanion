package com.tonykazanjian.codenamescompanion;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public interface MainActivityView {

    void displayCards(List<WordCard> cards);
    void saveTextOnCard(String text);
    void removeCard();
}
