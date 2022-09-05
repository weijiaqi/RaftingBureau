package com.drifting.bureau.mvp.ui.activity.user;

import static com.drifting.bureau.app.FilePathConstant.STAR_PATH;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.mvp.ui.activity.index.ar.ARActivity;
import com.drifting.bureau.mvp.ui.activity.web.ShowWebViewActivity;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.mvp.ui.dialog.PrivacyPolicyDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ARCoreUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.FileUtil;
import com.drifting.bureau.util.NotificationUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.animator.AnimatorUtil;
import com.drifting.bureau.util.downloadutil.DownloadRequest;
import com.drifting.bureau.util.manager.NotificationManager;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.di.component.AppComponent;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 启动页
 * @Author : WeiJiaQI
 * @Time : 2022/7/26 14:28
 */
public class PullNewGuideActivity extends BaseManagerActivity {
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;
    @BindView(R.id.ck_protocol)
    CheckBox mCkProtocol;
    @BindView(R.id.rl_check)
    RelativeLayout mRlCheck;
    @BindView(R.id.iv_pull1)
    ImageView mIvPull1;
    @BindView(R.id.iv_pull2)
    ImageView mIvPull2;
    @BindView(R.id.iv_pull3)
    ImageView mIvPull3;
    @BindView(R.id.iv_pull4)
    ImageView mIvPull4;
    @BindView(R.id.iv_pull5)
    ImageView mIvPull5;
    @BindView(R.id.iv_pull6)
    ImageView mIvPull6;
    @BindView(R.id.iv_pull7)
    ImageView mIvPull7;
    @BindView(R.id.iv_new_user)
    ImageView mIvNewUser;

    private PrivacyPolicyDialog privacyPolicyDialog;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, PullNewGuideActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pull_new_guide;
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

        statFloatAnim(mIvPull1);
        statFloatAnim(mIvPull2);
        statFloatAnim(mIvPull3);
        statFloatAnim(mIvPull4);
        statFloatAnim(mIvPull5);
        statFloatAnim(mIvPull6);
        statScaleAnim(mIvPull7);
        statFloatAnim(mIvNewUser);

        if (!Preferences.isAgreePrivacy()){
            privacyPolicyDialog = new PrivacyPolicyDialog(this);
            privacyPolicyDialog.setCancelable(false);
            privacyPolicyDialog.show();
            privacyPolicyDialog.setOnClickCallback(type -> {
                if (type == PrivacyPolicyDialog.SELECT_EXIT_APP) {
                    finish();
                }
            });
        }
    }

    public void statScaleAnim(View view) {
        AnimatorUtil.ScaleAnim(view, 3000);
    }

    public void statFloatAnim(View view) {
        AnimatorUtil.floatAnim(view, 1000, (int) (Math.random() * (6 - 1 + 3) + 3));
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
                    ShowWebViewActivity.start(PullNewGuideActivity.this, 1, false);
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
                    ShowWebViewActivity.start(PullNewGuideActivity.this, 2, false);
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

    @OnClick({R.id.tv_create_planet, R.id.tv_ar_select})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_create_planet: //建立星球
                    if (IsVisibility()) {
                        SignLoginHintActivity.start(this, true);
                    }
                    break;
                case R.id.tv_ar_select:  //AR查看
                    if (IsVisibility()) {
                        RequestUtil.create().planetar(entity -> {
                            if (entity != null && entity.getCode() == 200) {
                                if (!TextUtils.isEmpty(entity.getData().getAr_url())) {
                                    if (ARCoreUtil.checkArCoreAvailability(this)) {
                                        startCamrea(entity.getData().getAr_url()); //开启AR
                                    }
                                }
                            }
                        });
                    }
                    break;
            }
        }
    }

    public boolean IsVisibility() {
        if (!StringUtil.isEmpty(StringUtil.checkProtocol(mCkProtocol))) {
            mRlCheck.setVisibility(View.VISIBLE);
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.anim_jitter);
            mRlCheck.startAnimation(shake);
            return false;
        }
        return true;
    }

    public void startCamrea(String url) {
        PermissionDialog.requestCodePermissions(this, new PermissionDialog.PermissionCallBack() {
            @Override
            public void onSuccess() {
                String file = STAR_PATH + FileUtil.getUrlFileName(url);
                if (FileUtil.fileIsExists(file)) {
                    FileUtil.getNetworkFileSize(url, new Handler(Looper.myLooper()) {
                        @Override
                        public void handleMessage(Message msg) {
                            int fileSize = msg.getData().getInt("fileSize");
                            if (fileSize == new File(file).length()) {
                                ARActivity.start(PullNewGuideActivity.this, file, false);
                            } else {
                                showNotificationDialog(url);
                            }
                        }
                    });
                } else {
                    showNotificationDialog(url);
                }
            }

            @Override
            public void onFailure() {
            }

            @Override
            public void onAlwaysFailure() {
                PermissionDialog.showDialog(PullNewGuideActivity.this, "android.permission.CAMERA");
            }
        });
    }

    public void showNotificationDialog(String url) {
        if (NotificationManager.isOpenNotification(PullNewGuideActivity.this)) {
            DownloadRequest.whith().downloadWithNotification(PullNewGuideActivity.this, url);
        } else {
            NotificationUtil.showNotificationDialog(PullNewGuideActivity.this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.create().disDispose();
    }
}
