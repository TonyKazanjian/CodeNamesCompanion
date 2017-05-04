package com.tonykazanjian.codenamescompanion.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.WordCard;

import java.util.List;

import static android.R.id.list;

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

            //TODO - create layout for row
            rowView = inflater.inflate(R.layout.row, null);

           ViewHolder viewHolder = new ViewHolder();
//            viewHolder.icon = (ImageView) rowView.findViewById(R.id.rowImageView);
//            viewHolder.text = (TextView) rowView.findViewById(R.id.rowTextView);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.icon.setImageDrawable(list.get(position).ItemDrawable);
        holder.text.setText(list.get(position).ItemString);

        rowView.setOnDragListener(new ItemOnDragListener(list.get(position)));

        return rowView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView text;
    }


}
