package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.danikula.videocache.HttpProxyCacheServer;
import com.drifting.bureau.R;
import com.drifting.bureau.app.application.RBureauApplication;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.base.BaseManagerActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 视频播放
 * @Author : WeiJiaQI
 * @Time : 2022/5/17 16:04
 */
public class VideoActivity extends BaseManagerActivity {
    @BindView(R.id.toolbar_title)
    TextView mToobarTitle;
    @BindView(R.id.videoView)
    VideoView mVideoView;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.current)
    TextView current;
    @BindView(R.id.seekbar)
    SeekBar seekBar;
    @BindView(R.id.pause)
    ImageView mPause;
    @BindView(R.id.showProgress)
    LinearLayout showProgress;
    @BindView(R.id.relative)
    RelativeLayout relative;
    private static final String EXTRA_URI = "extra_uri";
    private String uri;
    private double current_pos, total_duration;
    boolean isVisible = true;
    private int playProgress;
    private Handler handler=new Handler();
    private Handler handler1 = new Handler();
    private Handler mHandler=new Handler();
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
        mToobarTitle.setText("查看视频");
        if (getIntent() != null) {
            uri = getIntent().getStringExtra(EXTRA_URI);
        }
        mVideoView.setOnCompletionListener(mp -> playVideo());
        mVideoView.setOnPreparedListener(mp -> setVideoProgress());
        playVideo();
        setPause();
        hideLayout();
    }


    @OnClick({R.id.toolbar_back})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
            }
        }
    }


    // display video progress
    public void setVideoProgress() {
        //get the video duration
        current_pos = mVideoView.getCurrentPosition();
        total_duration = mVideoView.getDuration();

        //display video duration
        total.setText(timeConversion((long) total_duration));
        current.setText(timeConversion((long) current_pos));
        seekBar.setMax((int) total_duration);


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    current_pos = mVideoView.getCurrentPosition();
                    current.setText(timeConversion((long) current_pos));
                    seekBar.setProgress((int) current_pos);
                    handler1.postDelayed(this, 1000);
                } catch (IllegalStateException ed) {
                    ed.printStackTrace();
                }
            }
        };
        handler1.postDelayed(runnable, 1000);

        //seekbar change listner
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current_pos = seekBar.getProgress();
                mVideoView.seekTo((int) current_pos);
            }
        });
    }

    //pause video
    public void setPause() {
        mPause.setOnClickListener(v -> {
            if (mVideoView.isPlaying()) {
                mVideoView.pause();
                mPause.setImageResource(R.drawable.voice_play);
            } else {
                mVideoView.start();
                mPause.setImageResource(R.drawable.voice_pause);
            }
        });
    }

    public void playVideo() {
        try {
            if (uri.contains("http")){
                HttpProxyCacheServer proxy = RBureauApplication.getProxy(this);
                String proxyUrl = proxy.getProxyUrl(uri);
                mVideoView.setVideoPath(proxyUrl);
            }else {
                mVideoView.setVideoPath(uri);
            }
            mVideoView.start();
            mPause.setImageResource(R.drawable.voice_pause);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //time conversion
    public String timeConversion(long value) {
        String songTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            songTime = String.format("%02d:%02d", mns, scs);
        }
        return songTime;
    }


    // hide progress when the video is playing
    public void hideLayout() {

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                showProgress.setVisibility(View.GONE);
                isVisible = false;
            }
        };
        handler.postDelayed(runnable, 5000);

        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(runnable);
                if (isVisible) {
                    showProgress.setVisibility(View.GONE);
                    isVisible = false;
                } else {
                    showProgress.setVisibility(View.VISIBLE);
                    mHandler.postDelayed(runnable, 5000);
                    isVisible = true;
                }
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        //记录播放的progress,避免黑屏
        mVideoView.pause();
        playProgress = mVideoView.getCurrentPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (playProgress != 0) {
            mVideoView.seekTo(playProgress);
            mVideoView.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        handler1.removeCallbacksAndMessages(null);
        mHandler.removeCallbacksAndMessages(null);
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
    }

}
