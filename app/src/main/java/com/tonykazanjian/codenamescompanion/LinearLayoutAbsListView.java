package com.tonykazanjian.codenamescompanion;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.LinearLayout;

/**
 * @author Tony Kazanjian
 */

public class LinearLayoutAbsListView extends LinearLayout {

    public AbsListView mAbsListView;

    public LinearLayoutAbsListView(Context context) {
        super(context);
    }

    public LinearLayoutAbsListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayoutAbsListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAbsListView(AbsListView absListView) {
        mAbsListView = absListView;
    }
}
