package com.tonykazanjian.codenamescompanion.listeners.grid;


import android.view.DragEvent;
import android.view.View;
import android.widget.AbsListView;

import com.tonykazanjian.codenamescompanion.PassObject;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.adapter.GridViewAdapter;
import com.tonykazanjian.codenamescompanion.adapter.ItemBaseAdapter;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class GridItemDragListener implements View.OnDragListener {

    WordCard mWordCard;

    public GridItemDragListener(WordCard wordCard) {
        mWordCard = wordCard;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        switch (dragEvent.getAction()){
            case DragEvent.ACTION_DROP:
                PassObject passObject = (PassObject) dragEvent.getLocalState();
                View gridView = passObject.view;
                WordCard passedWord = passObject.mWordCard;
                List<WordCard> srcList = passObject.mSourceList;
                AbsListView oldParent = (AbsListView)gridView.getParent();
                ItemBaseAdapter srcAdapter = (ItemBaseAdapter) (oldParent.getAdapter());

                AbsListView newParent = (AbsListView)view.getParent();
                ItemBaseAdapter destAdapter = (ItemBaseAdapter)(newParent.getAdapter());
                List<WordCard> destList = destAdapter.getWordCards();

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
        }



        return true;
    }

    private boolean removeItemToList(List<WordCard> l, WordCard it){
        boolean result = l.remove(it);
        return result;
    }
}
