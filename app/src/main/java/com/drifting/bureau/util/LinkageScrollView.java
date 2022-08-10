package com.drifting.bureau.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class LinkageScrollView extends HorizontalScrollView {
    private View mView;

    public LinkageScrollView(Context context) {
        super(context);
    }

    public LinkageScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinkageScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mView != null){
            mView.scrollTo(l,t);
        }
    }

    public void setScrollView(View view){
        mView = view;
    }
}
