package com.drifting.bureau.mvp.ui.activity.pay;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.PaymentEvent;
import com.drifting.bureau.mvp.model.entity.PayOrderEntity;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.view.ClockView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.component.DaggerPaymentInfoComponent;
import com.drifting.bureau.mvp.contract.PaymentInfoContract;
import com.drifting.bureau.mvp.presenter.PaymentInfoPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/18 10:54
 *
 * @author支付信息 module name is PaymentInfoActivity
 */
public class PaymentInfoActivity extends BaseActivity<PaymentInfoPresenter> implements PaymentInfoContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToobarTitle;
    @BindView(R.id.view_color_line)
    View mViewLine;
    @BindView(R.id.ck_wecheat)
    CheckBox mCkWeCheat;
    @BindView(R.id.ck_alipay)
    CheckBox mCkAlipay;
    @BindView(R.id.clockview)
    ClockView mTvClockview;

    private static final String EXRA_SN = "exra_sn";
    private String sn;

    public static void start(Context context, String sn, boolean closePage) {
        Intent intent = new Intent(context, PaymentInfoActivity.class);
        intent.putExtra(EXRA_SN, sn);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPaymentInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_payment_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToobarTitle.setText("订单支付");
        if (getIntent() != null) {
            sn = getIntent().getStringExtra(EXRA_SN);
        }
        initListener();
    }

    public void initListener() {
        mViewLine.setBackgroundColor(getColor(R.color.color_33));
        mTvClockview.setType(1);
        long curTime = System.currentTimeMillis() + 10000;
        mTvClockview.setEndTime(curTime);
    }

    @Override
    public void payOrderSuccess(PayOrderEntity entity) {
         showMessage("购买成功");
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

    @OnClick({R.id.toolbar_back, R.id.rl_wechat, R.id.ck_wecheat, R.id.rl_alipay, R.id.ck_alipay, R.id.tv_cofim_pay})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.rl_wechat:
                case R.id.ck_wecheat:
                    setCheckStatus(1);
                    break;
                case R.id.rl_alipay:
                case R.id.ck_alipay:
                    setCheckStatus(2);
                    break;
                case R.id.tv_cofim_pay: //立即购买
//                    EventBus.getDefault().post(new PaymentEvent());
//                    finish();
                    if (mPresenter!=null){
                        mPresenter.payOrder(sn);
                    }
                    break;
            }
        }
    }


    public void setCheckStatus(int status) {
        mCkWeCheat.setChecked(status == 1 ? true : false);
        mCkAlipay.setChecked(status == 1 ? false : true);
    }

}