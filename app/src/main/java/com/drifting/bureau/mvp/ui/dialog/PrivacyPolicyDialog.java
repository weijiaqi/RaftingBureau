package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.preference.Preference;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.ui.activity.web.ShowWebViewActivity;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.jess.arms.base.BaseDialog;

/**
 * @Description: 隐私协议条款
 * @Author : WeiJiaQI
 * @Time : 2022/9/5 11:22
 */
public class PrivacyPolicyDialog extends BaseDialog implements View.OnClickListener {
    private Context context;
    private TextView mTvPrivacy, mTvExitApp, mTvAgree;
    private CheckBox mCkProtocol;

    public static final int SELECT_EXIT_APP = 0x01;

    public static final int SELECT_ENTER_APP = 0x02;

    public PrivacyPolicyDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mTvPrivacy = findViewById(R.id.tv_privacy);
        mTvExitApp = findViewById(R.id.tv_exit_app);
        mTvAgree = findViewById(R.id.tv_agree);
        mCkProtocol = findViewById(R.id.ck_protocol);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvExitApp.setOnClickListener(this);
        mTvAgree.setOnClickListener(this);
        setUserComment();
    }


    /**
     * @description 给隐私设置颜色
     */
    public void setUserComment() {
        mTvPrivacy.setOnLongClickListener(v -> true);
        mTvPrivacy.setHighlightColor(context.getResources().getColor(android.R.color.transparent));
        mTvPrivacy.setText("我已阅读并同意");
        mTvPrivacy.append(buildRegisterSpannableString("《用户注册协议》"));
        mTvPrivacy.append("和");
        mTvPrivacy.append(buildPrivacySpannableString("《用户隐私政策》"));
        mTvPrivacy.setMovementMethod(LinkMovementMethod.getInstance());
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
                    ShowWebViewActivity.start(context, 2, false);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(context.getResources().getColor(R.color.color_0c));
                ds.setUnderlineText(false);
            }
        }, 0, userSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return userSpannable;
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
                    ShowWebViewActivity.start(context, 1, false);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(context.getResources().getColor(R.color.color_0c));
                ds.setUnderlineText(false);
            }
        }, 0, userSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return userSpannable;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_privacy_policy;
    }

    @Override
    protected float getDialogWith() {
        return 0.8f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_exit_app:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_EXIT_APP);
                }
                break;
            case R.id.tv_agree:
                if (!StringUtil.isEmpty(StringUtil.checkProtocol(mCkProtocol))) {
                    Toast.makeText(context, StringUtil.checkProtocol(mCkProtocol), Toast.LENGTH_SHORT).show();
                    Animation shake = AnimationUtils.loadAnimation(context, R.anim.anim_jitter);
                    mCkProtocol.startAnimation(shake);
                    return;
                }
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_ENTER_APP);
                }
                break;
        }
    }
}
