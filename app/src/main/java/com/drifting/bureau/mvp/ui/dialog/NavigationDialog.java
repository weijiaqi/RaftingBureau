package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.util.MapsUtil;
import com.jess.arms.base.BaseDialog;

/**
 * @description  导航跳转
 * @author 卫佳琪1
 * @time 18:06 18:06
 */

public class NavigationDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private TextView mGaode, mTencent, mBaidu;
    private String lng, lat;

    public NavigationDialog(@NonNull Context context, String lng, String lat) {
        super(context);
        this.context = context;
        this.lng = lng;
        this.lat = lat;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mGaode = findViewById(R.id.tv_gaode);
        mTencent = findViewById(R.id.tv_tencent);
        mBaidu = findViewById(R.id.tv_baidu);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mGaode.setOnClickListener(this);
        mTencent.setOnClickListener(this);
        mBaidu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_gaode:
                dismiss();
                //跳转到高德地图
                MapsUtil.goToGodeActivity(context, Double.valueOf(lng), Double.valueOf(lat));
                break;
            case R.id.tv_tencent:
                dismiss();
                //跳转到腾讯地图
                 MapsUtil.goToTencentActivity(context, Double.valueOf(lng), Double.valueOf(lat));
                break;
            case R.id.tv_baidu:
                dismiss();
                //跳转到百度地图
                MapsUtil.goToBaiduActivity(context, null, Double.valueOf(lng), Double.valueOf(lat));
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_navigation;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }


    @Override
    protected void getWindows(Window window) {
        super.getWindows(window);
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.EnterDialog);
        }
    }
}
