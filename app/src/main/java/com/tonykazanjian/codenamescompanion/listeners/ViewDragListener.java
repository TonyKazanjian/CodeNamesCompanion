package com.tonykazanjian.codenamescompanion.listeners;

import android.view.DragEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.tonykazanjian.codenamescompanion.LinearLayoutAbsListView;
import com.tonykazanjian.codenamescompanion.PassObject;
import com.tonykazanjian.codenamescompanion.UserPreferences;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.adapter.GridViewAdapter;
import com.tonykazanjian.codenamescompanion.adapter.ItemBaseAdapter;
import com.tonykazanjian.codenamescompanion.main.GamePresenter;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class ViewDragListener implements View.OnDragListener {

    private BaseAdapter srcAdapter;
    private BaseAdapter destAdapter;
    private List<WordCard> destList;

    private GamePresenter mGamePresenter;

    public ViewDragListener(GamePresenter gamePresenter) {
        mGamePresenter = gamePresenter;
    }

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

        UserPreferences.setSrcWordList(srcList);
        UserPreferences.setDestWordList(destList);
    }
}
