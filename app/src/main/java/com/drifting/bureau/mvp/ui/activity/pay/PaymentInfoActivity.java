package com.drifting.bureau.mvp.ui.activity.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.data.event.CouponAvailableEvent;
import com.drifting.bureau.data.event.PaymentEvent;
import com.drifting.bureau.di.component.DaggerPaymentInfoComponent;
import com.drifting.bureau.mvp.contract.PaymentInfoContract;
import com.drifting.bureau.mvp.model.entity.CouponsMineEntity;
import com.drifting.bureau.mvp.model.entity.PayOrderEntity;
import com.drifting.bureau.mvp.model.entity.SandPayQueryEntity;
import com.drifting.bureau.mvp.presenter.PaymentInfoPresenter;
import com.drifting.bureau.mvp.ui.activity.index.CouponAvailableActivity;
import com.drifting.bureau.mvp.ui.dialog.CurrencyDialog;
import com.drifting.bureau.pay.WXPay;
import com.drifting.bureau.util.GsonUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.view.ClockView;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.di.component.AppComponent;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
    @BindView(R.id.tv_usable)
    ShapeTextView mTvUsable;
    @BindView(R.id.rl_coupon)
    RelativeLayout mRlCoupon;
    private static final String EXRA_TYPE = "exra_type";
    private static final String EXRA_WAKE_UP_PAY = "exra_wake_up_pay";
    private static final String EXRA_COUPON_NAME = "exra_coupon_name";
    private static final String EXRA_COUPON_CODE = "exra_coupon_code";
    private static final String EXRA_COUPON_MONEY = "exra_coupon_money";
    private static final String EXRA_USE_SCENE = "use_scene";
    private static final String EXRA_SN = "exra_sn";
    private static final String EXRA_TOTAL = "exra_total";
    private static final String EXRA_TOTAL_TIME = "exra_total_time";
    private String sn, total, scene, discount, coupon_name, coupon_code, coupon_money;
    private int type, wake;
    private long totaltime;
    private long curTime;
    private CouponsMineEntity.ListBean listBean;
    private CurrencyDialog currencyDialog;

    private String[] scenes = new String[]{"", "createCD", "attendCD", "buyBox", "", "buySpace"};

    public static void start(Context context, int type, String sn, String total, boolean closePage) {
        Intent intent = new Intent(context, PaymentInfoActivity.class);
        intent.putExtra(EXRA_TYPE, type);
        intent.putExtra(EXRA_SN, sn);
        intent.putExtra(EXRA_TOTAL, total);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    public static void start(Context context, int type, String sn, String total, long totaltime, int Wake_up_pay, String coupon_name, String coupon_code, String coupon_money, String use_scene, boolean closePage) {
        Intent intent = new Intent(context, PaymentInfoActivity.class);
        intent.putExtra(EXRA_TYPE, type);
        intent.putExtra(EXRA_SN, sn);
        intent.putExtra(EXRA_TOTAL, total);
        intent.putExtra(EXRA_TOTAL_TIME, totaltime);
        intent.putExtra(EXRA_WAKE_UP_PAY, Wake_up_pay);
        intent.putExtra(EXRA_COUPON_NAME, coupon_name);
        intent.putExtra(EXRA_COUPON_CODE, coupon_code);
        intent.putExtra(EXRA_COUPON_MONEY, coupon_money);
        intent.putExtra(EXRA_USE_SCENE, use_scene);
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
        type = getInt(EXRA_TYPE);
        sn = getString(EXRA_SN);
        total = getString(EXRA_TOTAL);
        totaltime = getLong(EXRA_TOTAL_TIME);
        wake = getInt(EXRA_WAKE_UP_PAY);
        coupon_name = getString(EXRA_COUPON_NAME);
        coupon_code = getString(EXRA_COUPON_CODE);
        coupon_money = getString(EXRA_COUPON_MONEY);
        initListener();
    }

    public void initListener() {
        mTvPrice.setText(total);
        //   mViewLine.setBackgroundColor(getColor(R.color.color_33));
        mTvClockview.setType(1);
        if (type == 4) {
            curTime = totaltime * 1000 + System.currentTimeMillis();
            scene = getString(EXRA_USE_SCENE);
        } else {
            curTime = System.currentTimeMillis() + 1000 * 60 * 15;
            scene = scenes[type];
        }
        if (wake == 1) {  //1唤起过支付
            mTvUsable.setText(coupon_name + getString(R.string.discount_price, coupon_money));
        }else {
            RequestUtil.create().available(scene, entity -> {
                if (entity != null && entity.getCode() == 200) {
                    setCoupon(entity.getData().getTotal());
                }
            });
        }
        mTvClockview.setEndTime(curTime);
    }


    public void setCoupon(int total) {
        mRlCoupon.setClickable(total != 0 ? true : false);
        mTvUsable.setText(total != 0 ? "有可使用的优惠券" : "没有可使用的优惠券");
        if (total != 0) {
            mTvUsable.getTextColorBuilder().setTextGradientColors(getColor(R.color.color_6c), getColor(R.color.color_6d)).intoTextColor();
        } else {
            mTvUsable.getTextColorBuilder().setTextColor(getColor(R.color.color_66)).intoTextColor();
        }
    }


    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.toolbar_back, R.id.rl_coupon, R.id.rl_wechat, R.id.ck_wecheat, R.id.rl_ysf, R.id.ck_ysf, R.id.tv_cofim_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.rl_coupon:  //可使用优惠券
                if (type==4 &&wake==1){
                    showMessage("当前无法重新选择优惠券!");
                }else {
                    CouponAvailableActivity.start(this, listBean, total, scene, false);
                }
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
                if (coupon_code != null && !TextUtils.isEmpty(coupon_code)) {   //使用了优惠券
                    currencyDialog = new CurrencyDialog(PaymentInfoActivity.this);
                    currencyDialog.show();
                    currencyDialog.setType(2);
                    currencyDialog.setTitleText("提示");
                    currencyDialog.setContentText("点击确认支付按钮后\n选择的优惠券不可更改!");
                    currencyDialog.setText("取消", "确认支付");
                    currencyDialog.setOnClickCallback(type -> {
                        if (type == CurrencyDialog.SELECT_FINISH) {
                            checkPay(coupon_code);
                        }
                    });
                } else {
                    checkPay("");
                }
                break;
        }

    }

    //选择支付方式
    public void checkPay(String code) {
        if (mCkYsf.isChecked()) {  //云闪付
            payOder("sand", code, 1);
        } else if (mCkWeCheat.isChecked()) { //微信
            payOder("wechat", code, 2);
        }
    }

    //唤起支付订单接口
    public void payOder(String terminal, String code, int type) {
        if (mPresenter != null) {
            mPresenter.payOrder(sn, terminal, code, scene, type);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void CouponAvailableEvent(CouponAvailableEvent event) {
        if (event != null) {
            this.listBean = event.getListBean();
            if (listBean.getCoupon_type() == 1) {  //折扣
                discount = StringUtil.getDouble(StringUtil.sub(Double.parseDouble(total), StringUtil.mul(Double.parseDouble(total), listBean.getDiscount_rate())),2)+"";
            } else if (listBean.getCoupon_type() == 2) {//免单
                discount = total;
            } else {   ////满减和代金券
                discount = listBean.getDeduct_money();
            }
            coupon_code = listBean.getCode();
            mTvUsable.setText(listBean.getName() + getString(R.string.discount_price, discount));
        }
    }


    @Override
    public void payOrderSuccess(PayOrderEntity entity, int type) {
        if (entity != null) {
            if (entity.getNeed_pay() == 1) {   //需要现金支付
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
            } else {  //免单 直接走成功方法
                PaySuccess();
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
    public void sandPayQuery(String terminal) {
        if (mPresenter != null) {
            mPresenter.sandPayOrderQuery(sn, terminal);
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

        new Handler().postDelayed(() -> {
            PaymentEvent paymentEvent = new PaymentEvent();
            paymentEvent.setType(type);
            EventBus.getDefault().post(paymentEvent);
            finish();
        }, 1000);

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