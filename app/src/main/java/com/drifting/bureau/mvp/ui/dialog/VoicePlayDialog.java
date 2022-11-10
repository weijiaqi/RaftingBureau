package com.drifting.bureau.mvp.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.view.VoiceWave;
import com.jess.arms.base.BaseDialog;

/**
 * @description   语音播放对话框
 * @author 卫佳琪1
 * @time 19:25 19:25
 */

public class VoicePlayDialog extends BaseDialog implements View.OnClickListener{

    private VoiceWave videoView;
    private ImageView mIvPlay;
    private TextView mTvTime;
    private String content;
    private Context context;
    private int time;

    public VoicePlayDialog(@NonNull Context context,String content,int time) {
        super(context);
        this.context=context;
        this.content=content;
        this.time=time;
        init();
    }

    public void init(){
        setOnShowListener(dialogInterface -> {
            videoView.setDecibel(0);
            startPlay();
        });

        setOnDismissListener(dialog -> VideoUtil.stop(videoView,mIvPlay,mTvTime,time));
    }

    @Override
    protected void initView() {
        super.initView();
        videoView = findViewById(R.id.videoView);
        mIvPlay=findViewById(R.id.iv_play);
        mTvTime=findViewById(R.id.tv_time);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mIvPlay.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_voice_play;
    }

    @Override
    protected float getDialogWith() {
        return 0.7f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_play:
                startPlay();
                break;
        }
    }

    public void startPlay(){
        PermissionDialog.startVoicePlay((Activity) context,content, time, mIvPlay, videoView, mTvTime);
    }
}
