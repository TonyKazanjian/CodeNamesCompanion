package com.tonykazanjian.codenamescompanion.listeners;

import android.content.ClipData;
import android.view.View;
import android.widget.AdapterView;

import com.tonykazanjian.codenamescompanion.PassObject;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.adapter.GridViewAdapter;
import com.tonykazanjian.codenamescompanion.main.GamePresenter;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class GridItemLongClickListener implements AdapterView.OnItemLongClickListener {

    private GamePresenter mGamePresenter;

    public GridItemLongClickListener(GamePresenter gamePresenter) {
        mGamePresenter = gamePresenter;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        WordCard selectedWord = (WordCard) adapterView.getItemAtPosition(i);

        GridViewAdapter associatedAdapter = (GridViewAdapter) (adapterView.getAdapter());
        List<WordCard> wordCardList = associatedAdapter.getWordCards();

        PassObject passObject = new PassObject(view, selectedWord, wordCardList);

        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDrag(data, shadowBuilder, passObject, 0);

        return true;
    }
}
