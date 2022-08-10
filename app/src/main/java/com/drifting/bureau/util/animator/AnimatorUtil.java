package com.drifting.bureau.util.animator;


import static android.view.View.TRANSLATION_Y;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;


public class AnimatorUtil {

    /**
     * @description 上下抖动动画
     */
    public static void floatAnim(View view, int delay, int value) {
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(view, TRANSLATION_Y, -value, value, -value);
        translationYAnim.setDuration(delay);
        translationYAnim.setRepeatCount(ValueAnimator.INFINITE);
        translationYAnim.start();
    }


    /**
     * @description 透明缩放组合动画
     */

    public static void ScaleAnim(View view, int delay) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.2f, 1f);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view, "scaleX", 1, 0.5f, 1);
        objectAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, "scaleY", 1, 0.5f, 1);
        objectAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator);
        animatorSet.setDuration(delay);
        animatorSet.start();
    }


    /**
     * @description 透明动画
     */
    public static void AlphaAnim(View view, int delay) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.1f, 1f);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setDuration(delay);
        objectAnimator.start();
    }

}
