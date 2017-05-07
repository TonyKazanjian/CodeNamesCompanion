package com.tonykazanjian.codenamescompanion.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.listeners.grid.GridItemDragListener;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class GridViewAdapter extends ItemBaseAdapter {

    List<WordCard> mWordCards;
    Context mContext;

    public GridViewAdapter(Context context, List<WordCard> words, int columnCount) {
        super(context, words, columnCount);
        mWordCards = words;
        mContext = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CardHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_card, null);
            holder = new CardHolder(view);
            view.setTag(holder);
        } else {
            holder = (CardHolder)view.getTag();
        }

        if (mWordCards.size() != 0) {
            holder.build(mWordCards.get(i));

            view.setOnDragListener(new GridItemDragListener(mWordCards.get(i)));
        }
        return view;
    }

    public List<WordCard> getWordCards() {
        return mWordCards;
    }

    private class CardHolder extends RecyclerView.ViewHolder {

        TextView mCardText;
        ImageView mCloseBtn;
        ImageView mEditBtn;


        public CardHolder(View itemView) {
            super(itemView);
            mCardText = (TextView) itemView.findViewById(R.id.card_text);
            mCloseBtn = (ImageView) itemView.findViewById(R.id.close_btn);
            mEditBtn = (ImageView) itemView.findViewById(R.id.edit_btn);
        }

        void build(WordCard word) {
            mCardText.setText(word.getWord());
        }
    }
}
