package com.tonykazanjian.codenamescompanion.listeners;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.tonykazanjian.codenamescompanion.LinearLayoutAbsListView;
import com.tonykazanjian.codenamescompanion.PassObject;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.adapter.GridViewAdapter;
import com.tonykazanjian.codenamescompanion.adapter.ItemBaseAdapter;
import com.tonykazanjian.codenamescompanion.adapter.ItemListAdapter;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class ListViewDragListener implements View.OnDragListener {

    private BaseAdapter srcAdapter;
    private BaseAdapter destAdapter;
    private List<WordCard> destList;

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        PassObject passObject = (PassObject) dragEvent.getLocalState();
        View listView = passObject.view;
        WordCard passedWord = passObject.mWordCard;
        List<WordCard> srcList = passObject.mSourceList;
        AbsListView oldParent = (AbsListView)listView.getParent();

        switch (dragEvent.getAction()) {

            case DragEvent.ACTION_DROP:

                srcAdapter = (ItemBaseAdapter) (oldParent.getAdapter());
                LinearLayoutAbsListView newParent = (LinearLayoutAbsListView)view;
                destAdapter = (ItemBaseAdapter) newParent.mAbsListView.getAdapter();
                destList = ((ItemBaseAdapter)destAdapter).getWordCards();

                if(removeItemToList(srcList, passedWord)){
                    addItemToList(destList, passedWord);
                }

                srcAdapter.notifyDataSetChanged();
                destAdapter.notifyDataSetChanged();

                //smooth scroll to bottom
                newParent.mAbsListView.smoothScrollToPosition(destAdapter.getCount()-1);

                break;
        }
        return true;
    }

    private boolean removeItemToList(List<WordCard> l, WordCard it){
        return l.remove(it);
    }

    private boolean addItemToList(List<WordCard> l, WordCard it){
        return l.add(it);
    }
}
