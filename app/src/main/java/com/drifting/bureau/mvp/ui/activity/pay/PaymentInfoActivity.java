package com.drifting.bureau.mvp.ui.activity.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.PaymentEvent;
import com.drifting.bureau.di.component.DaggerPaymentInfoComponent;
import com.drifting.bureau.mvp.contract.PaymentInfoContract;
import com.drifting.bureau.mvp.model.entity.PayOrderEntity;
import com.drifting.bureau.mvp.model.entity.SandPayQueryEntity;
import com.drifting.bureau.mvp.presenter.PaymentInfoPresenter;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.drifting.bureau.view.ClockView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.unionpay.UPPayAssistEx;

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

    @BindView(R.id.ck_wecheat)
    CheckBox mCkWeCheat;
    @BindView(R.id.ck_alipay)
    CheckBox mCkAlipay;

    @BindView(R.id.ck_ysf)
    CheckBox mCkYsf;

    @BindView(R.id.clockview)
    ClockView mTvClockview;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.rl_ysf)
    RelativeLayout mRlYsf;
    private static final String EXRA_TYPE = "exra_type";
    private static final String EXRA_SN = "exra_sn";
    private static final String EXRA_TOTAL = "exra_total";
    private String sn, total;
    private int type;

    public static void start(Context context, int type, String sn, String tatal, boolean closePage) {
        Intent intent = new Intent(context, PaymentInfoActivity.class);
        intent.putExtra(EXRA_TYPE, type);
        intent.putExtra(EXRA_SN, sn);
        intent.putExtra(EXRA_TOTAL, tatal);
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
            type = getIntent().getIntExtra(EXRA_TYPE, 0);
            sn = getIntent().getStringExtra(EXRA_SN);
            total = getIntent().getStringExtra(EXRA_TOTAL);
        }
        initListener();
    }

    public void initListener() {
        mTvPrice.setText(total);
        //   mViewLine.setBackgroundColor(getColor(R.color.color_33));
        mTvClockview.setType(1);
        long curTime = System.currentTimeMillis() + 1000 * 60 * 15;
        mTvClockview.setEndTime(curTime);
    }


    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.toolbar_back, R.id.rl_wechat, R.id.ck_wecheat, R.id.rl_alipay, R.id.ck_alipay, R.id.rl_ysf, R.id.tv_cofim_pay})
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
                case R.id.rl_ysf:
                    mCkYsf.setChecked(!mCkYsf.isChecked());
                    break;
                case R.id.tv_cofim_pay: //立即购买
                    if (!mCkYsf.isChecked()) {
                        showMessage("请选择支付方式");
                        return;
                    }
                    if (mPresenter != null) {
                        mPresenter.payOrder(sn, "sand");
                    }
                    break;
            }
        }
    }


    @Override
    public void payOrderSuccess(PayOrderEntity entity) {
        if (entity != null && entity.getTn() != null) {
            startUnionpay(PaymentInfoActivity.this, entity.getTn());
        }
    }

    @Override
    public void sandPayQuerySuccess(SandPayQueryEntity entity) {
        if (entity != null) {
            if (entity.getStatus() == 1) {
                showMessage("购买成功");
                EventBus.getDefault().post(new PaymentEvent());
                finish();
            } else {
                showMessage("暂未查询到支付结果，请稍后尝试");
            }
        }
    }


    /**
     * 银联云闪付
     */
    public static void startUnionpay(Context context, String tradeNo) {
        UPPayAssistEx.startPay(context, null, null, tradeNo, "00");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String str = data.getExtras().getString("pay_result");
        if (str != null) {
            if (str.equalsIgnoreCase("success")) {
                if (!TextUtils.isEmpty(sn)) {
                    if (mPresenter != null) {
                        mPresenter.sandPayOrderQuery(sn);
                    }
                }
            } else if (str.equalsIgnoreCase("fail")) {
                showMessage("支付失败！");
            } else if (str.equalsIgnoreCase("cancel")) {
                showMessage("你已取消了本次订单的支付！");
            }
        }
    }

    @Override
    public void showLoading() {
        ViewUtil.create().show(this);
    }

    @Override
    public void hideLoading() {
        ViewUtil.create().dismiss();
    }


    @Override
    public void onNetError() {

    }


    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    public void setCheckStatus(int status) {
        mCkWeCheat.setChecked(status == 1 ? true : false);
        mCkAlipay.setChecked(status == 1 ? false : true);
    }

}