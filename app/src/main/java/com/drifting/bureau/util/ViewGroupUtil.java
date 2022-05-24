package com.drifting.bureau.util;


import android.widget.LinearLayout;

/**
* @description 动态设置边距
*/

public class ViewGroupUtil {

    public static LinearLayout.LayoutParams setMargin(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(left, top, right, bottom);
        return lp;
    }
}
