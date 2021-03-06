package com.tonykazanjian.codenamescompanion.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.main.GameView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class ItemBaseAdapter extends BaseAdapter {

    Context mContext;
    List<WordCard> mWordCards;
    GameView mGameView;

    public ItemBaseAdapter(Context context, List<WordCard> wordCards, GameView gameView) {
        mContext = context;
        mWordCards = wordCards;
        mGameView = gameView;
    }

    @Override
    public int getCount() {
        return mWordCards.size();
    }

    @Override
    public Object getItem(int i) {
        return mWordCards.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public List<WordCard> getWordCards(){
        return mWordCards;
    }

    public void clearWordCards() {
        mWordCards.clear();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
