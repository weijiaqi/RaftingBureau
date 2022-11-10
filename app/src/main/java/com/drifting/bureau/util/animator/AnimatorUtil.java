package com.drifting.bureau.util.animator;


import static android.view.View.TRANSLATION_Y;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;


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
     * @description 上下抖动动画
     */
    private static int[] times = new int[]{300, 600, 900};

    public static void ObjectAnim(View view, int type, int delay, int value) {
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(view, TRANSLATION_Y, -value, value, -value);
        translationYAnim.setDuration(delay);
        translationYAnim.setStartDelay(times[type]);
        translationYAnim.setRepeatCount(ValueAnimator.INFINITE);
        translationYAnim.start();
    }

    /**
     * @description 透明缩放组合动画
     */

    public static void ScaleAnim(View view, int delay, float value1, float value2, float value3) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", value1, value2, value1);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view, "scaleX", value1, value3, value1);
        objectAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, "scaleY", value1, value3, value1);
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


    /**
     * View渐现动画效果
     */
    public static void setShowAnimation(View view, int duration, boolean type) {
        AlphaAnimation mShowAnimation = new AlphaAnimation(type ? 0.0f : 1.0f, type ? 1.0f : 0.0f);
        mShowAnimation.setDuration(duration);
        mShowAnimation.setFillAfter(true);
        view.startAnimation(mShowAnimation);
    }


    /**
     * 3D翻转动画
     *
     * @param rootView
     * @author QiuLong
     */
    public static void start3DRotateAnimator(final View rootView, Animator.AnimatorListener listener) {
        final float from = 180;
        final float to = from == 0 ? 180 : 0;

        ValueAnimator rotateAnimator = ValueAnimator.ofFloat(from, to);
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                if (value <= 90) {
                    rootView.setRotationY(value);
                } else {
                    rootView.setRotationY(value - 180);
                }
            }
        });

        ValueAnimator tzAnimator = ValueAnimator.ofFloat(1, 0.6f, 1);
        tzAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                rootView.setScaleX(value);
                rootView.setScaleY(value);
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.setDuration(600);
        set.playTogether(rotateAnimator, tzAnimator);
        set.addListener(listener);
        set.start();
    }
}
