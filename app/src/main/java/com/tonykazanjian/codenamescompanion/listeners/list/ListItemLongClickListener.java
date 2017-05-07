package com.tonykazanjian.codenamescompanion.listeners.list;

import android.content.ClipData;
import android.view.View;
import android.widget.AdapterView;

import com.tonykazanjian.codenamescompanion.PassObject;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.adapter.ItemBaseAdapter;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class ListItemLongClickListener implements AdapterView.OnItemLongClickListener {

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        WordCard selectedWord = (WordCard)(adapterView.getItemAtPosition(i));
        ItemBaseAdapter associatedAdapter = (ItemBaseAdapter)(adapterView.getAdapter());
        List<WordCard> associatedList = associatedAdapter.getWordCards();

        PassObject passObject = new PassObject(view, selectedWord, associatedList);
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDrag(data, shadowBuilder, passObject, 0);

        return true;
    }
}
