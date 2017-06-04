package com.tonykazanjian.codenamescompanion.listeners;

import android.support.annotation.Nullable;
import android.view.DragEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.tonykazanjian.codenamescompanion.PassObject;
import com.tonykazanjian.codenamescompanion.Utils;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.adapter.ItemBaseAdapter;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class ItemDragListener implements View.OnDragListener{
    private WordCard mWordCard;
    private BaseAdapter srcAdapter;
    private BaseAdapter destAdapter;
    private List<WordCard> destList;

    public ItemDragListener(WordCard wordCard) {
        mWordCard = wordCard;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        Utils.Constants.sIsItemDragging = true;
        PassObject passObject = (PassObject) dragEvent.getLocalState();
        View itemView = passObject.view;
        WordCard passedWord = passObject.mWordCard;
        List<WordCard> srcList = passObject.mSourceList;
        AbsListView oldParent = (AbsListView)itemView.getParent();

        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DROP:
                Utils.Constants.sIsItemDragging = false;
                srcAdapter = (ItemBaseAdapter) (oldParent.getAdapter());
                AbsListView newParent = (AbsListView) view.getParent();

                destAdapter = (ItemBaseAdapter) newParent.getAdapter();
                destList = ((ItemBaseAdapter) destAdapter).getWordCards();

                if (destList != null) {
                    int removeLocation = srcList.indexOf(passedWord);
                    int insertLocation = destList.indexOf(mWordCard);
    /*
     * If drag and drop on the same list, same position,
     * ignore
     */
                    if(srcList != destList || removeLocation != insertLocation){
                        if(removeItemFromList(srcList, passedWord)){
                            destList.add(insertLocation, passedWord);
                        }

                        srcAdapter.notifyDataSetChanged();
                        destAdapter.notifyDataSetChanged();
                    }
                    break;
                }
        }
        return true;
    }

    private boolean removeItemFromList(List<WordCard> l, WordCard it){
        return l.remove(it);
    }

    private boolean addItemToList(List<WordCard> l, WordCard it){
        return l.add(it);
    }
}
