package com.drifting.bureau.mvp.ui.activity.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.mvp.model.entity.DesireCounterEntity;
import com.drifting.bureau.mvp.ui.activity.web.ShowWebViewActivity;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.SpannableUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.callback.BaseDataCallBack;
import com.drifting.bureau.util.request.RequestUtil;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.component.AppComponent;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 公众漂
 * @Author : WeiJiaQI
 * @Time : 2022/10/26 15:43
 */
public class PublicFloatersActivity extends BaseManagerActivity {

    @BindView(R.id.tv_nums)
    TextView mTvNums;
    @BindView(R.id.tv_expect)
    ShapeTextView mTvExPect;
    @BindView(R.id.rl_cup_guide)
    RelativeLayout mRlCupGuide;
    @BindView(R.id.iv_guide1)
    ImageView mIvGuide1;
    @BindView(R.id.iv_guide2)
    ImageView mIvGuide2;
    @BindView(R.id.iv_guide3)
    ImageView mIvGuide3;
    @BindView(R.id.iv_guide4)
    ImageView mIvGuide4;
    @BindView(R.id.iv_guide5)
    ImageView mIvGuide5;
    @BindView(R.id.tv_cup_desc)
    TextView mTvCupDesc;
    @BindView(R.id.tv_cup_name)
    TextView mTvCupName;
    @BindView(R.id.rl_expect)
    RelativeLayout mRlexpect;
    @BindView(R.id.iv_bear)
    ImageView mIvBear;
    private SpannableStringBuilder passerFit;
    private DesireCounterEntity desireCounterEntity;
    private MediaPlayer mediaPlayer;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, PublicFloatersActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_public_floaters;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        desireCounter();
    }

    public void desireCounter(){
        RequestUtil.create().desireCounter(entity -> {
            if (entity != null && entity.getData() != null && entity.getCode() == 200) {
                desireCounterEntity = entity.getData();
                passerFit = SpannableUtil.getBuilder(this, "共 ").setTextSize(12).setBold().append(desireCounterEntity.getTotal()+ "").setTextSize(17).setBold().append(" 人期待").setTextSize(12).setBold().build();
                mTvNums.setText(passerFit);
                if (desireCounterEntity.getDesired() == 0) {
                    mTvExPect.getShapeDrawableBuilder().setSolidColor(getColor(R.color.color_ff2b)).intoBackground();
                    mTvExPect.setText("我也期待");
                } else {
                    mTvExPect.getShapeDrawableBuilder().setSolidColor(getColor(R.color.color_cc)).intoBackground();
                    mTvExPect.setText("期待中");
                }
            }
        });
    }


    @OnClick({R.id.tv_expect, R.id.iv_cooperation, R.id.iv_exit_shop, R.id.iv_psychological_cup, R.id.iv_emotion_cup, R.id.iv_stars_cup, R.id.iv_workplace_cup, R.id.iv_sinology_cup, R.id.rl_cup_guide})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_expect:    //我也期待
                    if (desireCounterEntity != null && desireCounterEntity.getDesired() == 0) {
                        RequestUtil.create().desire(entity -> {
                            if (entity != null && entity.getData() != null && entity.getCode() == 200) {
                                desireCounter();
                            }
                        });
                    }
                    break;
                case R.id.iv_cooperation:  //咨询师合作处
                    ShowWebViewActivity.start(this,5,false);
                    break;
                case R.id.iv_exit_shop:
                    finish();
                    break;
                case R.id.iv_psychological_cup:  //心理杯
                    setCupStatus(1);
                    break;
                case R.id.iv_emotion_cup:  //情感杯
                    setCupStatus(2);
                    break;
                case R.id.iv_stars_cup:  //星象杯
                    setCupStatus(3);
                    break;
                case R.id.iv_workplace_cup:  //职场杯
                    setCupStatus(4);
                    break;
                case R.id.iv_sinology_cup:  //国学杯
                    setCupStatus(5);
                    break;
                case R.id.rl_cup_guide:  //引导点击
                    stop();
                    mRlCupGuide.setVisibility(View.INVISIBLE);
                    mRlexpect.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }


    public void setCupStatus(int type) {
        mRlCupGuide.setVisibility(View.VISIBLE);
        mIvBear.setVisibility(View.VISIBLE);
        mRlexpect.setVisibility(View.INVISIBLE);
        mIvGuide1.setVisibility(View.INVISIBLE);
        mIvGuide2.setVisibility(View.INVISIBLE);
        mIvGuide3.setVisibility(View.INVISIBLE);
        mIvGuide4.setVisibility(View.INVISIBLE);
        mIvGuide5.setVisibility(View.INVISIBLE);
        if (type == 1) {
            mIvGuide1.setVisibility(View.VISIBLE);
            mTvCupDesc.setText("通过一杯“心理杯”，未来将会有心理陪伴师为你提供免费时长的心理解惑。");
            mTvCupName.setText(getString(R.string.buy_cup, "心理杯"));
            mediaPlayer = MediaPlayer.create(this, R.raw.guide1);
        } else if (type == 2) {
            mIvGuide2.setVisibility(View.VISIBLE);
            mTvCupDesc.setText("通过一杯“情感杯”，未来将会有情感咨询师为你提供免费时长的情感答疑。");
            mTvCupName.setText(getString(R.string.buy_cup, "情感杯"));
            mediaPlayer = MediaPlayer.create(this, R.raw.guide2);
        } else if (type == 3) {
            mIvGuide3.setVisibility(View.VISIBLE);
            mTvCupDesc.setText("通过一杯“星象杯”，未来将会有星象专家为你提供免费时长的星座、塔罗牌占卜等服务。");
            mTvCupName.setText(getString(R.string.buy_cup, "星象杯"));
            mediaPlayer = MediaPlayer.create(this, R.raw.guide3);
        } else if (type == 4) {
            mIvGuide4.setVisibility(View.VISIBLE);
            mTvCupDesc.setText("通过一杯“职场杯”，未来将会有职场达人为你提供免费时长的职业及未来指引等分享。");
            mTvCupName.setText(getString(R.string.buy_cup, "职场杯"));
            mediaPlayer = MediaPlayer.create(this, R.raw.guide4);
        } else if (type == 5) {
            mIvGuide5.setVisibility(View.VISIBLE);
            mTvCupDesc.setText("通过一杯“国学杯”，未来将会有国学大师为你提供免费时长的国学、八卦等服务。");
            mTvCupName.setText(getString(R.string.buy_cup, "国学杯"));
            mediaPlayer = MediaPlayer.create(this, R.raw.guide5);
        }
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        stop();
        super.onStop();
    }


    public void stop(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    @Override
    protected void onDestroy() {
        RequestUtil.create().disDispose();
        super.onDestroy();
    }
}
