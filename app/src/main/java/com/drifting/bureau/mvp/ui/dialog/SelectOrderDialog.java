package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.OrderDetailEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
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

    private TextView mTvLeaveSpace, mTvMadeForHim, mTvname, mTvPlanet, mTvIdentity, mTvWord;
    private RelativeLayout mRlVoicePlay, mRlVideoPlay;
    private VoiceWave voiceWave;
    private UserInfoEntity userInfoEntity;
    private OrderDetailEntity orderDetailEntity;

    public SelectOrderDialog(@NonNull Context context, UserInfoEntity userInfoEntity, OrderDetailEntity orderDetailEntity) {
        super(context);
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
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvLeaveSpace.setOnClickListener(this);
        mTvMadeForHim.setOnClickListener(this);
        mTvname.setText("昵称：" + userInfoEntity.getUser().getName());
        mTvPlanet.setText(userInfoEntity.getPlanet().getName());
        mTvIdentity.setText(userInfoEntity.getUser().getName());
        switch (orderDetailEntity.getType_id()) {
            case 1:
                mTvWord.setVisibility(View.VISIBLE);
                mTvWord.setText(orderDetailEntity.getContent());
                break;
            case 2:
                mRlVoicePlay.setVisibility(View.VISIBLE);
                break;
            case 3:
                mRlVideoPlay.setVisibility(View.VISIBLE);
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
                dismiss();
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
        }
    }
}
