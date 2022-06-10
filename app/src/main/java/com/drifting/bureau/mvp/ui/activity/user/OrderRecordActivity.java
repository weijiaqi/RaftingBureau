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
import com.drifting.bureau.data.event.MyBlindBoxRefreshEvent;
import com.drifting.bureau.data.event.OrderRecordEvent;
import com.drifting.bureau.di.component.DaggerOrderRecordComponent;
import com.drifting.bureau.mvp.model.entity.DriftingTrackEntity;

import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;
import com.drifting.bureau.mvp.ui.adapter.OrderRecordAdapter;
import com.drifting.bureau.mvp.ui.holder.OrderRecordHolder;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.OrderRecordContract;
import com.drifting.bureau.mvp.presenter.OrderRecordPresenter;
import com.rb.core.xrecycleview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/28 11:27
 *
 * @author 订单记录
 * module name is OrderRecordActivity
 */
public class OrderRecordActivity extends BaseActivity<OrderRecordPresenter> implements OrderRecordContract.View,XRecyclerView.LoadingListener {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    private int mPage = 1;
    private int limit = 10;
    private OrderRecordAdapter orderRecordAdapter;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, OrderRecordActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderRecordComponent //如找不到该类,请编译一下项目
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
        mToolbarTitle.setText("订单记录");
        initListener();
    }


    public void initListener() {
        mRcyPublic.setLayoutManager(new LinearLayoutManager(this));
        mRcyPublic.setLoadingListener(this);
        orderRecordAdapter = new OrderRecordAdapter(new ArrayList<>());
        mRcyPublic.setAdapter(orderRecordAdapter);
        getData(mPage, true);
    }

    public void getData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.ordermine(mPage, limit, loadType);
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
    public void onloadStart() {
        if (orderRecordAdapter.getDatas() == null || orderRecordAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(this, mFlState);
        }
    }

    @Override
    public void onOrderMineSuccess(OrderRecordEntity entity, boolean isNotData) {
        if (mPage == 1 && orderRecordAdapter.getItemCount() != 0) {
            orderRecordAdapter.clearData();
        }
        List<OrderRecordEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            if (isNotData) {
                mPage = 2;
                orderRecordAdapter.setData(list);
            } else {
                mPage++;
                orderRecordAdapter.addData(list);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OrderRecordEvent(OrderRecordEvent orderRecordEvent) {
        if (orderRecordEvent != null) {
            onRefresh();
        }
    }


    @Override
    public void loadState(int type) {
        if (orderRecordAdapter.getDatas() == null || orderRecordAdapter.getDatas().size() == 0) {
            if (type == ViewUtil.NOT_DATA) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_DATA);
            } else if (type == ViewUtil.NOT_SERVER) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_SERVER);
            } else if (type == ViewUtil.NOT_NETWORK) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_NETWORK);
            } else {
                ViewUtil.create().setView(mFlState);
            }
        } else {
            ViewUtil.create().setView(mFlState);
        }
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }


}