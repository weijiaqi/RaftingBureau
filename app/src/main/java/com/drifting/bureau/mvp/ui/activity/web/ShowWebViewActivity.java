package com.drifting.bureau.mvp.ui.activity.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.mvp.ui.activity.user.BuildGuideActivity;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

/**
 * @Description:  H5界面
 * @Author     : WeiJiaQI
 * @Time       : 2022/5/9 11:51
 */
public class ShowWebViewActivity  extends BaseActivity {
    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, ShowWebViewActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
