package com.drifting.bureau.view;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.drifting.bureau.app.application.RBureauApplication;
import com.jess.arms.utils.ArmsUtils;


public class DiscoveryTransformer  implements ViewPager.PageTransformer{
    private static final float DEFAULT_MIN_SCALE = 0.7f;
    private float mMinScale = DEFAULT_MIN_SCALE;//view缩小值
    public static final float DEFAULT_CENTER = 0.9f;
    private int pageWidth;
    private int pageHeight;

    @Override
    public void transformPage(View view, float position) {
        pageWidth = view.getWidth();
        pageHeight = view.getHeight();
        view.setPivotY(pageHeight / 2);
        view.setPivotX(pageWidth / 2);

        if (position == 0.0f) {
            view.setAlpha(1f);
        } else if (position < -1.0f) {
            // [-Infinity,-1)
            // view移动到最左边，在屏幕之外
            handleInvisiblePage(view, position);
        } else if (position < 0.0f) {
            // [-1,0]
            // view移动到左边
            handleLeftPage(view, position);
        } else if (position <= 1.0f) {
            // view移动到右边
            handleRightPage(view, position);
        } else {
            // (1,+Infinity]
            //  view移动到右边，在屏幕之外
            view.setAlpha(0.5f);
            view.setPivotX(0);
            view.setPivotY(-ArmsUtils.dip2px(RBureauApplication.getContext(),350));
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
        }
    }

    public void handleLeftPage(View view, float position) {
        view.setAlpha(0.5f);
        float scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale;
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);

        view.setPivotX(pageWidth * (DEFAULT_CENTER + (DEFAULT_CENTER * -position))+ArmsUtils.dip2px(RBureauApplication.getContext(),150));
        view.setPivotY(ArmsUtils.dip2px(RBureauApplication.getContext(),700));
    }


    public void handleInvisiblePage(View view, float position) {
        view.setAlpha(0.5f);
        view.setScaleX(mMinScale);
        view.setScaleY(mMinScale);
        view.setPivotX(pageWidth * (DEFAULT_CENTER + (DEFAULT_CENTER * -position))+ArmsUtils.dip2px(RBureauApplication.getContext(),150));
        view.setPivotY(ArmsUtils.dip2px(RBureauApplication.getContext(),700));
    }



    public void handleRightPage(View view, float position) {
        view.setAlpha(0.5f);
        float scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale;
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);
        view.setPivotX(pageWidth * ((1 - position) * DEFAULT_CENTER));
        view.setPivotY(-ArmsUtils.dip2px(RBureauApplication.getContext(),350));
    }


}
