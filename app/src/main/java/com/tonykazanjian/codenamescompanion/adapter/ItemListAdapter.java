package com.tonykazanjian.codenamescompanion.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.listeners.ItemDragListener;
import com.tonykazanjian.codenamescompanion.main.GameView;

import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class ItemListAdapter extends ItemBaseAdapter {

    public ItemListAdapter(Context context, List<WordCard> wordCards, GameView gameView) {
        super(context, wordCards, gameView);
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // reuse views
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_word, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (i <= mWordCards.size()-1){
            viewHolder.text.setText(mWordCards.get(i).getWord());
            viewHolder.mCloseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mWordCards.remove(i);
                    notifyDataSetChanged();
                }
            });
            convertView.setOnDragListener(new ItemDragListener(mWordCards.get(i), mGameView));
        }

        return convertView;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView mCloseBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.word_text);
            mCloseBtn = (ImageView)itemView.findViewById(R.id.close_btn);

            expandTouchArea(itemView, mCloseBtn, 20);
        }
    }

    public void expandTouchArea(final View bigView, final View smallView, final int extraPadding) {
        bigView.post(new Runnable() {
            @Override
            public void run() {
                Rect rect = new Rect();
                smallView.getHitRect(rect);
                rect.top -= extraPadding;
                rect.left -= extraPadding;
                rect.right += extraPadding;
                rect.bottom += extraPadding;
                bigView.setTouchDelegate(new TouchDelegate(rect, smallView));
            }
        });
    }


}
