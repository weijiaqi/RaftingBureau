package com.drifting.bureau.mvp.ui.activity;


import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;

import com.drifting.bureau.mvp.ui.activity.unity.ARMetaverseCenterActivity;
import com.drifting.bureau.mvp.ui.activity.user.PullNewGuideActivity;
import com.drifting.bureau.mvp.ui.dialog.PrivacyPolicyDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.RongIMUtil;
import com.jess.arms.di.component.AppComponent;
import com.umeng.commonsdk.UMConfigure;

import timber.log.Timber;


public class SplashActivity extends BaseManagerActivity {

    private PrivacyPolicyDialog privacyPolicyDialog;
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
        if (!Preferences.isAgreePrivacy()) {
            privacyPolicyDialog = new PrivacyPolicyDialog(this);
            privacyPolicyDialog.setCancelable(false);
            privacyPolicyDialog.show();
            privacyPolicyDialog.setOnClickCallback(type -> {
                if (type == PrivacyPolicyDialog.SELECT_EXIT_APP) {
                    finish();
                } else if (type == PrivacyPolicyDialog.SELECT_ENTER_APP) {
                    Preferences.setAgreePrivacy(true);
                    //友盟隐私合规授权
                    UMConfigure.submitPolicyGrantResult(getApplicationContext(), true);
                    mHandler.postDelayed(mHomeRunnable, 500);
                }
            });
        } else {
            mHandler.postDelayed(mHomeRunnable, 1000);
        }
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
            ARMetaverseCenterActivity.start(SplashActivity.this, true);
        } else {
            DiscoveryTourActivity.start(SplashActivity.this, true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacks(mHomeRunnable);
        }
    }
}
