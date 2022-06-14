package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.billy.android.swipe.SmartSwipe;
import com.billy.android.swipe.SmartSwipeWrapper;
import com.billy.android.swipe.SwipeConsumer;
import com.billy.android.swipe.consumer.DrawerConsumer;
import com.billy.android.swipe.consumer.SlidingConsumer;
import com.billy.android.swipe.listener.SimpleSwipeListener;
import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerPlanetarySelectComponent;
import com.drifting.bureau.mvp.ui.activity.user.AboutMeActivity;
import com.drifting.bureau.mvp.ui.activity.user.AccountSettingsActivity;
import com.drifting.bureau.mvp.ui.fragment.PlanetaryDisFragment;
import com.drifting.bureau.mvp.ui.fragment.PostDriftingFragment;
import com.drifting.bureau.util.ClickUtil;
import com.hjq.shape.view.ShapeTextView;
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


    private static String EXTRA_POSTION = "extra_postion";


    private int postion;

    public static void start(Context context, int postion, boolean closePage) {
        Intent intent = new Intent(context, PlanetarySelectActivity.class);
        intent.putExtra(EXTRA_POSTION, postion);
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
        if (getIntent() != null) {
            postion = getIntent().getIntExtra(EXTRA_POSTION, 0);
        }
        initListener();
    }

    public void initListener() {
        Fragment fragment = PlanetaryDisFragment.newInstance(postion);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fame, fragment).commitAllowingStateLoss();
    }

    public Activity getActivity() {
        return this;
    }


    @OnClick({R.id.toolbar_back, R.id.tv_move_away})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_move_away:
                    MoveAwayPlanetaryActivity.start(this, 1,false);
                    break;
            }
        }
    }



    @Override
    public void showMessage(@NonNull String message) {

    }


}