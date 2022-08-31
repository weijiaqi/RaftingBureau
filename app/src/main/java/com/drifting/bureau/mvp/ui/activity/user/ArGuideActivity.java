package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
<<<<<<< HEAD
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.mvp.ui.activity.home.ArCenterConsoleActivity;
=======
import com.drifting.bureau.base.BaseActivity;
import com.drifting.bureau.mvp.ui.activity.home.ArCenterConsoleActivity;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;
>>>>>>> origin/dev
import com.drifting.bureau.util.ARCoreUtil;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.di.component.AppComponent;

import butterknife.OnClick;

/**
 * @Description: AR引导页面
 * @Author : WeiJiaQI
 * @Time : 2022/8/23 17:43
 */
<<<<<<< HEAD
public class ArGuideActivity extends BaseManagerActivity {
=======
public class ArGuideActivity extends BaseActivity {
>>>>>>> origin/dev

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, ArGuideActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ar_guide;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
    }

    @OnClick({R.id.tv_no_like, R.id.tv_enter})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_no_like:
                    finish();
                    break;
                case R.id.tv_enter:  //进入AR版本
                    if (ARCoreUtil.checkArCoreAvailability(this)) {
                        if(ClaimPlanetActivity.claimPlanetActivity!=null){
                            ClaimPlanetActivity.claimPlanetActivity.finish();
                        }
                        ArCenterConsoleActivity.start(this,true);
                    }
                    break;
            }
        }
    }

}
