package com.drifting.bureau.mvp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.mvp.ui.activity.home.NewDiscoveryTourActivity;
import com.drifting.bureau.mvp.ui.activity.unity.ARMetaverseCenterActivity;
import com.drifting.bureau.mvp.ui.activity.user.PullNewGuideActivity;
import com.drifting.bureau.mvp.ui.dialog.PrivacyPolicyDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.RongIMUtil;
import com.drifting.bureau.video.EmptyControlVideo;
import com.jess.arms.di.component.AppComponent;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.umeng.commonsdk.UMConfigure;
import butterknife.BindView;
import timber.log.Timber;


public class SplashActivity extends BaseManagerActivity {

    @BindView(R.id.video_player)
    EmptyControlVideo mVieoPlayer;

    @BindView(R.id.rl_center)
    RelativeLayout mRlCenter;
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
                    mVieoPlayer.setUp("https://v.metapeza.com/afile/vedio/start.mp4", true, "");
                    mVieoPlayer.startPlayLogic();
                    mVieoPlayer.setVideoAllCallBack(new GSYSampleCallBack() {

                        @Override
                        public void onAutoComplete(String url, Object... objects) {
                            super.onAutoComplete(url, objects);
                            startActivity();
                        }
                    });
                }
            });
        } else {
            mRlCenter.setBackgroundResource(R.drawable.layer_splash);
            mHandler.postDelayed(mHomeRunnable, 500);
        }
    }

    Runnable mHomeRunnable = () -> {
        mVieoPlayer.setVisibility(View.GONE);
        startActivity();
    };




    public void startActivity() {
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
    }


    public void startMainActivity() {
        if (Preferences.isARModel()) {
            ARMetaverseCenterActivity.start(SplashActivity.this, true);
        } else {
            NewDiscoveryTourActivity.start(SplashActivity.this, true);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mVieoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVieoPlayer.onVideoResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (mHandler != null) {
            mHandler.removeCallbacks(mHomeRunnable);
        }
    }
}
