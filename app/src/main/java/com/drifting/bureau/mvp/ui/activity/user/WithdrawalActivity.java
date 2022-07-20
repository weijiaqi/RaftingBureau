package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerWithdrawalComponent;
import com.drifting.bureau.mvp.contract.WithdrawalContract;
import com.drifting.bureau.mvp.presenter.WithdrawalPresenter;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.ToastUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/27 15:12
 *
 * @author 提现
 * module name is WithdrawalActivity
 */
public class WithdrawalActivity extends BaseActivity<WithdrawalPresenter> implements WithdrawalContract.View {
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.et_money)
    EditText mEtMoney;
    @BindView(R.id.et_account)
    TextView mEtAccount;
    @BindView(R.id.et_name)
    TextView mEtName;
    private PublicDialog publicDialog;
    private static String EXTRA_MONEY = "extra_money";
    private static String EXTRA_TYPE = "extra_type";
    private String money;
    private int type;

    public static void start(Context context, int type, String money, boolean closePage) {
        Intent intent = new Intent(context, WithdrawalActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_MONEY, money);
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
        if (getIntent() != null) {  //1，空间站提现 2，分销提现
            type = getIntent().getExtras().getInt(EXTRA_TYPE);
            money = getIntent().getExtras().getString(EXTRA_MONEY);
        }
        initListener();
    }

    public void initListener() {
        TextUtil.watchEditView(mEtMoney);
        mTvMoney.setText("可提现金额：" + money);
    }

    @OnClick({R.id.toolbar_back, R.id.tv_all, R.id.tv_withdrawal})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_all:
                    mEtMoney.setText(money);
                    break;
                case R.id.tv_withdrawal:  //提现
                    if (StringUtil.isEmpty(mEtAccount.getText().toString())) {
                        showMessage("请输入银行卡号!");
                        return;
                    }
                    if (StringUtil.isEmpty(mEtName.getText().toString())) {
                        showMessage("请输入银行账户的实名信息!");
                        return;
                    }
                    if (StringUtil.isEmpty(mEtMoney.getText().toString())) {
                        showMessage("请输入需要提现的金额!");
                        return;
                    }
                    if (Double.parseDouble(mEtMoney.getText().toString()) == 0) {
                        showMessage("请输入正确的提现金额!");
                        return;
                    }

                    if (!StringUtil.isEmpty(StringUtil.CompareMoney(mEtMoney.getText().toString(), money))) {
                        showMessage(StringUtil.CompareMoney(mEtMoney.getText().toString(), money));
                        return;
                    }
                    if (mPresenter != null) {
                        mPresenter.withdrawapply(mEtName.getText().toString(), mEtAccount.getText().toString(), mEtMoney.getText().toString(), type);
                    }
                    break;
            }
        }
    }

    @Override
    public void onWithdrawSuccess() {
        publicDialog = new PublicDialog(this);
        publicDialog.show();
        publicDialog.setCancelable(false);
        publicDialog.setTitleText("申请成功");
        publicDialog.setContentText("已成功提交提现申请 请耐心等待审核");
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
}