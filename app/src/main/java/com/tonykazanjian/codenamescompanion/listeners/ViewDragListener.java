package com.tonykazanjian.codenamescompanion.listeners;

import android.support.v7.widget.CardView;
import android.view.DragEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.tonykazanjian.codenamescompanion.LinearLayoutAbsListView;
import com.tonykazanjian.codenamescompanion.PassObject;
import com.tonykazanjian.codenamescompanion.UserPreferences;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.adapter.GridViewAdapter;
import com.tonykazanjian.codenamescompanion.adapter.ItemBaseAdapter;
import com.tonykazanjian.codenamescompanion.main.GamePresenter;
import com.tonykazanjian.codenamescompanion.main.GameView;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class ViewDragListener implements View.OnDragListener {

    private BaseAdapter srcAdapter;
    private BaseAdapter destAdapter;
    private List<WordCard> destList;

    private GamePresenter mGamePresenter;
    private GameView mGameView;

    public ViewDragListener(GamePresenter gamePresenter, GameView gameView) {
        mGamePresenter = gamePresenter;
        mGameView = gameView;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        PassObject passObject = (PassObject) dragEvent.getLocalState();
        View listView = passObject.view;
        WordCard passedWord = passObject.mWordCard;
        List<WordCard> srcList = passObject.mSourceList;
        AbsListView oldParent = (AbsListView)listView.getParent();
        LinearLayoutAbsListView newParent = (LinearLayoutAbsListView)view;

        switch (dragEvent.getAction()) {

            case DragEvent.ACTION_DRAG_STARTED:
                mGameView.onDragStarted(false);
                break;

            case DragEvent.ACTION_DRAG_ENTERED:
                mGameView.onViewBGChanged(newParent.mAbsListView, true);
                break;

            case DragEvent.ACTION_DRAG_EXITED:
                mGameView.onViewBGChanged(newParent.mAbsListView, false);
                break;

            case DragEvent.ACTION_DROP:
                mGameView.onDragStarted(true);
                mGameView.onViewBGChanged(newParent.mAbsListView, false);

                srcAdapter = (ItemBaseAdapter) (oldParent.getAdapter());
                destAdapter = (ItemBaseAdapter) newParent.mAbsListView.getAdapter();
                destList = ((ItemBaseAdapter)destAdapter).getWordCards();

                swapItems(srcList, passedWord);

                srcAdapter.notifyDataSetChanged();
                destAdapter.notifyDataSetChanged();

                //smooth scroll to bottom
                newParent.mAbsListView.smoothScrollToPosition(destAdapter.getCount()-1);

                break;
        }
        return true;
    }

    private void swapItems(List<WordCard> srcList, WordCard passedWord) {
        if (srcAdapter instanceof GridViewAdapter) {
            if(mGamePresenter.removeItemFromGrid(srcList, passedWord)){
                mGamePresenter.addItemToList(destList, passedWord);
            }
        } else if (destAdapter instanceof GridViewAdapter) {
            if(mGamePresenter.removeItemFromList(srcList, passedWord)){
                mGamePresenter.addItemToGrid(destList, passedWord);
            }
        } else if(mGamePresenter.removeItemFromList(srcList, passedWord)){
            mGamePresenter.addItemToList(destList, passedWord);
        }
    }
}
