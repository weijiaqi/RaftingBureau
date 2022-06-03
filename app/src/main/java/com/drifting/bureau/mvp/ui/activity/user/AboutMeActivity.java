package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.billy.android.swipe.SmartSwipe;
import com.billy.android.swipe.SmartSwipeWrapper;
import com.billy.android.swipe.consumer.DrawerConsumer;
import com.billy.android.swipe.consumer.SlidingConsumer;
import com.drifting.bureau.R;

import com.drifting.bureau.di.component.DaggerAboutMeComponent;
import com.drifting.bureau.mvp.model.entity.AoubtMeEntity;
import com.drifting.bureau.mvp.model.entity.UserEntity;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;
import com.drifting.bureau.mvp.ui.activity.index.MoveAwayPlanetaryActivity;
import com.drifting.bureau.mvp.ui.activity.index.PlanetaryDetailActivity;
import com.drifting.bureau.mvp.ui.activity.index.PlanetarySelectActivity;
import com.drifting.bureau.mvp.ui.adapter.AboutMeAdapter;
import com.drifting.bureau.mvp.ui.fragment.PlanetaryDisFragment;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.TextUtil;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.AboutMeContract;
import com.drifting.bureau.mvp.presenter.AboutMePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/12 12:48
 *
 * @author 关于我
 * module name is AboutMeActivity
 */
public class AboutMeActivity extends BaseActivity<AboutMePresenter> implements AboutMeContract.View {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.rcy_mylist)
    RecyclerView mRcyList;
    @BindView(R.id.pr_upload_value)
    ProgressBar mPrUpload;

    private AboutMeAdapter aboutMeAdapter;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, AboutMeActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAboutMeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_about_me; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        TextUtil.setRightImage(mIvRight, R.drawable.setting);
        mRcyList.setLayoutManager(new GridLayoutManager(this, 3));
        aboutMeAdapter = new AboutMeAdapter(new ArrayList<>());
        mRcyList.setAdapter(aboutMeAdapter);
        aboutMeAdapter.setData(getData());

        if (mPresenter != null) {
            mPresenter.getUser();
        }
        mPrUpload.setProgress(50);
        setTopSwipe();
    }


    public List<AoubtMeEntity> getData() {
        List<AoubtMeEntity> list = new ArrayList<>();
        list.add(new AoubtMeEntity("漂流轨迹", "我的漂流"));
        list.add(new AoubtMeEntity("订单记录", "我的漂流"));
        list.add(new AoubtMeEntity("星际战队", "战队成员"));
        list.add(new AoubtMeEntity("附近门店", "漂流局茶饮店"));
        return list;
    }


    @Override
    public void userSuccess(UserEntity userEntity) {

    }

    public Activity getActivity() {
        return this;
    }


    @OnClick({R.id.toolbar_back, R.id.iv_right, R.id.tv_select,R.id.tv_explore})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_right:
                    AccountSettingsActivity.start(this, false);
                    break;
                case R.id.tv_select: //查看
                    PlanetarySelectActivity.start(this, 1,false);
                    break;
                case R.id.tv_explore:
                    DiscoveryTourActivity.start(this,true);
                    break;
            }
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

    }


    public void setTopSwipe() {
        View topMenu = LayoutInflater.from(this).inflate(R.layout.activity_planetary_select, null);
        Fragment fragment = PlanetaryDisFragment.newInstance(1);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fame, fragment).commitAllowingStateLoss();
        RelativeLayout toolbar_back = topMenu.findViewById(R.id.toolbar_back);
        TextView toolbar_title = topMenu.findViewById(R.id.toolbar_title);
        TextView tv_move_away = topMenu.findViewById(R.id.tv_move_away);
        toolbar_back.setOnClickListener(v -> {
            finish();
        });
        toolbar_title.setText("星球分布");
        tv_move_away.setOnClickListener(v -> {
            MoveAwayPlanetaryActivity.start(this, false);
        });
        topMenu.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        SmartSwipeWrapper topMenuWrapper = SmartSwipe.wrap(topMenu);
        DrawerConsumer slidingConsumer = new SlidingConsumer()
                .setTopDrawerView(topMenuWrapper)
                .showScrimAndShadowOutsideContentView()
                //设置遮罩层背景颜色，默认透明
                .setScrimColor(R.color.color_00_7f)
                //设置边框阴影颜色，默认透明
//                .setShadowColor(R.color.transparent)
                //设置边框阴影大小
//                .setShadowSize(SmartSwipe.dp2px(20, this))
                //设置监听
                //  .addListener(listener)
                //将SwipeConsumer类型转换为DrawerConsumer类型
                .as(DrawerConsumer.class);
        SmartSwipe.wrap(this).addConsumer(slidingConsumer);
    }


    public void startActivity(int type) {
        PlanetaryDetailActivity.start(this, type, false);
    }

}