package com.drifting.bureau.mvp.ui.activity;


import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
<<<<<<< HEAD
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.mvp.ui.activity.home.ArCenterConsoleActivity;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;

import com.drifting.bureau.mvp.ui.activity.user.PullNewGuideActivity;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.RongIMUtil;
=======
import com.drifting.bureau.mvp.ui.activity.home.ArCenterConsoleActivity;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;
import com.drifting.bureau.mvp.ui.activity.user.ArGuideActivity;
import com.drifting.bureau.mvp.ui.activity.user.BuildGuideActivity;
import com.drifting.bureau.mvp.ui.activity.user.ClaimPlanetActivity;
import com.drifting.bureau.mvp.ui.activity.user.PullNewGuideActivity;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.RongIMUtil;
import com.jess.arms.base.BaseActivity;
>>>>>>> origin/dev
import com.jess.arms.di.component.AppComponent;

import timber.log.Timber;


<<<<<<< HEAD
public class SplashActivity extends BaseManagerActivity {
=======
public class SplashActivity extends BaseActivity {
>>>>>>> origin/dev

    private Handler mHandler = new Handler();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mHandler.postDelayed(mHomeRunnable, 1000);
    }

    Runnable mHomeRunnable = () -> {
        if (!Preferences.isAnony()) {
            PullNewGuideActivity.start(SplashActivity.this, true);
        } else {
            RongIMUtil.getInstance().connect(Preferences.getRcToken(), new RongIMUtil.ConnectListener() {
                @Override
                public void onConnectSuccess() {
                    startMainActivity();
                }

                @Override
                public void onConnectError() {
                    Timber.e("融云连接失败");
                    startMainActivity();
                }
            });
        }
    };


    public void startMainActivity() {
        if (Preferences.isARModel()) {
            ArCenterConsoleActivity.start(SplashActivity.this, true);
        } else {
            DiscoveryTourActivity.start(SplashActivity.this, true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mHomeRunnable);
    }
}
