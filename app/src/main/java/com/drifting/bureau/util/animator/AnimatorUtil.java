package com.drifting.bureau.util.animator;


import static android.view.View.TRANSLATION_Y;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;


public class AnimatorUtil {

    /**
     * @description 上下抖动动画
     */
    public static void floatAnim(View view, int delay) {
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(view, TRANSLATION_Y, -6.0f, 6.0f, -6.0f);
        translationYAnim.setDuration(delay);
        translationYAnim.setRepeatCount(ValueAnimator.INFINITE);
        translationYAnim.start();
    }

}
