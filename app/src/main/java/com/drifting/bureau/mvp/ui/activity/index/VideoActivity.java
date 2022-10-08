package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;

import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.base.BaseManagerActivity;
import com.jess.arms.di.component.AppComponent;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @Description: 视频播放
 * @Author : WeiJiaQI
 * @Time : 2022/5/17 16:04
 */
public class VideoActivity extends BaseManagerActivity {

    @BindView(R.id.video_player)
    StandardGSYVideoPlayer videoPlayer;
    private static final String EXTRA_URI = "extra_uri";
    private String uri;


    public static void start(Context context, String uri, boolean closePage) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(EXTRA_URI, uri);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_video;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);

        uri = getString(EXTRA_URI);
        videoPlayer.setUp(uri, true, "");
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        videoPlayer.getBackButton().setOnClickListener(v -> {
            finish();
        });


        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
                // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
                //orientationUtils.resolveByClick();
                finish();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        ///不需要屏幕旋转
        videoPlayer.setNeedOrientationUtils(true);
        videoPlayer.startPlayLogic();
    }


    @OnClick({R.id.toolbar_back})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    onBackPressed();
                    break;
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
///       不需要回归竖屏
//        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            videoPlayer.getFullscreenButton().performClick();
//            return;
//        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}
