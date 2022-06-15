package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerDeliveryDetailsComponent;
import com.drifting.bureau.mvp.model.entity.DeliveryDetailsEntity;
import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
import com.drifting.bureau.mvp.ui.adapter.DeliveryDetailsAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.DeliveryDetailsContract;
import com.drifting.bureau.mvp.presenter.DeliveryDetailsPresenter;
import com.rb.core.xrecycleview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/19 17:11
 *
 * @author 传递详情
 * module name is DeliveryDetailsActivity
 */
public class DeliveryDetailsActivity extends BaseActivity<DeliveryDetailsPresenter> implements DeliveryDetailsContract.View, XRecyclerView.LoadingListener {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_delivery)
    XRecyclerView mRcyDelivery;
    @BindView(R.id.tv_drifting_name)
    TextView mTvDriftingName;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_planet)
    TextView mTvPlanet;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    @BindView(R.id.rl_top)
    RelativeLayout mRlTop;
    @BindView(R.id.line1)
    View line1;
    private int mPage = 1;
    private int limit = 10;
    private DeliveryDetailsAdapter deliveryDetailsAdapter;

    private static String EXTRA_MESSAGE_ID = "message_id";
    private int id;

    private DeliveryDetailsEntity.MessagePathBean messageBean;

    public static void start(Context context, int id, boolean closePage) {
        Intent intent = new Intent(context, DeliveryDetailsActivity.class);
        intent.putExtra(EXTRA_MESSAGE_ID, id);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDeliveryDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_delivery_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("传递详情");
        if (getIntent() != null) {
            id = getIntent().getIntExtra(EXTRA_MESSAGE_ID, 0);
        }
        initListener();
    }

    public void initListener() {
        mRcyDelivery.setNestedScrollingEnabled(false);
        mRcyDelivery.setLayoutManager(new LinearLayoutManager(this));
        mRcyDelivery.setLoadingListener(this);
        mRcyDelivery.setPullRefreshEnabled(false);
        deliveryDetailsAdapter = new DeliveryDetailsAdapter();
        mRcyDelivery.setAdapter(deliveryDetailsAdapter);
        getData(mPage, true);
    }

    public void getData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.pathdetails(id, mPage, limit, loadType);
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
    public void onPathDetailSuccess(DeliveryDetailsEntity entity, boolean isNotData) {
        if (entity != null) {
            if ( mPage==1&& entity.getMessage() != null) {
                mRlTop.setVisibility(View.VISIBLE);
                line1.setVisibility(View.VISIBLE);
                messageBean = entity.getMessage();
                mTvName.setText("昵称：" + messageBean.getUser_name());
                mTvPlanet.setText(messageBean.getPlanet_level_name() + "     " + messageBean.getLevel_name());
            }

            List<DeliveryDetailsEntity.MessagePathBean> list = entity.getMessage_path();
            if (mPage == 1) {
                list.add(0, messageBean);
            }
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getType_id() == 1) {
                    list.get(i).setType(1);
                } else if (list.get(i).getType_id() == 2) {
                    list.get(i).setType(2);
                } else {
                    list.get(i).setType(3);
                }
            }
            if (list != null && list.size() > 0) {
                if (isNotData) {
                    mPage = 2;
                    deliveryDetailsAdapter.setData(list);
                } else {
                    mPage++;
                    deliveryDetailsAdapter.addData(list);
                }
            }
        }

    }

    @Override
    public void onloadStart() {
        if (deliveryDetailsAdapter.getDatas() == null || deliveryDetailsAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(this, mFlState);
        }
    }


    @Override
    public void loadFinish(boolean loadType, boolean isNotData) {
        if (mRcyDelivery == null) {
            return;
        }
        if (!loadType && isNotData) {
            mRcyDelivery.loadEndLine();
        } else {
            mRcyDelivery.refreshEndComplete();
        }
    }

    @Override
    public void loadState(int type) {
        if (type == ViewUtil.NOT_DATA) {
            ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_DATA);
        } else if (type == ViewUtil.NOT_SERVER) {
            ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_SERVER);
        } else if (type == ViewUtil.NOT_NETWORK) {
            ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_NETWORK);
        } else {
            ViewUtil.create().setView(mFlState);
        }
    }

    @Override
    public void onNetError() {

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
        ToastUtil.showToast(message);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.create().disDispose();
    }
}