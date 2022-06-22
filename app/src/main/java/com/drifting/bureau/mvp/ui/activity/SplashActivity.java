package com.drifting.bureau.mvp.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;
import com.drifting.bureau.mvp.ui.activity.user.BuildGuideActivity;
import com.drifting.bureau.storageinfo.Preferences;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import io.rong.imkit.RongIM;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.RongIMClient;

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
        mHandler.postDelayed(mHomeRunnable, 1500);
    }

    Runnable mHomeRunnable = () -> {
        if (!Preferences.isAnony()) {
            BuildGuideActivity.start(SplashActivity.this, true);
        } else {
            DiscoveryTourActivity.start(SplashActivity.this, true);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mHomeRunnable);
    }
}
