package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.di.component.DaggerSpaceCapsuleComponent;
import com.drifting.bureau.mvp.contract.GetSpaceStationContract;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.MysteryboxEntity;
import com.drifting.bureau.mvp.model.entity.PrizeEntity;
import com.drifting.bureau.mvp.model.entity.SpaceAboutEntity;
import com.drifting.bureau.mvp.model.entity.SpaceCheckEntity;
import com.drifting.bureau.mvp.model.entity.SpaceStationEntity;
import com.drifting.bureau.mvp.presenter.GetSpaceStationPresenter;
import com.drifting.bureau.mvp.ui.activity.user.MySpaceStationActivity;
import com.drifting.bureau.mvp.ui.dialog.ExchangeCodeDialog;
import com.drifting.bureau.mvp.ui.dialog.HowToPlayDialog;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.ToastUtil;
import com.jess.arms.di.component.AppComponent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 太空舱
 * @Author : WeiJiaQI
 * @Time : 2022/5/24 9:39
 */
public class SpaceCapsuleActivity extends BaseManagerActivity<GetSpaceStationPresenter> implements GetSpaceStationContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_exchange_code)
    ImageView mIvExchangeCode;
    private HowToPlayDialog howToPlayDialog;
    private ExchangeCodeDialog exchangeCodeDialog;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, SpaceCapsuleActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSpaceCapsuleComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_space_capsule;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("空间站");
        if (DateUtil.localDateIsAfter()) {  //返回true
            mIvExchangeCode.setVisibility(View.GONE);
        } else {
            mIvExchangeCode.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.toolbar_back, R.id.tv_how_to_play, R.id.tv_space_obtain, R.id.tv_space_enter, R.id.iv_exchange_code})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_how_to_play:   //玩法说明
                    if (mPresenter != null) {
                        mPresenter.spaceabout();
                    }
                    break;
                case R.id.tv_space_obtain:  //获取空间站
                    GetSpaceStationActivity.start(this, false);
                    break;
                case R.id.tv_space_enter:  //进入空间站
                    if (mPresenter != null) {
                        mPresenter.spacecheck();
                    }
                    break;
                case R.id.iv_exchange_code: //兑换入口
                    exchangeCodeDialog = new ExchangeCodeDialog(this);
                    exchangeCodeDialog.show();
                    exchangeCodeDialog.setOnContentClickCallback(content -> {
                        if (mPresenter != null) {
                            mPresenter.spaceexchange(content);
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public void onGetMysterybox(MysteryboxEntity list) {

    }

    @Override
    public void onGetSpaceList(List<SpaceStationEntity> list) {

    }

    @Override
    public void onCreateOrderSpaceSuccess(CreateOrderEntity entity) {

    }

    @Override
    public void onSpaceCheck(SpaceCheckEntity entity) {
        if (entity != null) {
            if (entity.getStatus() == 0) {
                showMessage("检测到您还没拥有空间站,请去获取!");
            } else {
                MySpaceStationActivity.start(this, false);
            }
        }
    }

    @Override
    public void onAwardPreviewSuccess(List<PrizeEntity> list) {

    }

    @Override
    public void onSpaceAbout(List<SpaceAboutEntity> list) {
        if (list != null && list.size() > 0) {
            howToPlayDialog = new HowToPlayDialog(this, list);
            howToPlayDialog.show();
        }
    }

    @Override
    public void onNetError() {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }
}
