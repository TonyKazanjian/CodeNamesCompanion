package com.tonykazanjian.codenamescompanion.main;

import android.widget.LinearLayout;

import com.tonykazanjian.codenamescompanion.LinearLayoutAbsListView;
import com.tonykazanjian.codenamescompanion.WordCard;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public interface GameView {

    void onGridCardsDisplayed(List<WordCard> cards);
//    void onCodePanelListDisplayed_1(List<WordCard> cards);
//    void onCodePanelListDisplayed_2(List<WordCard> cards);
//    void onCodePanelListDisplayed_3(List<WordCard> cards);
//    void onCodePanelListDisplayed_4(List<WordCard> cards);
    void showEmptyState();
    void removeEmptyState();
    void onViewBGChanged(LinearLayoutAbsListView newParent, boolean isEntered);
    void onDragStarted(boolean textInputHasFocus);
}
