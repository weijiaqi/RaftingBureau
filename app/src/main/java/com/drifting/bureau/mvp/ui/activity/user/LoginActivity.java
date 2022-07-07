package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.data.entity.LoginLocallyEntity;
import com.drifting.bureau.di.component.DaggerLoginComponent;
import com.drifting.bureau.mvp.model.entity.LoginEntity;
import com.drifting.bureau.mvp.ui.activity.SplashActivity;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;
import com.drifting.bureau.mvp.ui.adapter.LoginListAdapter;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.LogInOutDataUtil;
import com.drifting.bureau.util.RongIMUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.VerifyUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.LoginContract;
import com.drifting.bureau.mvp.presenter.LoginPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 卫佳琪
 * @description 登录
 * @time 15:35 15:35
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.rcy_login)
    RecyclerView mRcyLogin;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    private List<LoginLocallyEntity> list;
    private LoginListAdapter loginListAdapter;
    private CountDownTimer timer;
    private int status = 1;
    private String phone,nikename;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mRcyLogin.setLayoutManager(new LinearLayoutManager(this));
        mRcyLogin.setHasFixedSize(true);
        loginListAdapter = new LoginListAdapter();
        mRcyLogin.setAdapter(loginListAdapter);
        loginListAdapter.setData(getLoginData());
        mEtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    sendSubmit();
                    return true;
                }
                return false;
            }
        });
    }

    public List<LoginLocallyEntity> getLoginData() {
        list = new ArrayList<>();
        list.add(new LoginLocallyEntity("你好！欢迎来到元宇宙漂流局 请选择创建星球的方式", 1, false));
        list.add(new LoginLocallyEntity("请在下方输入你的手机号", 1, false));
        return list;
    }

    @OnClick({R.id.tv_submit})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_submit:
                    sendSubmit();
                    break;
            }
        }
    }

    public void sendSubmit() {
        if (TextUtils.isEmpty(mEtContent.getText().toString())) {
            showMessage("内容不能为空!");
            return;
        }
        if (status == 1) {
            if (!StringUtil.isEmpty(StringUtil.checkMobile(mEtContent.getText().toString()))) {
                showMessage(StringUtil.checkMobile(mEtContent.getText().toString()));
                return;
            }
            setData(mEtContent.getText().toString(), 2, false);
            if (mPresenter != null) {
                phone = mEtContent.getText().toString();
                mPresenter.getCode(mEtContent.getText().toString(), 1, status);
            }
        } else if (status == 2) {
            setData(mEtContent.getText().toString(), 2, false);
            if (mPresenter != null) {
                mPresenter.login(phone, mEtContent.getText().toString(), status);
            }
        } else if (status == 3) {
            if (mEtContent.getText().length() > 9) {
                showMessage("请输入1~9字符!");
                return;
            }
            nikename=mEtContent.getText().toString();
            setData(nikename, 2, false);
            if (mPresenter != null) {
                mEtContent.setText("");
                mPresenter.register(phone, nikename, status);
            }
        }
    }

    /**
     * @description editetxt  hint设置
     */
    public void setEditHint(String text) {
        mEtContent.setText("");
        mEtContent.setHint(text);
        if (status == 2) {
            cleanCountDown();
            mTvSubmit.setText("确认");
            mTvSubmit.setEnabled(true);
        }
    }

    /**
     * @description 列表数据设置
     */
    public void setData(String content, int type, boolean isPlanet) {
        loginListAdapter.addData(new LoginLocallyEntity(content, type, isPlanet));
        moveToPosition(loginListAdapter.getItemCount());
    }

    /**
     * 开始倒计时
     */
    private void startCountDown() {
        if (timer == null) {
            timer = new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //剩余秒数
                    mTvSubmit.setEnabled(false);
                    mTvSubmit.setText((Math.round((double) millisUntilFinished / 1000) - 1) + "S");
                }

                @Override
                public void onFinish() {
                    mTvSubmit.setEnabled(true);
                    mTvSubmit.setText("获取验证码");
                    cleanCountDown();
                }
            }.start();
        }
    }

    /**
     * 结束倒计时
     */
    public void cleanCountDown() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param position 要跳转的位置
     */
    private void moveToPosition(int position) {
        ((LinearLayoutManager) mRcyLogin.getLayoutManager()).scrollToPositionWithOffset(position - 1, 0);
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void getCodeOk() {
        setData(getString(R.string.phone, VerifyUtil.mobilePhoneReplace(mEtContent.getText().toString())), 1, false);
        startCountDown();
        setEditHint("请输入验证码");
        status = 2;
    }

    @Override
    public void loginSuccess(LoginEntity loginEntity) {
        if (loginEntity.getStatus() == 1) {
            setData("请输入你在星球的昵称", 1, false);
            setEditHint("请输入昵称");
            status = 3;
        } else {
            LogInOutDataUtil.successInSetData(loginEntity);
            RongIMUtil.getInstance().connect(loginEntity.getRc_token(), new RongIMUtil.ConnectListener() {
                @Override
                public void onConnectSuccess() {
                    showMessage("登录成功");
                    DiscoveryTourActivity.start(LoginActivity.this, true);
                }

                @Override
                public void onConnectError() {
                    ToastUtil.showToast("会话消息故障!");
                    DiscoveryTourActivity.start(LoginActivity.this, true);
                }
            });
        }
    }

    @Override
    public void registerSuccess(LoginEntity loginEntity) {
        LogInOutDataUtil.successInSetData(loginEntity);
        RongIMUtil.getInstance().connect(loginEntity.getRc_token(), new RongIMUtil.ConnectListener() {
            @Override
            public void onConnectSuccess() {
                setData("恭喜你，信息创建成功!\n请领取你的专属星球！", 3, true);
                setEditHint("");
            }

            @Override
            public void onConnectError() {
                setData("恭喜你，信息创建成功!\n请领取你的专属星球！", 3, true);
                setEditHint("");
            }
        });


    }

    @Override
    public void loginFail(String result) {
        setData(result, 1, false);
    }

    @Override
    public void onNetError(int status) {
        showMessage("你的元宇宙接收器信号故障，请稍后再试!");
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanCountDown();
    }
}