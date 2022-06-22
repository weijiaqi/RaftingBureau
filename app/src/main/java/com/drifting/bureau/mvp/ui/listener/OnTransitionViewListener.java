package com.drifting.bureau.mvp.ui.listener;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.rb.core.tab.view.indicator.Indicator;
import com.rb.core.tab.view.utils.ColorGradient;


/**
 * 自定义tab
 */
public class OnTransitionViewListener implements Indicator.OnTransitionListener {
    private float selectSize = -1;
    private float unSelectSize = -1;
    private ColorGradient gradient;
    private float dFontFize = -1;

    private boolean isPxSize = false;

    public OnTransitionViewListener() {
        super();
    }

    public OnTransitionViewListener(float selectSize, float unSelectSize, int selectColor, int unSelectColor) {
        super();
        setColor(selectColor, unSelectColor);
        setSize(selectSize, unSelectSize);
    }

    public final OnTransitionViewListener setSize(float selectSize, float unSelectSize) {
        isPxSize = false;
        this.selectSize = selectSize;
        this.unSelectSize = unSelectSize;
        this.dFontFize = selectSize - unSelectSize;
        return this;
    }

    public final OnTransitionViewListener setValueFromRes(Context context, int selectColorId, int unSelectColorId, int selectSizeId,
                                                          int unSelectSizeId) {
        setColorId(context, selectColorId, unSelectColorId);
        setSizeId(context, selectSizeId, unSelectSizeId);
        return this;
    }


    public final OnTransitionViewListener setValueFromColor(Context context, int selectColor, int unSelectColor, int selectSizeId,
                                                          int unSelectSizeId) {
        gradient = new ColorGradient(unSelectColor, selectColor, 100);
        setSizeId(context, selectSizeId, unSelectSizeId);
        return this;
    }

    public final OnTransitionViewListener setColorId(Context context, int selectColorId, int unSelectColorId) {
        Resources res = context.getResources();
        setColor(res.getColor(selectColorId), res.getColor(unSelectColorId));
        return this;
    }

    public final OnTransitionViewListener setSizeId(Context context, int selectSizeId, int unSelectSizeId) {
        Resources res = context.getResources();
        setSize(res.getDimensionPixelSize(selectSizeId), res.getDimensionPixelSize(unSelectSizeId));
        isPxSize = true;
        return this;
    }

    public final OnTransitionViewListener setColor(int selectColor, int unSelectColor) {
        gradient = new ColorGradient(unSelectColor, selectColor, 100);
        return this;
    }

    @Override
    public void onTransition(View view, int position, float selectPercent) {
        TextView selectTextView = view.findViewById(R.id.tv_tab_top_title);
        if (selectTextView == null) {
            return;
        }
        if (gradient != null) {
            selectTextView.setTextColor(gradient.getColor((int) (selectPercent * 100)));
        }
        if (unSelectSize > 0 && selectSize > 0) {
            if (isPxSize) {
                selectTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, unSelectSize + dFontFize * selectPercent);
            } else {
                selectTextView.setTextSize(unSelectSize + dFontFize * selectPercent);
            }

        }
    }

}
