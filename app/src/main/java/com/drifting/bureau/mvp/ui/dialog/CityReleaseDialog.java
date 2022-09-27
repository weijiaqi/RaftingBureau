package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;


/**
 * @Description: 城市发布
 * @Author : WeiJiaQI
 * @Time : 2022/9/17 11:58
 */
public class CityReleaseDialog extends BaseDialog implements View.OnClickListener {

    public static final int SELECT_PAY = 0x01;
    public static final int SELECT_FREE = 0x02;

    private ImageView mIvCityPay, mIvCityFree;

    public CityReleaseDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        super.initView();
        mIvCityPay = findViewById(R.id.iv_city_pay);
        mIvCityFree = findViewById(R.id.iv_city_free);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mIvCityPay.setOnClickListener(this);
        mIvCityFree.setOnClickListener(this);
    }


    @Override
    protected int getContentView() {
        return R.layout.dialog_city_release;
    }

    @Override
    protected float getDialogWith() {
        return 0.8f;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_city_pay:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_PAY);
                }
                break;
            case R.id.iv_city_free:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_FREE);
                }
                break;
        }
    }

}
