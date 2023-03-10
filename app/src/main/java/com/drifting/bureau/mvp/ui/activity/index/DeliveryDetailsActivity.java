package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.di.component.DaggerDeliveryDetailsComponent;
import com.drifting.bureau.mvp.contract.DeliveryDetailsContract;
import com.drifting.bureau.mvp.model.entity.DeliveryDetailsEntity;
import com.drifting.bureau.mvp.presenter.DeliveryDetailsPresenter;
import com.drifting.bureau.mvp.ui.adapter.DeliveryDetailsAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.di.component.AppComponent;
import com.rb.core.xrecycleview.XRecyclerView;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/19 17:11
 *
 * @author 传递详情
 * module name is DeliveryDetailsActivity
 */
public class DeliveryDetailsActivity extends BaseManagerActivity<DeliveryDetailsPresenter> implements DeliveryDetailsContract.View, XRecyclerView.LoadingListener {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_delivery)
    XRecyclerView mRcyDelivery;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    private int mPage = 1;
    private int limit = 10;
    private DeliveryDetailsAdapter deliveryDetailsAdapter;
    private static String EXTRA_MESSAGE_ID = "message_id";
    private static String EXTRA_CODE_CITY = "code_city";
    private int id;
    private String codecity;


    public static void start(Context context, int id, String code_city, boolean closePage) {
        Intent intent = new Intent(context, DeliveryDetailsActivity.class);
        intent.putExtra(EXTRA_MESSAGE_ID, id);
        intent.putExtra(EXTRA_CODE_CITY, code_city);
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
        id = getInt(EXTRA_MESSAGE_ID);
        codecity =getString(EXTRA_CODE_CITY);
        initListener();
    }

    public void initListener() {
        mRcyDelivery.setNestedScrollingEnabled(false);
        mRcyDelivery.setLayoutManager(new LinearLayoutManager(this));
        mRcyDelivery.setLoadingListener(this);
        deliveryDetailsAdapter = new DeliveryDetailsAdapter();
        mRcyDelivery.setAdapter(deliveryDetailsAdapter);
        getData(mPage, true);
    }

    public void getData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.pathdetails(id,codecity, mPage, limit, loadType);
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
        if (deliveryDetailsAdapter.getDatas() == null || deliveryDetailsAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(this, mFlState);
        }
    }


    @Override
    public void onPathDetailSuccess(DeliveryDetailsEntity entity, boolean isNotData) {
        if (entity != null) {
            List<DeliveryDetailsEntity.MessagePathBean> list = entity.getMessage_path();
            for (int i = 0; i < list.size(); i++) {
                if (!TextUtils.isEmpty(list.get(i).getContent())) {
                    list.get(i).setType(1);
                } else if (!TextUtils.isEmpty(list.get(i).getAudio())) {
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