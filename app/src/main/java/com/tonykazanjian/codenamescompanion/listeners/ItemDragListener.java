package com.tonykazanjian.codenamescompanion.listeners;

import android.support.annotation.Nullable;
import android.view.DragEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.tonykazanjian.codenamescompanion.LinearLayoutAbsListView;
import com.tonykazanjian.codenamescompanion.PassObject;
import com.tonykazanjian.codenamescompanion.Utils;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.adapter.ItemBaseAdapter;
import com.tonykazanjian.codenamescompanion.main.GameView;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class ItemDragListener implements View.OnDragListener{
    private WordCard mWordCard;
    private BaseAdapter srcAdapter;
    private BaseAdapter destAdapter;
    private List<WordCard> destList;
    private GameView mGameView;

    public ItemDragListener(WordCard wordCard, GameView gameView) {
        mWordCard = wordCard;
        mGameView = gameView;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        PassObject passObject = (PassObject) dragEvent.getLocalState();
        View itemView = passObject.view;
        WordCard passedWord = passObject.mWordCard;
        List<WordCard> srcList = passObject.mSourceList;
        AbsListView oldParent = (AbsListView)itemView.getParent();
        AbsListView newParent = (AbsListView) view.getParent();

        switch (dragEvent.getAction()) {

            case DragEvent.ACTION_DRAG_ENTERED:
                mGameView.onViewBGChanged(newParent, true);
                break;

            case DragEvent.ACTION_DRAG_EXITED:
                mGameView.onViewBGChanged(newParent, false);
                break;

            case DragEvent.ACTION_DROP:
                srcAdapter = (ItemBaseAdapter) (oldParent.getAdapter());
                mGameView.onViewBGChanged(newParent, false);

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
