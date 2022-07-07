package com.drifting.bureau.mvp.ui.activity;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;
import com.drifting.bureau.mvp.ui.activity.user.BuildGuideActivity;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.AppUtil;
import com.drifting.bureau.util.RongIMUtil;
import com.drifting.bureau.util.ToastUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import java.util.Timer;

import timber.log.Timber;


public class SplashActivity extends BaseActivity {

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
            BuildGuideActivity.start(SplashActivity.this, true);
        } else {
            RongIMUtil.getInstance().connect(Preferences.getRcToken(), new RongIMUtil.ConnectListener() {
                @Override
                public void onConnectSuccess() {
                    DiscoveryTourActivity.start(SplashActivity.this, true);
                }

                @Override
                public void onConnectError() {
                    Timber.e("融云连接失败");
                    DiscoveryTourActivity.start(SplashActivity.this, true);
                }
            });

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mHomeRunnable);
    }
}
