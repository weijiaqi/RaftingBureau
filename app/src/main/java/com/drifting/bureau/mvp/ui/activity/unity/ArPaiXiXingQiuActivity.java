package com.drifting.bureau.mvp.ui.activity.unity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.di.component.AppComponent;
import com.unity3d.player.IUnityPlayerLifecycleEvents;
import com.unity3d.player.MultiWindowSupport;
import com.unity3d.player.UnityPlayer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 卫佳琪1
 * @description 派系星球
 * @time 18:39 18:39
 */

public class ArPaiXiXingQiuActivity extends BaseManagerActivity implements IUnityPlayerLifecycleEvents {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.ll_add)
    LinearLayout mLlAdd;
    @BindView(R.id.rl_anim)
    ImageView mRlAnim;
    @BindView(R.id.tv_change_mode)
    ShapeTextView mTvChangeMode;
    @BindView(R.id.rl_right)
    RelativeLayout mRlright;
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

    private UserInfoEntity userInfoEntity;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, ArPaiXiXingQiuActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ar_paixixingqiu;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        setStatusBarHeight(mTvBar);
        mRlright.setVisibility(View.GONE);
        mTvChangeMode.setText("返回");

        String cmdLine = updateUnityCommandLineArguments(getIntent().getStringExtra("unity"));
        getIntent().putExtra("unity", cmdLine);
        mUnityPlayer = new UnityPlayer(this, this);
        View playerView = mUnityPlayer.getView();
        mLlAdd.addView(playerView);
        mUnityPlayer.requestFocus();
        AnimationDrawable anim = (AnimationDrawable) mRlAnim.getBackground();
        anim.start();
        new Handler().postDelayed(() -> {
            anim.stop();
            mRlAnim.setVisibility(View.GONE);
        }, 4500);

        RequestUtil.create().userplayer(Preferences.getUserId(), entity -> {
            if (entity != null && entity.getCode() == 200) {
                userInfoEntity=entity.getData();
                mUnityPlayer.UnitySendMessage("Main Camera", "OpenPaiXiXingQiu", userInfoEntity.getPlanet().getLevel() + "");
            }
        });
    }


    //进入个人星球
    public void GeRenXingQiu() {
        mUnityPlayer.UnitySendMessage("Main Camera", "ClosePaiXiXingQiu", "");
        mUnityPlayer.UnitySendMessage("Main Camera", "OpenGeRenXingQiu", "");
        if (userInfoEntity != null) {
            mUnityPlayer.UnitySendMessage("Main Camera", "XunZhang", userInfoEntity.getUser().getStatus() + "");
        }
    }


    //进入派系星球
    public void PaiXiXingQiu() {
        mUnityPlayer.UnitySendMessage("Main Camera", "CloseGeRenXingQiu", "");
        if (userInfoEntity!=null){
            mUnityPlayer.UnitySendMessage("Main Camera", "OpenPaiXiXingQiu", userInfoEntity.getPlanet().getLevel() + "");
        }
    }

    @OnClick({R.id.tv_change_mode})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_change_mode:
                    finish();
                    break;
            }
        }
    }


    // Override this in your custom UnityPlayerActivity to tweak the command line arguments passed to the Unity Android Player
    // The command line arguments are passed as a string, separated by spaces
    // UnityPlayerActivity calls this from 'onCreate'
    // Supported: -force-gles20, -force-gles30, -force-gles31, -force-gles31aep, -force-gles32, -force-gles, -force-vulkan
    // See https://docs.unity3d.com/Manual/CommandLineArguments.html
    // @param cmdLine the current command line arguments, may be null
    // @return the modified command line string or null
    protected String updateUnityCommandLineArguments(String cmdLine) {
        return cmdLine;
    }


    // When Unity player unloaded move task to background
    @Override
    public void onUnityPlayerUnloaded() {
        moveTaskToBack(true);
    }

    // Callback before Unity player process is killed
    @Override
    public void onUnityPlayerQuitted() {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mUnityPlayer.newIntent(intent);
    }


    // Quit Unity
    @Override
    protected void onDestroy() {
        mUnityPlayer.destroy();
        RequestUtil.create().disDispose();
        super.onDestroy();
    }

    // If the activity is in multi window mode or resizing the activity is allowed we will use
    // onStart/onStop (the visibility callbacks) to determine when to pause/resume.
    // Otherwise it will be done in onPause/onResume as Unity has done historically to preserve
    // existing behavior.
    @Override
    protected void onStop() {
        super.onStop();

        if (!MultiWindowSupport.getAllowResizableWindow(this))
            return;

        mUnityPlayer.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!MultiWindowSupport.getAllowResizableWindow(this))
            return;

        mUnityPlayer.resume();
    }

    // Pause Unity
    @Override
    protected void onPause() {
        super.onPause();

        MultiWindowSupport.saveMultiWindowMode(this);

        if (MultiWindowSupport.getAllowResizableWindow(this))
            return;

        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override
    protected void onResume() {
        super.onResume();

        if (MultiWindowSupport.getAllowResizableWindow(this) && !MultiWindowSupport.isMultiWindowModeChangedToTrue(this))
            return;

        mUnityPlayer.resume();
    }

    // Low Memory Unity
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL) {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }
}
