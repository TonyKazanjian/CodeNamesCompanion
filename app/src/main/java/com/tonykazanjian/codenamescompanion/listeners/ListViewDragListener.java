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

            case DragEvent.ACTION_DRAG_ENTERED:
                if (oldParent.getAdapter() instanceof ItemListAdapter){
                    Log.i(this.getClass().getCanonicalName(), "Dragged From List");
                    LinearLayoutAbsListView enteredParent = (LinearLayoutAbsListView)view;
                    if (enteredParent.mAbsListView.getAdapter() instanceof GridViewAdapter) {
                        Log.i(this.getClass().getCanonicalName(), "Inside Grid");
                        destAdapter = (GridViewAdapter) enteredParent.mAbsListView.getAdapter();
                        destList = ((GridViewAdapter)destAdapter).getWordCards();

//                        if(removeItemToList(srcList, passedWord)){
//                            addItemToList(destList, passedWord);
//                        }
//
//                        destAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.i(this.getClass().getCanonicalName(), "Dragged from Grid");
                }
                break;
            case DragEvent.ACTION_DROP:

                if (oldParent.getAdapter() instanceof GridViewAdapter) {
                    srcAdapter = (GridViewAdapter) (oldParent.getAdapter());
                } else {
                    srcAdapter = (ItemBaseAdapter) (oldParent.getAdapter());
                }

                LinearLayoutAbsListView newParent = (LinearLayoutAbsListView)view;
                if (newParent.mAbsListView.getAdapter() instanceof GridViewAdapter) {
                    destAdapter = (GridViewAdapter) newParent.mAbsListView.getAdapter();
                    destList = ((GridViewAdapter)destAdapter).getWordCards();
                } else {
                    destAdapter = (ItemBaseAdapter) newParent.mAbsListView.getAdapter();
                    destList = ((ItemBaseAdapter)destAdapter).getWordCards();
                }


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
        if (srcAdapter instanceof GridViewAdapter) {
            ((GridViewAdapter) srcAdapter).remove(it);
        }

        return l.remove(it);
    }

    private boolean addItemToList(List<WordCard> l, WordCard it){

        if (destAdapter instanceof GridViewAdapter) {
            ((GridViewAdapter)destAdapter).add(it);
        }
        return l.add(it);
    }
}
