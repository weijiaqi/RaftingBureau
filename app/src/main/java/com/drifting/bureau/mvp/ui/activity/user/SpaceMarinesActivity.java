package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerSpaceMarinesComponent;
import com.drifting.bureau.mvp.model.entity.InfoForShareEntity;
import com.drifting.bureau.mvp.model.entity.TeamStatisticEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.ui.dialog.ShareDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.SpaceMarinesContract;
import com.drifting.bureau.mvp.presenter.SpaceMarinesPresenter;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/28 12:03
 *
 * @author 星际战队
 * module name is SpaceMarinesActivity
 */
public class SpaceMarinesActivity extends BaseActivity<SpaceMarinesPresenter> implements SpaceMarinesContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_income)
    TextView mTvIncome;
    @BindView(R.id.tv_persons)
    TextView mTvPersons;
    @BindView(R.id.tv_sure_withdrawal)
    TextView mTvWithdrawal;
    @BindView(R.id.tv_under_review)
    TextView mTvUnderReview;
    @BindView(R.id.withdrawn_cash)
    TextView mTvWithdrawalCash;
    @BindView(R.id.tv_order_num)
    TextView mTvOrderNum;
    @BindView(R.id.tv_ranking)
    TextView mTvRanking;


    private ShareDialog shareDialog;

    private UserInfoEntity userInfoEntity;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, SpaceMarinesActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSpaceMarinesComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_space_marines; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("星际战队");
        initListener();
    }

    public void initListener() {
        if (mPresenter != null) {
            mPresenter.team();
        }
        getUserInfo();
    }

    public void getUserInfo() {
        RequestUtil.create().userplayer(Preferences.getUserId(), entity -> {
            if (entity != null && entity.getCode() == 200) {
                userInfoEntity = entity.getData();
                mTvName.setText(userInfoEntity.getUser().getName());
            }
        });
    }

    @OnClick({R.id.toolbar_back, R.id.tv_withdrawal, R.id.tv_withdrawal_record, R.id.tv_share})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_withdrawal: //提现
                    WithdrawalActivity.start(this, 2, mTvWithdrawal.getText().toString(), false);
                    break;
                case R.id.tv_withdrawal_record://提现记录
                    WithdrawalRecordActivity.start(this, false);
                    break;
                case R.id.tv_share: //分享
                    if (userInfoEntity != null) {
                        shareDialog = new ShareDialog(this, userInfoEntity);
                        shareDialog.show();
                    }
                    break;
            }
        }
    }


    @Override
    public void OnTeamSuccess(TeamStatisticEntity entity) {
        if (entity != null) {
            mTvIncome.setText(entity.getTotal_income());
            mTvPersons.setText(entity.getPeople() + "");
            mTvWithdrawal.setText(entity.getWithdrawable());
            mTvUnderReview.setText(entity.getAuditing());
            mTvWithdrawalCash.setText(entity.getWithdrawn());
            mTvOrderNum.setText(entity.getOrder_num() + "");
            mTvRanking.setText(entity.getRanking() + "");
        }
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