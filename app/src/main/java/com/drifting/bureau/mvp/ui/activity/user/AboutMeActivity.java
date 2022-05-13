package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.drifting.bureau.R;

import com.drifting.bureau.di.component.DaggerAboutMeComponent;
import com.drifting.bureau.mvp.ui.adapter.AboutMeAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.AboutMeContract;
import com.drifting.bureau.mvp.presenter.AboutMePresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/12 12:48
 *
 * @author 谢况
 * module name is AboutMeActivity
 */
public class AboutMeActivity extends BaseActivity<AboutMePresenter> implements AboutMeContract.View {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.rcy_mylist)
    RecyclerView mRcyList;

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
        TextUtil.setRightImage(mIvRight,R.drawable.setting);
        mRcyList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        aboutMeAdapter = new AboutMeAdapter(new ArrayList<>());
        mRcyList.setAdapter(aboutMeAdapter);
    }


    public Activity getActivity() {
        return this;
    }


    @OnClick({R.id.toolbar_back})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
            }
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}