package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.animation.ProgressBarAnimation;
import com.jess.arms.base.BaseDialog;


/**
 * @Description: 制作进度
 * @Author : WeiJiaQI
 * @Time : 2022/5/25 14:53
 */
public class MakeScheduleDialog extends BaseDialog {
    private ProgressBar makebar;
    private TextView mTvPasserNum;

    public static final int SELECT_FINISH = 0x01;

    public MakeScheduleDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        makebar = findViewById(R.id.tv_make);
        mTvPasserNum = findViewById(R.id.tv_passer_num);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        ProgressBarAnimation anim = new ProgressBarAnimation(makebar, mTvPasserNum, 0, 100);
        anim.setDuration(1000 * 5);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                 dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_FINISH);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        makebar.startAnimation(anim);
    }

    @Override
    protected int getContentView() {
        getWindow().setDimAmount(0.9f);
        return R.layout.dialog_make_schedule;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }


}
