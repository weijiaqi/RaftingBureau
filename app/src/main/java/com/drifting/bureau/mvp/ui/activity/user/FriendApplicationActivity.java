package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.AnswerCompletedEvent;
import com.drifting.bureau.data.event.FriendApplicationEvent;
import com.drifting.bureau.di.component.DaggerFriendApplicationComponent;
import com.drifting.bureau.mvp.model.entity.FriendApplicationEntity;
import com.drifting.bureau.mvp.model.entity.SysmessageMineEntity;
import com.drifting.bureau.mvp.ui.adapter.FriendApplicationAdapter;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.FriendApplicationContract;
import com.drifting.bureau.mvp.presenter.FriendApplicationPresenter;
import com.rb.core.xrecycleview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/18 15:54
 *
 * @author 好友申请
 * module name is FriendApplicationActivity
 */
public class FriendApplicationActivity extends BaseActivity<FriendApplicationPresenter> implements FriendApplicationContract.View, XRecyclerView.LoadingListener {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    private int mPage = 1;
    private int limit = 10;
    private FriendApplicationAdapter friendApplicationAdapter;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, FriendApplicationActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFriendApplicationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_refresh_layout; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("好友申请");
        initListener();
    }

    public void initListener() {
        mRcyPublic.setLayoutManager(new LinearLayoutManager(this));
        mRcyPublic.setLoadingListener(this);
        friendApplicationAdapter = new FriendApplicationAdapter(new ArrayList<>());
        mRcyPublic.setAdapter(friendApplicationAdapter);

        getData(mPage, true);
    }

    public void getData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.applyToMe(mPage, limit, loadType);
        }
    }


    @Override
    public void onRefresh() {
        mPage = 1;
        getData(mPage, true);
    }

    @Override
    public void onLoadMore() {
        getData(mPage, false);
    }

    @Override
    public void onloadStart() {
        if (friendApplicationAdapter.getDatas() == null || friendApplicationAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(this, mFlState);
        }
    }

    @Override
    public void onApplyToMeSuccess(FriendApplicationEntity entity, boolean isNotData) {
        if (mPage == 1 && friendApplicationAdapter.getItemCount() != 0) {
            friendApplicationAdapter.clearData();
        }
        List<FriendApplicationEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            if (isNotData) {
                mPage = 2;
                friendApplicationAdapter.setData(list);
            } else {
                mPage++;
                friendApplicationAdapter.addData(list);
            }
        }
    }

    @Override
    public void loadFinish(boolean loadType, boolean isNotData) {
        if (mRcyPublic == null) {
            return;
        }
        if (!loadType && isNotData) {
            mRcyPublic.loadEndLine();
        } else {
            mRcyPublic.refreshEndComplete();
        }
    }

    @Override
    public void loadState(int dataState) {
        if (friendApplicationAdapter.getDatas() == null || friendApplicationAdapter.getDatas().size() == 0) {
            if (dataState == ViewUtil.NOT_DATA) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_DATA);
            } else if (dataState == ViewUtil.NOT_SERVER) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_SERVER);
            } else if (dataState == ViewUtil.NOT_NETWORK) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_NETWORK);
            } else {
                ViewUtil.create().setView(mFlState);
            }
        } else {
            ViewUtil.create().setView(mFlState);
        }
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


    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void FriendApplicationEvent(FriendApplicationEvent event) {
        if (event != null) {
            onRefresh();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.create().disDispose();
    }
}