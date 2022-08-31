package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.WithdrawEvent;
import com.drifting.bureau.di.component.DaggerMySpaceStationComponent;
import com.drifting.bureau.mvp.contract.MySpaceStationContract;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.OrderDetailEntity;
import com.drifting.bureau.mvp.model.entity.OrderOneEntity;
import com.drifting.bureau.mvp.model.entity.SpaceInfoEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.presenter.MySpaceStationPresenter;
import com.drifting.bureau.mvp.ui.activity.index.vr.SpaceStationVRActivity;
import com.drifting.bureau.mvp.ui.dialog.MakeScheduleDialog;
import com.drifting.bureau.mvp.ui.dialog.MakingTeaDialog;
import com.drifting.bureau.mvp.ui.dialog.MySpaceStationDialog;
import com.drifting.bureau.mvp.ui.dialog.MyTreasuryDialog;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;

import com.drifting.bureau.mvp.ui.dialog.ShareDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.base.BaseManagerActivity;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/24 17:06
 *
 * @author 我的空间站
 * module name is MySpaceStationActivity
 */
public class MySpaceStationActivity extends BaseManagerActivity<MySpaceStationPresenter> implements MySpaceStationContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
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

    private MakeScheduleDialog makeScheduleDialog;
    private PublicDialog publicDialog;
    private MyTreasuryDialog myTreasuryDialog;
    private ShareDialog shareDialog;
    private MySpaceStationDialog mySpaceStationDialog;

    private MakingTeaDialog makingTeaDialog;
    private OrderOneEntity orderOneEntity;
    private UserInfoEntity userInfoEntity;
    private Handler handler;
    private String ar_url;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, MySpaceStationActivity.class);
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

        initListener();
    }

    public void initListener() {
        getInfo();
    }

    Runnable mAdRunnable = () -> getOrderOne();


    @Override
    public void onResume() {
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

    @OnClick({R.id.toolbar_back, R.id.tv_select, R.id.tv_my_treasury, R.id.tv_upgrade, R.id.rl_total_revenue, R.id.rl_making_records, R.id.rl_withdrawal, R.id.tv_not_data, R.id.vr_space_station})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_select:   //查看
                    if (mPresenter != null & orderOneEntity != null) {
                        mPresenter.details(orderOneEntity.getLog_id(), orderOneEntity.getLevel(), orderOneEntity.getUser_id());
                    }
                    break;
                case R.id.tv_my_treasury: //我的库存
                    myTreasuryDialog = new MyTreasuryDialog(this);
                    myTreasuryDialog.show();
                    break;
                case R.id.tv_upgrade:  //升级
                    RequestUtil.create().levelcurrent(entity1 -> {
                        if (entity1 != null && entity1.getCode() == 200) {
                            mySpaceStationDialog = new MySpaceStationDialog(this, entity1.getData());
                            mySpaceStationDialog.show();
                            mySpaceStationDialog.setOnClickCallback(type -> {
                                if (type == MySpaceStationDialog.SELECT_FINISH) {
                                    myTreasuryDialog = new MyTreasuryDialog(this);
                                    myTreasuryDialog.show();
                                }
                            });
                        }
                    });
                    break;
                case R.id.rl_total_revenue: //收支记录
                    IncomeRecordActivity.start(this, false);
                    break;
                case R.id.rl_making_records:  //制作记录
                    MakingRecordActivity.start(this, false);
                    break;
                case R.id.rl_withdrawal: //提现
                    WithdrawalActivity.start(this, 1, mTvWithdrawal.getText().toString(), false);
                    break;
                case R.id.tv_not_data:  //分享
                    if (mPresenter != null) {
                        mPresenter.userplayer(2, Preferences.getUserId());
                    }
                    break;
                case R.id.vr_space_station:
                    if (!TextUtils.isEmpty(ar_url)) {
                        SpaceStationVRActivity.start(this, ar_url, false);
                    }
                    break;
            }
        }
    }


    @Override
    public void onSpcaeInfoSuccess(SpaceInfoEntity entity) {
        if (entity != null) {
            ar_url = entity.getAr_url();
            GlideUtil.create().loadLongImage(this, entity.getBackground(), mIvPic);
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
                handler.postDelayed(mAdRunnable, entity.getTimeout() * 1000);
            }
        }
    }




    public void setOrderGone(boolean type) {
        mLlContent.setVisibility(type ? View.GONE : View.VISIBLE);
        mTvNotData.setVisibility(type ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onUserInfoSuccess(int type, UserInfoEntity entity) {
        if (entity != null & entity.getUser() != null) {
            userInfoEntity = entity;
            shareDialog = new ShareDialog(this, userInfoEntity);
            shareDialog.show();
        }
    }

    @Override
    public void onCommentDetailsSuccess(CommentDetailsEntity entity) {
        if (entity != null) {
            makingTeaDialog = new MakingTeaDialog(this, entity);
            makingTeaDialog.show();
            if (orderOneEntity != null) {
                makingTeaDialog.setOnClickCallback(type -> {
                    if (type == MakingTeaDialog.SELECT_CANCEL) { //丢回太空
                        if (mPresenter != null) {
                            mPresenter.orderthrow(orderOneEntity.getSpace_order_id());
                        }
                    } else if (type == MakingTeaDialog.SELECT_FINISH) { //为他制作
                        makeScheduleDialog = new MakeScheduleDialog(this);
                        makeScheduleDialog.show();
                        makeScheduleDialog.setCancelable(false);
                        makeScheduleDialog.setOnClickCallback(type1 -> {
                            if (type1 == MakingTeaDialog.SELECT_FINISH) {
                                if (mPresenter != null) {
                                    mPresenter.ordermaking(orderOneEntity.getSpace_order_id());
                                }
                            }
                        });
                    }
                });
            }
        }
    }


    @Override
    public void onOrderMakingSuccess() { //制作成功
        showDialog(1, "制作完成", "已经制作完成并发往太空了\n拥有该订单带来的收益");
    }

    @Override
    public void onOrderThrowSuccess() { //丢回太空
        makingTeaDialog.dismiss();
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void WithdrawEvent(WithdrawEvent event) {
        if (event != null) {
            if (event.getType() == 1) {
                getInfo();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(mAdRunnable);
        }
        RequestUtil.create().disDispose();
    }

}