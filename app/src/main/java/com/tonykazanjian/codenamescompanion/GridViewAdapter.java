package com.tonykazanjian.codenamescompanion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class GridViewAdapter extends BaseDynamicGridAdapter {

    public GridViewAdapter(Context context, List<?> items, int columnCount) {
        super(context, items, columnCount);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CardHolder holder;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_card, null);
            holder = new CardHolder(view);
            view.setTag(holder);
        } else {
            holder = (CardHolder)view.getTag();
        }

        holder.build(getItem(i).toString());
        return view;
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

        void build(String word) {
            mCardText.setText(word);
        }
    }
}
