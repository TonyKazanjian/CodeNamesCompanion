package com.tonykazanjian.codenamescompanion.listeners.list;

import android.view.DragEvent;
import android.view.View;
import android.widget.AbsListView;

import com.tonykazanjian.codenamescompanion.LinearLayoutAbsListView;
import com.tonykazanjian.codenamescompanion.PassObject;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.adapter.GridViewAdapter;
import com.tonykazanjian.codenamescompanion.adapter.ItemBaseAdapter;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class ListItemDragListener implements View.OnDragListener {
    WordCard mWordCard;

    public ListItemDragListener(WordCard wordCard) {
        mWordCard = wordCard;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DROP:
                PassObject passObject = (PassObject) dragEvent.getLocalState();
                View itemView = passObject.view;
                WordCard passedWord = passObject.mWordCard;
                List<WordCard> wordCardList = passObject.mSourceList;
                AbsListView oldParent = (AbsListView)itemView.getParent();
                ItemBaseAdapter srcAdapter = (ItemBaseAdapter) (oldParent.getAdapter());

                LinearLayoutAbsListView newParent = (LinearLayoutAbsListView)view;
                ItemBaseAdapter destAdapter = (ItemBaseAdapter) (newParent.mAbsListView.getAdapter());
                List<WordCard> destList = destAdapter.getWordCards();

                if(removeItemToList(wordCardList, passedWord)){
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
