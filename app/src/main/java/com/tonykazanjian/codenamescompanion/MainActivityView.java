package com.tonykazanjian.codenamescompanion;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public interface MainActivityView {

    void displayCards(List<WordCard> cards);
    void enterTextOnCard(String text);
    void saveTextOnCard();
    void removeCard();
}
