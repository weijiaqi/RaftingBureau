package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.BackSpaceEvent;
import com.drifting.bureau.data.event.DonationEvent;
import com.drifting.bureau.di.component.DaggerMySpaceStationComponent;
import com.drifting.bureau.mvp.model.entity.MySpaceStationEntity;
import com.drifting.bureau.mvp.model.entity.MyTreasuryEntity;
import com.drifting.bureau.mvp.model.entity.OrderDetailEntity;
import com.drifting.bureau.mvp.model.entity.OrderOneEntity;
import com.drifting.bureau.mvp.model.entity.SpaceInfoEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.ui.dialog.MakeScheduleDialog;
import com.drifting.bureau.mvp.ui.dialog.MySpaceStationDialog;
import com.drifting.bureau.mvp.ui.dialog.MyTreasuryDialog;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.drifting.bureau.mvp.ui.dialog.SelectOrderDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.MySpaceStationContract;
import com.drifting.bureau.mvp.presenter.MySpaceStationPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/24 17:06
 *
 * @author 我的空间站
 * module name is MySpaceStationActivity
 */
public class MySpaceStationActivity extends BaseActivity<MySpaceStationPresenter> implements MySpaceStationContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rl_top)
    RelativeLayout mRlTop;
    @BindView(R.id.tv_levle_name)
    TextView mTvlevelName;
    @BindView(R.id.tv_whole_make)
    TextView mTvWholeMake;
    @BindView(R.id.tv_whole_income)
    TextView mTvWholeIncome;
    @BindView(R.id.tv_today_make)
    TextView mTvTodayMake;
    @BindView(R.id.tv_withdrawal)
    TextView mTvWithdrawal;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.tv_not_data)
    TextView mTvNotData;
    @BindView(R.id.tv_timeliness)
    TextView mTvTimeLine;
    private SelectOrderDialog selectOrderDialog;
    private MakeScheduleDialog makeScheduleDialog;
    private PublicDialog publicDialog;
    private MyTreasuryDialog myTreasuryDialog;
    private MySpaceStationDialog mySpaceStationDialog;
    private int SpaceId;
    private static final String EXTRA_SPACE_ID = "extra_space_id";
    private OrderOneEntity orderOneEntity;
    private UserInfoEntity userInfoEntity;
    private Handler handler;

    public static void start(Context context, int space_id, boolean closePage) {
        Intent intent = new Intent(context, MySpaceStationActivity.class);
        intent.putExtra(EXTRA_SPACE_ID, space_id);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMySpaceStationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_space_station; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("我的空间站");
        if (getIntent() != null) {
            SpaceId = getIntent().getIntExtra(EXTRA_SPACE_ID, 0);
        }
        initListener();
    }

    public void initListener() {
        getInfo();
    }

    Runnable mAdRunnable = () -> getOrderOne();


    @Override
    protected void onResume() {
        super.onResume();
        handler = new Handler();
        handler.postDelayed(mAdRunnable, 60);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(mAdRunnable);
        }
    }


    public void getOrderOne() {
        if (mPresenter != null) {
            mPresenter.orderone();
        }
    }

    public void getInfo() {
        if (mPresenter != null) {
            mPresenter.spaceinfo(Preferences.getUserId());
        }
    }

    @OnClick({R.id.toolbar_back, R.id.tv_select, R.id.tv_my_treasury, R.id.tv_upgrade, R.id.rl_total_revenue, R.id.rl_making_records, R.id.rl_withdrawal})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_select:   //查看
                    if (mPresenter != null & orderOneEntity != null) {
                        mPresenter.userplayer(orderOneEntity.getUser_id() + "");
                    }
                    break;
                case R.id.tv_my_treasury: //我的库存
                    getStorageList();
                    break;
                case R.id.tv_upgrade:  //升级
                    levelcurrent();
                    break;
                case R.id.rl_total_revenue: //收支记录
                    IncomeRecordActivity.start(this, false);
                    break;
                case R.id.rl_making_records:  //制作记录
                    MakingRecordActivity.start(this, false);
                    break;
                case R.id.rl_withdrawal: //提现
                    WithdrawalActivity.start(this, mTvWithdrawal.getText().toString(), false);
                    break;
            }
        }
    }

    //我的库存
    public void getStorageList() {
        if (mPresenter != null) {
            mPresenter.getStorageList();
        }
    }

    //升级
    public void levelcurrent() {
        if (mPresenter != null) {
            mPresenter.levelcurrent();
        }
    }

    @Override
    public void onSpcaeInfoSuccess(SpaceInfoEntity entity) {
        if (entity != null) {
            switch (entity.getLevel()) {
                case 1:
                    mRlTop.setBackgroundResource(R.drawable.my_space_station_top1);
                    break;
                case 2:
                    mRlTop.setBackgroundResource(R.drawable.my_space_station_top2);
                    break;
                case 3:
                    mRlTop.setBackgroundResource(R.drawable.my_space_station_top3);
                    break;
                case 4:
                    mRlTop.setBackgroundResource(R.drawable.my_space_station_top4);
                    break;
                case 5:
                    mRlTop.setBackgroundResource(R.drawable.my_space_station_top5);
                    break;
            }

            mTvlevelName.setText(entity.getLevel_name());
            mTvWholeMake.setText(entity.getTotal_make() + "");
            mTvWholeIncome.setText(StringUtil.frontValue(entity.getTotal_income()));
            mTvTodayMake.setText(entity.getToday_make() + "");
            mTvWithdrawal.setText(StringUtil.frontValue(entity.getWithdrawable()));
        }
    }

    @Override
    public void onOrderOneSuccess(OrderOneEntity entity) {
        if (entity != null) {
            orderOneEntity = entity;
            if (orderOneEntity.getTimeout() == 0) {
                setOrderGone(true);
                handler.postDelayed(mAdRunnable, 1000 * 10);
            } else {
                setOrderGone(false);
                mTvTimeLine.setText(DateUtil.TimeRemaining(orderOneEntity.getTimeout()) + "后将消失");
                handler.postDelayed(mAdRunnable, entity.getTimeout() * 10);
            }
        }
    }


    public void setOrderGone(boolean type) {
        mLlContent.setVisibility(type ? View.GONE : View.VISIBLE);
        mTvNotData.setVisibility(type ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onOrderDetailSuccess(OrderDetailEntity entity) {
        if (entity != null) {
            selectOrderDialog = new SelectOrderDialog(this, userInfoEntity, entity);
            selectOrderDialog.show();
            selectOrderDialog.setOnClickCallback(type -> {
                if (type == SelectOrderDialog.SELECT_CANCEL) { //丢回太空
                    if (mPresenter != null) {
                        mPresenter.orderthrow(entity.getSpace_order_id());
                    }
                } else if (type == SelectOrderDialog.SELECT_FINISH) { //为他制作
                    makeScheduleDialog = new MakeScheduleDialog(this);
                    makeScheduleDialog.show();
                    makeScheduleDialog.setCancelable(false);
                    makeScheduleDialog.setOnClickCallback(type1 -> {
                        if (type1 == SelectOrderDialog.SELECT_FINISH) {
                            if (mPresenter != null) {
                                mPresenter.ordermaking(entity.getSpace_order_id());
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onUserInfoSuccess(UserInfoEntity entity) {
        if (entity != null & entity.getUser() != null) {
            userInfoEntity = entity;
            mPresenter.orderdetail(orderOneEntity.getSpace_order_id());
        }
    }

    @Override
    public void onLevelCurrentSuccess(MySpaceStationEntity entity) {
        if (entity != null) {
            mySpaceStationDialog = new MySpaceStationDialog(this, entity);
            mySpaceStationDialog.show();
        }
    }

    @Override
    public void onStorageMineSuccess(List<MyTreasuryEntity> entity) {
        if (entity != null) {
            myTreasuryDialog = new MyTreasuryDialog(this, entity);
            myTreasuryDialog.show();
            myTreasuryDialog.setOnClickCallback(type -> {
                if (type == MyTreasuryDialog.SELECT_USE) {
                    levelcurrent();
                }
            });
        }
    }

    @Override
    public void onOrderMakingSuccess() { //制作成功
        showDialog(1, "制作完成", "已经制作完成并发往太空了\n拥有该订单带来的收益");
    }

    @Override
    public void onOrderThrowSuccess() { //丢回太空
        selectOrderDialog.dismiss();
        showDialog(2, "订单已丢回太空", "已经把该订单丢回太空\n该订单的收益将无法拥有");
    }


    public void showDialog(int status, String title, String content) {
        publicDialog = new PublicDialog(this);
        publicDialog.show();
        publicDialog.setCancelable(false);
        publicDialog.setTitleText(title);
        publicDialog.setContentText(content);
        publicDialog.setOnClickCallback(type -> {
            if (type == PublicDialog.SELECT_FINISH) {
                if (status == 1) {
                    getInfo();
                }
                handler.postDelayed(mAdRunnable, 60);
            }
        });
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void DonationEvent(DonationEvent donationEvent) {
        if (donationEvent != null) {
            getStorageList();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeCallbacks(mAdRunnable);
        }
    }

}