package com.tonykazanjian.codenamescompanion.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.listeners.ItemDragListener;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class ItemListAdapter extends ItemBaseAdapter {

    public ItemListAdapter(Context context, List<WordCard> wordCards) {
        super(context, wordCards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();

            rowView = inflater.inflate(R.layout.item_word, null);

           ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.word_text);
            rowView.setTag(viewHolder);
        } else {
            ViewHolder holder = (ViewHolder) rowView.getTag();
            holder.text.setText(mWordCards.get(position).getWord());
            rowView.setOnDragListener(new ItemDragListener(mWordCards.get(position)));
        }

        return rowView;
    }

    static class ViewHolder {
        TextView text;
    }


}
