package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;
import com.drifting.bureau.mvp.ui.activity.index.MoveAwayPlanetaryActivity;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 领取星球
 * @Author : WeiJiaQI
 * @Time : 2022/5/10 10:56
 */
public class ClaimPlanetActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView mTvName;

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
        mTvName.setText(Preferences.getUserName());
    }

    @OnClick({R.id.tv_enter_rb, R.id.tv_perfect})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_enter_rb:
                    DiscoveryTourActivity.start(this, true);
                    break;
                case R.id.tv_perfect:
                    MoveAwayPlanetaryActivity.start(ClaimPlanetActivity.this, 2, false);
                    break;
            }
        }
    }



}
