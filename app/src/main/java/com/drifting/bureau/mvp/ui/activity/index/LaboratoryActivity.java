package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;

import com.drifting.bureau.util.BitmapUtil;
import com.drifting.bureau.util.ClickUtil;

import com.drifting.bureau.util.animator.AnimatorUtil;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 卫佳琪1
 * @description 漂流科研院心理实验室
 * @time 15:40 15:40
 */

public class LaboratoryActivity extends BaseManagerActivity {

    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.toolbar_title)
    TextView mToolBarTitle;
    @BindView(R.id.iv_enter)
    ImageView mIvEnter;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, LaboratoryActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_laboratory;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        mToolBarTitle.setText("漂流科研院心理实验室");
        AnimatorUtil.ScaleAnim(mIvEnter, 2000,1f,0.8f,0.8f);
    }

    @OnClick({R.id.toolbar_back, R.id.iv_enter})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_enter:
                    Bitmap bitmap = BitmapUtil.getImageOfView(mIvBg);
                    BitmapUtil.getCropBitmaps(bitmap);
                    Intent intent = new Intent(this, LaboratoryEnterActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
            }
        }
    }

}
