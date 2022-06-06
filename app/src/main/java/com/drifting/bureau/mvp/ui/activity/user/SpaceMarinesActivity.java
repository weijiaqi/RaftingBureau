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
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.ui.dialog.ShareDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
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
          if (mPresenter!=null){
              mPresenter.userplayer(Preferences.getUserId());
          }
    }

    @OnClick({R.id.toolbar_back, R.id.tv_withdrawal, R.id.tv_withdrawal_record, R.id.tv_share})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_withdrawal: //提现
                    WithdrawalActivity.start(this, false);
                    break;
                case R.id.tv_withdrawal_record://提现记录
                    WithdrawalRecordActivity.start(this, false);
                    break;
                case R.id.tv_share: //分享
                    if (userInfoEntity!=null){
                        shareDialog = new ShareDialog(this,userInfoEntity);
                        shareDialog.show();
                    }

                    break;
            }
        }
    }

    @Override
    public void onUserInfoSuccess(UserInfoEntity entity) {
        if (entity != null) {
            userInfoEntity = entity;
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

    }
}