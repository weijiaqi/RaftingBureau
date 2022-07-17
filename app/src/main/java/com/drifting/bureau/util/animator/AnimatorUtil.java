package com.drifting.bureau.util.animator;


import static android.view.View.TRANSLATION_Y;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;


public class AnimatorUtil {

    /**
     * @description 上下抖动动画
     */
    public static void floatAnim(View view, int delay,float value) {
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(view, TRANSLATION_Y, -value, value, -value);
        translationYAnim.setDuration(delay);
        translationYAnim.setRepeatCount(ValueAnimator.INFINITE);
        translationYAnim.start();
    }

    public static void TransAnim(View view, View view2, View view3, int delay) {
        ValueAnimator animator = ValueAnimator.ofInt(300, -3000);
        animator.setDuration(delay);
        animator.setRepeatCount(-1);
        animator.addUpdateListener(valueAnimator -> {
            int value = (int) valueAnimator.getAnimatedValue();
            view.setX(value + 800);
            view.setY(-value+200);
            view2.setX(value + 1600);
            view2.setY(-value);
            view3.setX(value + 2400);
            view3.setY(-value);
        });
        animator.start();
    }

}
