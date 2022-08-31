package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.mvp.ui.activity.web.ShowWebViewActivity;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
<<<<<<< HEAD
=======
import com.jess.arms.base.BaseActivity;
>>>>>>> origin/dev
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 建立自己的星球
 * @Author : WeiJiaQI
 * @Time : 2022/5/9 9:31
 */
public class BuildGuideActivity extends BaseManagerActivity {
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;
    @BindView(R.id.ck_protocol)
    CheckBox mCkProtocol;
    @BindView(R.id.rl_check)
    RelativeLayout mRlCheck;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, BuildGuideActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_build_guide;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setUserComment();
        mCkProtocol.setOnCheckedChangeListener((compoundButton, ischecked) -> {
            mRlCheck.setVisibility(ischecked ? View.GONE : View.VISIBLE);
        });

        if (Preferences.isTest()) {
            ToastUtil.showToast("测试环境");
        }
    }

    /**
     * @description 给隐私设置颜色
     */
    public void setUserComment() {
        mTvProtocol.setOnLongClickListener(v -> true);
        mTvProtocol.setHighlightColor(getResources().getColor(android.R.color.transparent));
        mTvProtocol.setText("我已阅读并同意");
        mTvProtocol.append(buildPrivacySpannableString("《用户隐私协议》"));
        mTvProtocol.append("和");
        mTvProtocol.append(buildRegisterSpannableString("《用户注册协议》"));
        mTvProtocol.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * @description 用户隐私协议
     */
    private SpannableString buildPrivacySpannableString(String privacy) {
        SpannableString userSpannable = new SpannableString(privacy);
        userSpannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (!ClickUtil.isFastClick(widget.getId())) {
                    ShowWebViewActivity.start(BuildGuideActivity.this, 1,false);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.white));
                ds.setUnderlineText(false);
            }
        }, 0, userSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return userSpannable;
    }

    /**
     * @description 用户注册协议
     */
    private SpannableString buildRegisterSpannableString(String register) {
        SpannableString userSpannable = new SpannableString(register);
        userSpannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (!ClickUtil.isFastClick(widget.getId())) {
                    ShowWebViewActivity.start(BuildGuideActivity.this,2, false);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.white));
                ds.setUnderlineText(false);
            }
        }, 0, userSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return userSpannable;
    }

    @OnClick({R.id.tv_create_planet})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_create_planet: //建立星球
                    if (!StringUtil.isEmpty(StringUtil.checkProtocol(mCkProtocol))) {
                        mRlCheck.setVisibility(View.VISIBLE);
                        Animation shake = AnimationUtils.loadAnimation(this, R.anim.anim_jitter);
                        mRlCheck.startAnimation(shake);
                        return;
                    }
                    SignLoginHintActivity.start(this, true);
                    break;
            }
        }
    }

}
