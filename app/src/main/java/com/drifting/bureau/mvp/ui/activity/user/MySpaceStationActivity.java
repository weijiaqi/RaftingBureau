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
import com.drifting.bureau.di.component.DaggerMySpaceStationComponent;
import com.drifting.bureau.mvp.ui.activity.index.GetSpaceStationActivity;
import com.drifting.bureau.mvp.ui.dialog.CurrencyDialog;
import com.drifting.bureau.mvp.ui.dialog.HowToPlayDialog;
import com.drifting.bureau.mvp.ui.dialog.MakeScheduleDialog;
import com.drifting.bureau.mvp.ui.dialog.MySpaceStationDialog;
import com.drifting.bureau.mvp.ui.dialog.MyTreasuryDialog;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.drifting.bureau.mvp.ui.dialog.SelectOrderDialog;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.MySpaceStationContract;
import com.drifting.bureau.mvp.presenter.MySpaceStationPresenter;

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

    private SelectOrderDialog selectOrderDialog;
    private MakeScheduleDialog makeScheduleDialog;
    private PublicDialog publicDialog;
    private MyTreasuryDialog myTreasuryDialog;
    private MySpaceStationDialog mySpaceStationDialog;

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

    }


    @OnClick({R.id.toolbar_back, R.id.tv_select, R.id.tv_my_treasury, R.id.tv_upgrade,R.id.rl_total_revenue,R.id.rl_making_records,R.id.rl_withdrawal})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_select:   //查看
                    selectOrderDialog = new SelectOrderDialog(this);
                    selectOrderDialog.show();
                    selectOrderDialog.setCancelable(false);
                    selectOrderDialog.setOnClickCallback(type -> {
                        if (type == SelectOrderDialog.SELECT_FINISH) {
                            makeScheduleDialog = new MakeScheduleDialog(this);
                            makeScheduleDialog.show();
                            makeScheduleDialog.setCancelable(false);
                            makeScheduleDialog.setOnClickCallback(type1 -> {
                                if (type1 == SelectOrderDialog.SELECT_FINISH) {
                                    publicDialog = new PublicDialog(this);
                                    publicDialog.show();
                                }
                            });
                        }
                    });
                    break;
                case R.id.tv_my_treasury: //我的库存
                    myTreasuryDialog = new MyTreasuryDialog(this);
                    myTreasuryDialog.show();
                    break;
                case R.id.tv_upgrade:  //升级
                    mySpaceStationDialog = new MySpaceStationDialog(this);
                    mySpaceStationDialog.show();
                    break;
                case R.id.rl_total_revenue: //收支记录
                    IncomeRecordActivity.start(this,false);
                    break;
                case R.id.rl_making_records:  //制作记录
                    MakingRecordActivity.start(this,false);
                    break;
                case R.id.rl_withdrawal: //提现
                    WithdrawalActivity.start(this,false);
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