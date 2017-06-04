package com.tonykazanjian.codenamescompanion.listeners;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.tonykazanjian.codenamescompanion.LinearLayoutAbsListView;
import com.tonykazanjian.codenamescompanion.PassObject;
import com.tonykazanjian.codenamescompanion.Utils;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.adapter.GridViewAdapter;
import com.tonykazanjian.codenamescompanion.adapter.ItemBaseAdapter;
import com.tonykazanjian.codenamescompanion.adapter.ItemListAdapter;
import com.tonykazanjian.codenamescompanion.main.MainActivityPresenter;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class ViewDragListener implements View.OnDragListener {

    private BaseAdapter srcAdapter;
    private BaseAdapter destAdapter;
    private List<WordCard> destList;

    private MainActivityPresenter mMainActivityPresenter;

    public ViewDragListener(MainActivityPresenter mainActivityPresenter) {
        mMainActivityPresenter = mainActivityPresenter;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        Utils.Constants.sIsItemDragging = true;
        PassObject passObject = (PassObject) dragEvent.getLocalState();
        View listView = passObject.view;
        WordCard passedWord = passObject.mWordCard;
        List<WordCard> srcList = passObject.mSourceList;
        AbsListView oldParent = (AbsListView)listView.getParent();

        switch (dragEvent.getAction()) {

            case DragEvent.ACTION_DROP:
                Utils.Constants.sIsItemDragging = false;

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
            if(mMainActivityPresenter.removeItemFromGrid(srcList, passedWord)){
                mMainActivityPresenter.addItemToList(destList, passedWord);
            }
        } else if (destAdapter instanceof GridViewAdapter) {
            if(mMainActivityPresenter.removeItemFromList(srcList, passedWord)){
                mMainActivityPresenter.addItemToGrid(destList, passedWord);
            }
        } else if(mMainActivityPresenter.removeItemFromList(srcList, passedWord)){
            mMainActivityPresenter.addItemToList(destList, passedWord);
        }
    }
}
