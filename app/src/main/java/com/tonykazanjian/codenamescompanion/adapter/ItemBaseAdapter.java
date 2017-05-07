package com.tonykazanjian.codenamescompanion.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tonykazanjian.codenamescompanion.WordCard;

import org.askerov.dynamicgrid.AbstractDynamicGridAdapter;
import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class ItemBaseAdapter extends BaseDynamicGridAdapter {

    Context mContext;
    List<WordCard> mWordCards;

    public ItemBaseAdapter(Context context, List<WordCard> wordCards, int columncount) {
        super(context, wordCards, columncount);
        mContext = context;
        mWordCards = wordCards;
    }

    @Override
    public int getCount() {
        return mWordCards.size();
    }

    @Override
    public Object getItem(int i) {
        return mWordCards.get(i);
    }

//    @Override
//    public long getItemId(int i) {
//        return i;
//    }

    public List<WordCard> getWordCards(){
        return mWordCards;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void reorderItems(int originalPosition, int newPosition) {

    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public boolean canReorder(int position) {
        return false;
    }
}
