package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerWithdrawalComponent;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.WithdrawalContract;
import com.drifting.bureau.mvp.presenter.WithdrawalPresenter;

import butterknife.OnClick;


/**
 * Created on 2022/05/27 15:12
 *
 * @author 提现
 * module name is WithdrawalActivity
 */
public class WithdrawalActivity extends BaseActivity<WithdrawalPresenter> implements WithdrawalContract.View {

    private PublicDialog publicDialog;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, WithdrawalActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWithdrawalComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_withdrawal; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        initListener();
    }

    public void initListener() {

    }

    @OnClick({R.id.toolbar_back,R.id.tv_withdrawal})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_withdrawal:  //提现
                    publicDialog=new PublicDialog(this);
                    publicDialog.show();
                    publicDialog.setCancelable(false);
                    publicDialog.setTitleText("申请成功");
                    publicDialog.setContentText("已成功提交提现申请 请耐心等待审核");
                    break;
            }
        }
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}