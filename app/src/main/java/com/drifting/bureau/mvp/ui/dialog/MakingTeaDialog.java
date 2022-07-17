package com.drifting.bureau.mvp.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.ui.activity.index.SelectImageActivity;
import com.drifting.bureau.mvp.ui.activity.index.VideoActivity;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.view.VoiceWave;
import com.jess.arms.base.BaseDialog;

/**
 * @description 为他制作
 */

public class MakingTeaDialog extends BaseDialog implements View.OnClickListener {
    private LinearLayout mRlVoicePlay;

    private ImageView mIvPlay, mIvVoiceDelete, mIvVideoPlay, mIvPic;
    private VoiceWave mVideoView;
    private TextView mTvTime, mTvLeaveSpace, mTvMadeForHim,mTtWord;
    private CommentDetailsEntity orderDetailEntity;
    private RelativeLayout mRlVideoPlay,mRlStartVoice;
    private Context context;
    private int totaltime;

    public static final int SELECT_FINISH = 0x01;
    public static final int SELECT_CANCEL = 0x02;

    public MakingTeaDialog(@NonNull Context context, CommentDetailsEntity orderDetailEntity) {
        super(context);
        this.context = context;
        this.orderDetailEntity = orderDetailEntity;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_making_tea;
    }

    @Override
    protected void initView() {
        super.initView();
        mTtWord = findViewById(R.id.tv_word);
        mIvPlay = findViewById(R.id.iv_play);
        mIvVoiceDelete = findViewById(R.id.iv_voice_delete);
        mIvVideoPlay = findViewById(R.id.iv_video_play);
        mIvPic = findViewById(R.id.iv_pic);
        mVideoView = findViewById(R.id.videoView);
        mTvTime = findViewById(R.id.tv_time);
        mTvLeaveSpace = findViewById(R.id.tv_leave_space);
        mTvMadeForHim = findViewById(R.id.tv_made_for_him);
        mRlVoicePlay = findViewById(R.id.rl_voice_play);
        mRlVideoPlay = findViewById(R.id.rl_video_play);
        mRlStartVoice=findViewById(R.id.rl_start_voice);
    }


    @Override
    protected void initEvents() {
        super.initEvents();
        mIvVoiceDelete.setOnClickListener(this);
        mTvLeaveSpace.setOnClickListener(this);
        mTvMadeForHim.setOnClickListener(this);
        mRlVideoPlay.setOnClickListener(this);
        mRlStartVoice.setOnClickListener(this);
        if (TextUtils.isEmpty(orderDetailEntity.getContent())) {
            mTtWord.setText("收到一份来自元宇宙的电波传递");
        } else {
            mTtWord.setText(orderDetailEntity.getContent());
        }

        if (!TextUtils.isEmpty(orderDetailEntity.getAlbum())) {  //图片
            mRlVideoPlay.setVisibility(View.VISIBLE);
            mIvVideoPlay.setVisibility(View.GONE);
            GlideUtil.create().loadLongImage(context, orderDetailEntity.getAlbum(), mIvPic);
        }


        if (!TextUtils.isEmpty(orderDetailEntity.getAudio())) { //语音
            mRlVoicePlay.setVisibility(View.VISIBLE);
            mIvVoiceDelete.setVisibility(View.GONE);
            totaltime = VideoUtil.getLocalVideoDuration(orderDetailEntity.getAudio());
            mTvTime.setText(totaltime + "S");
            mVideoView.setDecibel(0);
        }
        if (!TextUtils.isEmpty(orderDetailEntity.getImage())) {  //视频
            mRlVideoPlay.setVisibility(View.VISIBLE);
            mIvVideoPlay.setVisibility(View.VISIBLE);
            GlideUtil.create().loadLongImage(context, orderDetailEntity.getImage(), mIvPic);
        }

    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_start_voice: //语音播放
                PermissionDialog.startVoicePlay((Activity) context, orderDetailEntity.getAudio(), totaltime, mIvPlay, mVideoView, mTvTime);
                break;
            case R.id.rl_video_play:  //视频播放
                if (!TextUtils.isEmpty(orderDetailEntity.getVedio())) {
                    VideoActivity.start(context, orderDetailEntity.getVedio(), false);
                } else {//图片
                    SelectImageActivity.start(context, orderDetailEntity.getAlbum(), false);
                }
                break;
            case R.id.tv_leave_space:   //丢回太空
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_CANCEL);
                }
                break;
            case R.id.tv_made_for_him: //制作
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_FINISH);
                }
                break;
        }
    }
}
