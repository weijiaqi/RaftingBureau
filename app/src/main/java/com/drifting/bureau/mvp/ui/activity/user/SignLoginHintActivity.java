package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerSignLoginComponent;
import com.drifting.bureau.mvp.contract.SignLoginContract;
import com.drifting.bureau.mvp.model.entity.LoginEntity;
import com.drifting.bureau.mvp.presenter.SignLoginPresenter;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.LogInOutDataUtil;
import com.drifting.bureau.util.RongIMUtil;
import com.drifting.bureau.util.ToastUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 注册/登录提示
 * @Author : WeiJiaQI
 * @Time : 2022/5/9 13:31
 */
public class SignLoginHintActivity extends BaseActivity<SignLoginPresenter> implements SignLoginContract.View {
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_sign_login)
    TextView mTvSignLogin;
    @BindView(R.id.tv_other_phone)
    TextView mTvOtherPhone;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, SignLoginHintActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSignLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sign_login_hint;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mTvContent.setText("你好！欢迎来到元宇宙漂流局\n请选择创建星球的方式");
        if (!TextUtils.isEmpty(Preferences.getPhone())) {
            mTvSignLogin.setText(Preferences.getPhone() + "一键登录");
            mTvOtherPhone.setVisibility(View.VISIBLE);
        } else {
            mTvSignLogin.setText("手机号注册/登录");
            mTvOtherPhone.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick({R.id.tv_sign_login, R.id.tv_other_phone})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_sign_login: //注册/登录
                    if (!TextUtils.isEmpty(Preferences.getPhone())) {
                        if (mPresenter != null) {
                            mPresenter.passwordlogin(Preferences.getPhone(), Preferences.getPassword());
                        }
                    } else {
                        LoginActivity.start(this, false);
                    }
                    break;
                case R.id.tv_other_phone:
                    LoginActivity.start(this, false);
                    break;
            }
        }
    }

    @Override
    public void loginSuccess(LoginEntity loginEntity) {
        LogInOutDataUtil.successInSetData(loginEntity);
        RongIMUtil.getInstance().connect(loginEntity.getRc_token(), new RongIMUtil.ConnectListener() {
            @Override
            public void onConnectSuccess() {
                showMessage("登录成功");
                DiscoveryTourActivity.start(SignLoginHintActivity.this, true);
            }

            @Override
            public void onConnectError() {
                //ToastUtil.showToast("会话消息故障!");
                DiscoveryTourActivity.start(SignLoginHintActivity.this, true);
            }
        });
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    @Override
    public void onNetError() {

    }
}
