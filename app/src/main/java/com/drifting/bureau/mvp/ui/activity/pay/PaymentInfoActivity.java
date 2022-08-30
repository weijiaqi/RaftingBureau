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
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.data.event.PaymentEvent;
import com.drifting.bureau.di.component.DaggerPaymentInfoComponent;
import com.drifting.bureau.mvp.contract.PaymentInfoContract;
import com.drifting.bureau.mvp.model.entity.PayOrderEntity;
import com.drifting.bureau.mvp.model.entity.SandPayQueryEntity;
import com.drifting.bureau.mvp.presenter.PaymentInfoPresenter;
import com.drifting.bureau.pay.WXPay;
import com.drifting.bureau.util.GsonUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.drifting.bureau.view.ClockView;
import com.jess.arms.di.component.AppComponent;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/18 10:54
 *
 * @author支付信息 module name is PaymentInfoActivity
 */
public class PaymentInfoActivity extends BaseManagerActivity<PaymentInfoPresenter> implements PaymentInfoContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToobarTitle;
    @BindView(R.id.ck_wecheat)
    CheckBox mCkWeCheat;
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
    private static final String EXRA_TOTAL_TIME = "exra_total_time";
    private String sn, total;
    private int type;
    private long totaltime;
    private long curTime;

    public static void start(Context context, int type, String sn, String tatal, long totaltime, boolean closePage) {
        Intent intent = new Intent(context, PaymentInfoActivity.class);
        intent.putExtra(EXRA_TYPE, type);
        intent.putExtra(EXRA_SN, sn);
        intent.putExtra(EXRA_TOTAL, tatal);
        intent.putExtra(EXRA_TOTAL_TIME, totaltime);
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
            totaltime = getIntent().getLongExtra(EXRA_TOTAL_TIME, 0);
        }
        initListener();
    }

    public void initListener() {
        mTvPrice.setText(total);
        //   mViewLine.setBackgroundColor(getColor(R.color.color_33));
        mTvClockview.setType(1);
        if (type == 4) {
            curTime = totaltime * 1000 + System.currentTimeMillis();
        } else {
            curTime = System.currentTimeMillis() + 1000 * 60 * 15;
        }
        mTvClockview.setEndTime(curTime);
    }


    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.toolbar_back, R.id.rl_wechat, R.id.ck_wecheat, R.id.rl_ysf, R.id.ck_ysf, R.id.tv_cofim_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.rl_wechat:
                setCheckStatus(1);
                break;
            case R.id.ck_wecheat:
                if (mCkWeCheat.isChecked()) {
                    mCkYsf.setChecked(false);
                }
                break;
            case R.id.rl_ysf:
                setCheckStatus(3);
                break;
            case R.id.ck_ysf: //云闪付
                if (mCkYsf.isChecked()) {
                    mCkWeCheat.setChecked(false);
                }
                break;
            case R.id.tv_cofim_pay: //立即购买
                if (!mCkYsf.isChecked() && !mCkWeCheat.isChecked()) {
                    showMessage("请选择支付方式");
                    return;
                }
                if (mPresenter != null) {
                    if (mCkYsf.isChecked()) {  //云闪付
                        mPresenter.payOrder(sn, "sand", 1);
                    } else if (mCkWeCheat.isChecked()) { //微信
                        mPresenter.payOrder(sn, "wechat", 2);
                    }
                }
                break;
        }

    }


    @Override
    public void payOrderSuccess(PayOrderEntity entity, int type) {
        if (entity != null) {
            if (type == 1) { //云闪付
                if (entity.getTn() != null) {
                    startUnionpay(PaymentInfoActivity.this, entity.getTn());
                }
            } else if (type == 2) { //微信
                try {
                    wecheatpay(new JSONObject(GsonUtil.toJson(entity)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 微信支付
     */
    public void wecheatpay(JSONObject param) {
        String appid = param.optString("appId");
        if (TextUtils.isEmpty(appid)) {
            mWXPayCallBack.onError(WXPay.ERROR_PAY_PARAM);
            return;
        }
        WXPay.init(this, appid);
        WXPay.getInstance().doPay(param, mWXPayCallBack);
    }

    /**
     * 微信回调
     */
    private WXPay.WXPayResultCallBack mWXPayCallBack = new WXPay.WXPayResultCallBack() {
        @Override
        public void onSuccess() {
            sandPayQuery("wechat");
        }

        @Override
        public void onError(int error_code) {
            switch (error_code) {
                case WXPay.NO_OR_LOW_WX:
                    ToastUtil.showToast("未安装微信或微信版本过低");
                    break;
                case WXPay.ERROR_PAY_PARAM:
                    ToastUtil.showToast("参数错误");
                    break;
                case WXPay.ERROR_PAY:
                    ToastUtil.showToast("支付失败");
                    break;
            }
        }

        @Override
        public void onCancel() {
            ToastUtil.showToast("支付取消");
        }
    };



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
                    sandPayQuery("sand");
                }
            } else if (str.equalsIgnoreCase("fail")) {
                showMessage("支付失败！");
            } else if (str.equalsIgnoreCase("cancel")) {
                showMessage("你已取消了本次订单的支付！");
            }
        }
    }

    /**
     * @description 主动查询支付状态
     */
    public void sandPayQuery(String terminal){
        if (mPresenter != null) {
            mPresenter.sandPayOrderQuery(sn,terminal);
        }
    }

    @Override
    public void sandPayQuerySuccess(SandPayQueryEntity entity) {
        if (entity != null) {
            if (entity.getStatus() == 1) {
                PaySuccess();
            } else {
                showMessage("暂未查询到支付结果，请稍后尝试");
            }
        }
    }

    public void PaySuccess() {
        showMessage("购买成功");
        EventBus.getDefault().post(new PaymentEvent());
        finish();
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
        if (status == 1) {  //微信
            mCkWeCheat.setChecked(!mCkWeCheat.isChecked());
            mCkYsf.setChecked(false);
        } else if (status == 3) {   //云闪付
            mCkYsf.setChecked(!mCkYsf.isChecked());
            mCkWeCheat.setChecked(false);
        }
    }


}