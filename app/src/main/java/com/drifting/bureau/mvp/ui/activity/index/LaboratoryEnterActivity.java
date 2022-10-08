package com.drifting.bureau.mvp.ui.activity.index;

import static java.util.stream.Collectors.toList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;

import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;
import com.drifting.bureau.util.BitmapUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.animator.AnimatorUtil;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 漂流科研院心理实验室
 * @Author : WeiJiaQI
 * @Time : 2022/9/23 16:55
 */
public class LaboratoryEnterActivity extends BaseManagerActivity {

    @BindView(R.id.ll_toolbar)
    LinearLayout mLlToolBar;
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.toolbar_title)
    TextView mToolBarTitle;
    @BindView(R.id.iv_img_left)
    ImageView ivLeft;
    @BindView(R.id.iv_img_right)
    ImageView ivRight;
    @BindView(R.id.coverImageContainer)
    LinearLayout mCoverImage;
    @BindView(R.id.fl_find_system)
    FrameLayout mFlFindSystem;
    @BindView(R.id.rl_center)
    RelativeLayout mRlCenter;
    private Bitmap[] bitmapArray;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_laboratory_enter;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        mToolBarTitle.setText("漂流科研院心理实验室");
        bitmapArray = BitmapUtil.getBitmaps();
        ivLeft.setImageBitmap(bitmapArray[0]);
        ivRight.setImageBitmap(bitmapArray[1]);
        ivLeft.animate()
                .setDuration(1000)
                .translationX(-bitmapArray[0].getWidth())
                .start();

        ivRight.animate()
                .setDuration(1000)
                .translationX(bitmapArray[1].getWidth()).withEndAction(new Runnable() {
            @Override
            public void run() {
                if (mCoverImage != null) {
                    mCoverImage.setVisibility(View.GONE);
                    mFlFindSystem.setVisibility(View.VISIBLE);
                    AnimatorUtil.setShowAnimation(mFlFindSystem, 3000, true);
                }
            }
        }).start();


        AnimatorUtil.setShowAnimation(mRlCenter, 3000, true);
        AnimatorUtil.setShowAnimation(mLlToolBar, 3000, true);
        AnimatorUtil.ScaleAnim(mFlFindSystem, 2000, 1f, 0.8f, 0.9f);



    }


    @OnClick({R.id.toolbar_back, R.id.fl_find_system})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.fl_find_system:
                   AnswerTestActivity.start(this, true);
                    break;
            }
        }
    }

}
