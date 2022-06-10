package com.drifting.bureau.util.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;


public class AnimatorUtil {

    public static void floatAnim(View view, int delay) {
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(view, "translationY", -6.0f, 6.0f, -6.0f);
        translationYAnim.setDuration(delay);
        translationYAnim.setRepeatCount(ValueAnimator.INFINITE);
        translationYAnim.start();
        AnimatorSet btnSexAnimatorSet = new AnimatorSet();
        btnSexAnimatorSet.playTogether(translationYAnim);
        btnSexAnimatorSet.setStartDelay(delay);
        btnSexAnimatorSet.start();
    }

}
