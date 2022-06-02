package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerPlanetarySelectComponent;
import com.drifting.bureau.mvp.ui.activity.user.AccountSettingsActivity;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.PlanetarySelectContract;
import com.drifting.bureau.mvp.presenter.PlanetarySelectPresenter;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/01 11:04
 *
 * @author 星球分布
 * module name is PlanetarySelectActivity
 */
public class PlanetarySelectActivity extends BaseActivity<PlanetarySelectPresenter> implements PlanetarySelectContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, PlanetarySelectActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPlanetarySelectComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_planetary_select; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("星球分布");
        initListener();
    }

    public void initListener() {

    }

    public Activity getActivity() {
        return this;
    }


    @OnClick({R.id.toolbar_back, R.id.tv_planet11,R.id.tv_move_away})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_planet11: //荒芜星球
                    PlanetaryDetailActivity.start(this,false);
                    break;
                case R.id.tv_move_away:
                    MoveAwayPlanetaryActivity.start(this,false);
                    break;
            }
        }
    }


    @Override
    public void showMessage(@NonNull String message) {

    }
}