package com.drifting.bureau.mvp.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.OrderDetailEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.ui.activity.index.VideoActivity;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.view.VoiceWave;
import com.jess.arms.base.BaseDialog;

/**
 * @author 卫佳琪1
 * @description 查看订单
 * @time 10:49 10:49
 */

public class SelectOrderDialog extends BaseDialog implements View.OnClickListener {

    public static final int SELECT_FINISH = 0x01;
    public static final int SELECT_CANCEL = 0x02;

    private TextView mTvLeaveSpace, mTvMadeForHim, mTvname, mTvPlanet, mTvIdentity, mTvWord, mTvTime;
    private RelativeLayout mRlVoicePlay, mRlVideoPlay;
    private ImageView mIvPlay, mIvPic;
    private VoiceWave mVideoView;

    private UserInfoEntity userInfoEntity;
    private OrderDetailEntity orderDetailEntity;
    private int totaltime;
    private Context context;

    public SelectOrderDialog(@NonNull Context context, UserInfoEntity userInfoEntity, OrderDetailEntity orderDetailEntity) {
        super(context);
        this.context = context;
        this.userInfoEntity = userInfoEntity;
        this.orderDetailEntity = orderDetailEntity;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mTvLeaveSpace = findViewById(R.id.tv_leave_space);
        mTvMadeForHim = findViewById(R.id.tv_made_for_him);
        mTvname = findViewById(R.id.tv_name);
        mTvPlanet = findViewById(R.id.tv_planet);
        mTvIdentity = findViewById(R.id.tv_identity);
        mRlVoicePlay = findViewById(R.id.rl_voice_play);
        mRlVideoPlay = findViewById(R.id.rl_video_play);
        mTvWord = findViewById(R.id.tv_word);
        mTvTime = findViewById(R.id.tv_time);
        mIvPlay = findViewById(R.id.iv_play);
        mVideoView = findViewById(R.id.videoView);
        mIvPic = findViewById(R.id.iv_pic);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvLeaveSpace.setOnClickListener(this);
        mTvMadeForHim.setOnClickListener(this);
        mIvPlay.setOnClickListener(this);
        mRlVideoPlay.setOnClickListener(this);
        mTvname.setText("昵称：" + userInfoEntity.getUser().getName());
        mTvPlanet.setText(userInfoEntity.getPlanet().getName());
        mTvIdentity.setText(userInfoEntity.getUser().getLevel_name());
        switch (orderDetailEntity.getType_id()) {
            case 1:
                mTvWord.setVisibility(View.VISIBLE);
                mTvWord.setText(orderDetailEntity.getContent());
                break;
            case 2:
                mVideoView.setDecibel(0);
                totaltime=VideoUtil.getLocalVideoDuration(orderDetailEntity.getContent()) + 1;
                mTvTime.setText(totaltime + "S");
                mRlVoicePlay.setVisibility(View.VISIBLE);
                break;
            case 3:
                mRlVideoPlay.setVisibility(View.VISIBLE);
                GlideUtil.create().loadNormalPic(context, orderDetailEntity.getImage(), mIvPic);
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_select_order;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_leave_space:
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_CANCEL);
                }
                break;
            case R.id.tv_made_for_him:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_FINISH);
                }
                break;
            case R.id.iv_play: //语音播放
                PermissionDialog.startVoicePlay((Activity) context, orderDetailEntity.getContent(),totaltime, mIvPlay, mVideoView, mTvTime);
                break;
            case R.id.rl_video_play://播放视频
                VideoActivity.start(context, orderDetailEntity.getContent(), false);
                break;
        }
    }
}
