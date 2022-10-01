package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.drifting.bureau.R;
import com.drifting.bureau.WebUrlConstant;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.mvp.ui.activity.index.AnswerResultActivity;
import com.drifting.bureau.mvp.ui.activity.index.LaboratoryActivity;
import com.drifting.bureau.mvp.ui.activity.index.TeaShopActivity;
import com.drifting.bureau.mvp.ui.activity.unity.ArGeRenXingQiuActivity;
import com.drifting.bureau.mvp.ui.activity.unity.ArPaiXiXingQiuActivity;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.SpannableUtil;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

public class NewAboutMeActivity extends BaseManagerActivity {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.tv_nikename)
    TextView mTvNikeName;
    @BindView(R.id.tv_faction)
    TextView mTvFaction;
    @BindView(R.id.tv_identity)
    TextView mTvIdentity;
    @BindView(R.id.iv_play_bear)
    ImageView mIvPlayBear;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    @BindView(R.id.tv_attr)
    TextView mTvAttr;
    @BindView(R.id.tv_to_the_galaxy)
    TextView mTvGalaxy;
    @BindView(R.id.tv_person)
    TextView mTvPerson;
    @BindView(R.id.iv_personBg)
    ImageView mIvPersonBg;
    @BindView(R.id.tv_order_record)
    TextView mTvOrderRecord;
    @BindView(R.id.tv_physical_store)
    TextView mTvPhysicalStore;
    @BindView(R.id.tv_star_troopers)
    TextView mTvStarTroopers;
    @BindView(R.id.tv_drift_track)
    TextView mTvDriftTrack;
    @BindView(R.id.iv_turnplate)
    ImageView mIvTurnplate;
    @BindView(R.id.rl_bg)
    RelativeLayout mRlBg;
    private SpannableStringBuilder passerNikename, passerFaction, passerIdentity;


    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, NewAboutMeActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_new_about_me;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        TextUtil.setRightImage(mIvRight, R.drawable.setting);
        getUserInfo();
    }

    public void getUserInfo() {
        RequestUtil.create().userplayer(Preferences.getUserId(), entity -> {
            if (entity != null && entity.getCode() == 200) {
                GlideUtil.create().loadLongImage(this, entity.getData().getUser().getMascot(), mIvPlayBear);
                passerNikename = SpannableUtil.getBuilder(this, "昵称：").setForegroundColor(R.color.color_66).setTextSize(12).append(entity.getData().getUser().getName()).setForegroundColor(R.color.color_00).setTextSize(14).setBold().build();
                mTvNikeName.setText(passerNikename);
                passerFaction = SpannableUtil.getBuilder(this, "派系：").setForegroundColor(R.color.color_66).setTextSize(12).append(entity.getData().getPlanet().getName()).setForegroundColor(R.color.color_00).setTextSize(14).setBold().build();
                mTvFaction.setText(passerFaction);
                passerIdentity = SpannableUtil.getBuilder(this, "身份：").setForegroundColor(R.color.color_66).setTextSize(12).append(entity.getData().getUser().getLevel_name()).setForegroundColor(R.color.color_00).setTextSize(14).setBold().build();
                mTvIdentity.setText(passerIdentity);

                 //背景
                GlideUtil.create().loadViewLongImage(this, WebUrlConstant.ABOUT_DEF +"/android/"+ entity.getData().getPlanet().getLevel() + ".jpg", mRlBg);
                //底部背景
                GlideUtil.create().loadViewLongImage(this, entity.getData().getPlanet().getLevel() == 11 ? WebUrlConstant.BOOTOM_DEF_11 : WebUrlConstant.BOOTOM_DEF, mLlBottom);
                //查看我得属性背景
                GlideUtil.create().loadViewLongImage(this, WebUrlConstant.ABOUT_DEF + entity.getData().getPlanet().getLevel() + "/attr.png", mTvAttr);
                //前往派系
                GlideUtil.create().loadViewLongImage(this, WebUrlConstant.ABOUT_DEF + entity.getData().getPlanet().getLevel() + "/galaxy.png", mTvGalaxy);
                // 进入个人星球
                GlideUtil.create().loadViewLongImage(this, WebUrlConstant.ABOUT_DEF + entity.getData().getPlanet().getLevel() + "/person.png", mTvPerson);
                // 进入个人星球背景
                GlideUtil.create().loadLongImage(this, WebUrlConstant.ABOUT_DEF + entity.getData().getPlanet().getLevel() + "/personBg.png", mIvPersonBg);
                //订单记录
                GlideUtil.create().loadViewLongImage(this, WebUrlConstant.ABOUT_DEF + entity.getData().getPlanet().getLevel() + "/record.png", mTvOrderRecord);
                //订单记录
                GlideUtil.create().loadViewLongImage(this, WebUrlConstant.ABOUT_DEF + entity.getData().getPlanet().getLevel() + "/record.png", mTvOrderRecord);
                //实体门店
                GlideUtil.create().loadViewLongImage(this, WebUrlConstant.ABOUT_DEF + entity.getData().getPlanet().getLevel() + "/shop.png", mTvPhysicalStore);
                //星际战队
                GlideUtil.create().loadViewLongImage(this, WebUrlConstant.ABOUT_DEF + entity.getData().getPlanet().getLevel() + "/team.png", mTvStarTroopers);
                //  漂流轨迹
                GlideUtil.create().loadViewLongImage(this, WebUrlConstant.ABOUT_DEF + entity.getData().getPlanet().getLevel() + "/track.png", mTvDriftTrack);
                //圆盘
                GlideUtil.create().loadLongImage(this, WebUrlConstant.ABOUT_DEF + entity.getData().getPlanet().getLevel() + "/turnplate.png", mIvTurnplate);

            }
        });

    }


    @OnClick({R.id.toolbar_back, R.id.iv_right, R.id.tv_drift_track, R.id.tv_star_troopers, R.id.tv_physical_store, R.id.tv_order_record, R.id.tv_to_the_galaxy,R.id.tv_attr,R.id.tv_change_mode,R.id.tv_person})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_right:
                    AccountSettingsActivity.start(this, false);
                    break;
                case R.id.tv_drift_track:  //漂流轨迹
                    DriftingTrackActivity.start(this, false);
                    break;
                case R.id.tv_star_troopers: //星际战队
                    SpaceMarinesActivity.start(this, false);
                    break;
                case R.id.tv_physical_store: //实体门店
                    TeaShopActivity.start(this, false);
                    break;
                case R.id.tv_order_record: //订单记录
                    OrderRecordActivity.start(this, false);
                    break;
                case R.id.tv_to_the_galaxy: //鉴定派系
                    LaboratoryActivity.start(this, false);
                    break;
                case R.id.tv_attr: //查看我得属性
                    AnswerResultActivity.start(this,false);
                    break;
                case R.id.tv_change_mode:  //跳转到派系星球
                    ArPaiXiXingQiuActivity.start(this,false);
                    break;
                case R.id.tv_person:  //进入个人星球
                    ArGeRenXingQiuActivity.start(this,false);
                    break;
            }
        }
    }
}