package com.drifting.bureau.mvp.ui.activity.user;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerMakingRecordComponent;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.MakingRecordContract;
import com.drifting.bureau.mvp.presenter.MakingRecordPresenter;


/**
 * Created on 2022/05/27 14:39
 * @author 制作记录
 * module name is MakingRecordActivity
 */
public class MakingRecordActivity extends BaseActivity<MakingRecordPresenter> implements MakingRecordContract.View {

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, MakingRecordActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMakingRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState){
        return R.layout.activity_refresh_layout; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //setToolBar(toolbar, "MakingRecord");

        initListener();
    }

    public void initListener() {

    }

    public Activity getActivity(){
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}