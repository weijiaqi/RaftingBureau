package com.drifting.bureau.mvp.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.ui.activity.index.ImagePreviewActivity;
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
    private ImageView mIvPlay, mIvVideoPlay, mIvPic, mIvVideo, mIvRecording;
    private VoiceWave mVideoView;
    private TextView mTvTime, mTvLeaveSpace, mTvMadeForHim, mTvWord, mTvTip, mTvAddTopic;
    private CommentDetailsEntity orderDetailEntity;
    private RelativeLayout mRlVideoPlay, mRlStartVoice, mRlVoicePlay;
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
        mTvWord = findViewById(R.id.tv_word);
        mIvPlay = findViewById(R.id.iv_play);
        mTvAddTopic = findViewById(R.id.tv_add_topic);
        mIvVideoPlay = findViewById(R.id.iv_video_play);
        mIvPic = findViewById(R.id.iv_pic);
        mVideoView = findViewById(R.id.videoView);
        mTvTime = findViewById(R.id.tv_time);
        mTvLeaveSpace = findViewById(R.id.tv_leave_space);
        mTvMadeForHim = findViewById(R.id.tv_made_for_him);
        mRlVoicePlay = findViewById(R.id.rl_voice_play);
        mRlVideoPlay = findViewById(R.id.rl_video_play);
        mRlStartVoice = findViewById(R.id.rl_start_voice);
        mIvVideo = findViewById(R.id.iv_video);
        mTvTip = findViewById(R.id.tv_tip);
        mIvRecording = findViewById(R.id.iv_recording);
    }


    @Override
    protected void initEvents() {
        super.initEvents();

        mTvLeaveSpace.setOnClickListener(this);
        mTvMadeForHim.setOnClickListener(this);
        mRlVideoPlay.setOnClickListener(this);
        mRlStartVoice.setOnClickListener(this);

        if (!TextUtils.isEmpty(orderDetailEntity.getTag_name())) {
            mTvAddTopic.setVisibility(View.VISIBLE);
            mTvAddTopic.setText("#"+orderDetailEntity.getTag_name());
        } else {
            mTvAddTopic.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(orderDetailEntity.getImage())) {  //视频
            mRlVideoPlay.setVisibility(View.VISIBLE);
            mIvVideoPlay.setVisibility(View.VISIBLE);
            mIvVideo.setVisibility(View.INVISIBLE);
            GlideUtil.create().loadLongImage(context, orderDetailEntity.getImage(), mIvPic);
        } else if (!TextUtils.isEmpty(orderDetailEntity.getAudio())) { //语音
            mRlVoicePlay.setVisibility(View.VISIBLE);
            mTvTip.setVisibility(View.GONE);
            mVideoView.setVisibility(View.VISIBLE);
            mIvPlay.setVisibility(View.VISIBLE);
            mIvRecording.setVisibility(View.INVISIBLE);
            totaltime = VideoUtil.getLocalVideoDuration(orderDetailEntity.getAudio());
            mTvTime.setText(totaltime + "S");
            mVideoView.setDecibel(0);
        } else if (!TextUtils.isEmpty(orderDetailEntity.getAlbum())) {  //图片
            mRlVideoPlay.setVisibility(View.VISIBLE);
            mIvVideo.setVisibility(View.INVISIBLE);
            GlideUtil.create().loadLongImage(context, orderDetailEntity.getAlbum(), mIvPic);
        } else if (!TextUtils.isEmpty(orderDetailEntity.getContent())) {  //判断文字不为空
            mTvWord.setVisibility(View.VISIBLE);
            mTvWord.setMovementMethod(ScrollingMovementMethod.getInstance());
            mTvWord.setText(orderDetailEntity.getContent());
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
                    ImagePreviewActivity.start(context,orderDetailEntity.getAlbum());
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
