package com.tonykazanjian.codenamescompanion.listeners;

import android.view.DragEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.tonykazanjian.codenamescompanion.LinearLayoutAbsListView;
import com.tonykazanjian.codenamescompanion.PassObject;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.adapter.GridViewAdapter;
import com.tonykazanjian.codenamescompanion.adapter.ItemBaseAdapter;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class ViewDragListener implements View.OnDragListener {

    private BaseAdapter srcAdapter;
    private BaseAdapter destAdapter;
    private List<WordCard> destList;

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DROP:
                PassObject passObject = (PassObject) dragEvent.getLocalState();
                View listView = passObject.view;
                WordCard passedWord = passObject.mWordCard;
                List<WordCard> srcList = passObject.mSourceList;
                AbsListView oldParent = (AbsListView)listView.getParent();
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
