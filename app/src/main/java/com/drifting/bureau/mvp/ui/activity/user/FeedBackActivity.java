package com.drifting.bureau.mvp.ui.activity.user;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerFeedBackComponent;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.FeedBackContract;
import com.drifting.bureau.mvp.presenter.FeedBackPresenter;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/30 16:46
 * @author 意见反馈
 * module name is FeedBackActivity
 */
public class FeedBackActivity extends BaseActivity<FeedBackPresenter> implements FeedBackContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFeedBackComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState){
        return R.layout.activity_feed_back; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("意见反馈");
        initListener();
    }

    public void initListener() {

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

    public Activity getActivity(){
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}