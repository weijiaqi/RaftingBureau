package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.OnClick;

/**
 * @Description: 领取星球
 * @Author : WeiJiaQI
 * @Time : 2022/5/10 10:56
 */
public class ClaimPlanetActivity extends BaseActivity {

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, ClaimPlanetActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_claim_planet;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
    }

    @OnClick({R.id.tv_enter_rb})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_enter_rb:
                    DiscoveryTourActivity.start(this, true);
                    break;
            }
        }
    }

}
