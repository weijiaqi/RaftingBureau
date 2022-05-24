package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerGetSpaceStationComponent;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.GetSpaceStationContract;
import com.drifting.bureau.mvp.presenter.GetSpaceStationPresenter;


/**
 * Created on 2022/05/24 12:15
 *
 * @author 获取空间站
 * module name is GetSpaceStationActivity
 */
public class GetSpaceStationActivity extends BaseActivity<GetSpaceStationPresenter> implements GetSpaceStationContract.View {
    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, GetSpaceStationActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGetSpaceStationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_get_space_station; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
       setStatusBar(true);

        initListener();
    }

    public void initListener() {

    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}