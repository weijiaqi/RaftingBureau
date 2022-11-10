package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.app.application.RBureauApplication;
import com.drifting.bureau.data.event.AnswerCompletedEvent;
import com.drifting.bureau.di.component.DaggerAccountSettingsComponent;
import com.drifting.bureau.mvp.contract.AccountSettingsContract;
import com.drifting.bureau.mvp.presenter.AccountSettingsPresenter;
import com.drifting.bureau.mvp.ui.activity.SplashActivity;
import com.drifting.bureau.mvp.ui.activity.web.ShowWebViewActivity;
import com.drifting.bureau.mvp.ui.dialog.CurrencyDialog;
import com.drifting.bureau.mvp.ui.dialog.LogOutDialog;
import com.drifting.bureau.mvp.ui.dialog.ModifyNicknameDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.AppUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.LogInOutDataUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;

/**
 * Created on 2022/05/27 16:31
 *
 * @author 账户设置
 * module name is AccountSettingsActivity
 */
public class AccountSettingsActivity extends BaseActivity<AccountSettingsPresenter> implements AccountSettingsContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_nikename)
    TextView mTvNikename;
    @BindView(R.id.tv_version_code)
    TextView mTvVersionCode;
    private ModifyNicknameDialog modifyNicknameDialog;
    private LogOutDialog logOutDialog;
    private int clickCount = 0;

    private CurrencyDialog currencyDialog;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, AccountSettingsActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAccountSettingsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_account_settings; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("账户设置");
        mTvVersionCode.setText("版本号：V" + StringUtil.formatNullString(AppUtil.getVerName(RBureauApplication.getContext()) + "(" + AppUtil.getVersionCode(RBureauApplication.getContext()) + ")"));
        initListener();

        mTvVersionCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (clickCount == 3) {
                    currencyDialog = new CurrencyDialog(AccountSettingsActivity.this);
                    currencyDialog.show();
                    currencyDialog.setTitleText("环境切换");
                    currencyDialog.setText("正式环境", "测试环境");
                    currencyDialog.setOnClickCallback(type -> {
                        if (type == CurrencyDialog.SELECT_CANCEL) {
                            //正式环境
                            Preferences.saveTestState(false);
                        }

                        if (type == CurrencyDialog.SELECT_FINISH) {
                            //测试环境
                            Preferences.saveTestState(true);
                        }

                        Preferences.clearUserLoginData();

                        //重启App
                        CaocConfig config = new CaocConfig();
                        config.setRestartActivityClass(SplashActivity.class);
                        CustomActivityOnCrash.restartApplication(AccountSettingsActivity.this, config);
                    });
                    return true;
                }
                return false;
            }
        });
    }

    public void initListener() {
        getUserInfo();
    }

    public void getUserInfo() {
        RequestUtil.create().userplayer(Preferences.getUserId(), entity -> {
            if (entity != null && entity.getCode() == 200) {
                mTvNikename.setText(entity.getData().getUser().getName());
            }
        });
    }

    @OnClick({R.id.toolbar_back, R.id.rl_nikename, R.id.rl_feedback, R.id.rl_privacy, R.id.rl_register, R.id.tv_exit, R.id.rl_log_out, R.id.tv_version_code})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.rl_nikename:
                    modifyNicknameDialog = new ModifyNicknameDialog(this);
                    modifyNicknameDialog.show();
                    modifyNicknameDialog.setOnContentClickCallback(content -> {
                        if (mPresenter != null) {
                            mPresenter.setName(content);
                        }
                    });
                    break;
                case R.id.rl_feedback:
                    FeedBackActivity.start(this, false);
                    break;
                case R.id.rl_privacy:  //用户隐私协议
                    ShowWebViewActivity.start(this, 1, false);
                    break;
                case R.id.rl_register: //注册协议
                    ShowWebViewActivity.start(this, 2, false);
                    break;
                case R.id.tv_exit:  //退出
                    exit(1);
                    break;
                case R.id.rl_log_out: //注销账户
                    logOutDialog = new LogOutDialog(this);
                    logOutDialog.show();
                    logOutDialog.setOnClickCallback(type -> {
                        if (type == LogOutDialog.SELECT_FINISH) {
                            RequestUtil.create().unregister(entity -> {
                                if (entity != null && entity.getCode() == 200) {
                                    exit(2);
                                }
                            });
                        }
                    });
                    break;
                case R.id.tv_version_code:
                    clickCount++;
                    break;

            }
        }
    }


    public void exit(int type) {
        if (type == 1) {
            LogInOutDataUtil.successOutClearData();
        } else {
            LogInOutDataUtil.successOutClearAllData();
        }
        PullNewGuideActivity.start(this, true);
    }

    @Override
    public void onSetNameSuccess() {
        mTvNikename.setText(Preferences.getUserName());
        EventBus.getDefault().post(new AnswerCompletedEvent());
    }

    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.create().disDispose();
    }
}