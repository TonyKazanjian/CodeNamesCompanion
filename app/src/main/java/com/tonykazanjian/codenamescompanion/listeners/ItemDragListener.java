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

public class ItemDragListener implements View.OnDragListener {
    private WordCard mWordCard;
    private BaseAdapter srcAdapter;
    private BaseAdapter destAdapter;
    private List<WordCard> destList;

    public ItemDragListener(WordCard wordCard) {
        mWordCard = wordCard;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DROP:
                PassObject passObject = (PassObject) dragEvent.getLocalState();
                View itemView = passObject.view;
                WordCard passedWord = passObject.mWordCard;
                List<WordCard> srcList = passObject.mSourceList;
                AbsListView oldParent = (AbsListView)itemView.getParent();

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

                int removeLocation = srcList.indexOf(passedWord);
                int insertLocation = destList.indexOf(mWordCard);
    /*
     * If drag and drop on the same list, same position,
     * ignore
     */
                if(srcList != destList || removeLocation != insertLocation){
                    if(removeItemToList(srcList, passedWord)){
                        destList.add(insertLocation, passedWord);
                    }

                    srcAdapter.notifyDataSetChanged();
                    destAdapter.notifyDataSetChanged();
                }
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
