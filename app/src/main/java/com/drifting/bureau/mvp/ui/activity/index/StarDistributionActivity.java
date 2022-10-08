package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.di.component.DaggerStarDistributionComponent;
import com.drifting.bureau.mvp.contract.PlanetarySelectContract;
import com.drifting.bureau.mvp.model.entity.PlanetaryDetailEntity;
import com.drifting.bureau.mvp.presenter.PlanetarySelectPresenter;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 星域分布
 * @Author : WeiJiaQI
 * @Time : 2022/9/29 14:06
 */
public class StarDistributionActivity extends BaseManagerActivity<PlanetarySelectPresenter> implements PlanetarySelectContract.View {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.toolbar_title)
    TextView mToolBarTitle;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, StarDistributionActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStarDistributionComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_star_distribution;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        mToolBarTitle.setText("星域分布");

    }

    @OnClick({R.id.toolbar_back, R.id.tv_star_field_go})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_star_field_go:
                    LaboratoryActivity.start(this, true);
                    break;
            }
        }
    }


    @Override
    public void onPlanetaryDetailSuccess(PlanetaryDetailEntity entity) {

    }

    @Override
    public void onNetError() {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }
}
