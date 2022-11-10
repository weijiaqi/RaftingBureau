package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.data.event.CouponAvailableEvent;
import com.drifting.bureau.di.component.DaggerCouponAvailableComponent;
import com.drifting.bureau.mvp.contract.MyCouponContract;
import com.drifting.bureau.mvp.model.entity.CouponsMineEntity;
import com.drifting.bureau.mvp.presenter.MyCouponPresenter;
import com.drifting.bureau.mvp.ui.adapter.CouponAvailableAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.jess.arms.di.component.AppComponent;
import com.rb.core.xrecycleview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 可使用的优惠券
 * @Author : WeiJiaQI
 * @Time : 2022/10/14 15:20
 */
public class CouponAvailableActivity extends BaseManagerActivity<MyCouponPresenter> implements MyCouponContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    private int mPage = 1;
    private int limit = 1000;
    private CouponAvailableAdapter couponAvailableAdapter;

    private static final String EXTRA_COUPONSENTITY = "coupons_entity";

    private static final String EXTRA_TOTAL = "extra_total";

    private static final String EXTRA_SCENE = "extra_scene";

    private CouponsMineEntity.ListBean listBean;

    private String total, scene;

    public static void start(Context context, CouponsMineEntity.ListBean listBean, String total, String scene, boolean closePage) {
        Intent intent = new Intent(context, CouponAvailableActivity.class);
        intent.putExtra(EXTRA_COUPONSENTITY, listBean);
        intent.putExtra(EXTRA_TOTAL, total);
        intent.putExtra(EXTRA_SCENE, scene);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();

    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCouponAvailableComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_refresh_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("可使用卡券");
        listBean = (CouponsMineEntity.ListBean) getSerializable(EXTRA_COUPONSENTITY);
        total = getString(EXTRA_TOTAL);
        scene = getString(EXTRA_SCENE);
        initListener();
    }

    public void initListener() {
        mRcyPublic.setLayoutManager(new LinearLayoutManager(this));
        mRcyPublic.setPullRefreshEnabled(false);
        couponAvailableAdapter = new CouponAvailableAdapter(new ArrayList<>(), entity -> {
            CouponAvailableEvent couponAvailableEvent = new CouponAvailableEvent();
            couponAvailableEvent.setListBean(entity);
            EventBus.getDefault().post(couponAvailableEvent);
            finish();
        });
        mRcyPublic.setAdapter(couponAvailableAdapter);
        getData(mPage, true);
    }

    public void getData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.listForScene(mPage, limit, 0, scene, loadType);
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

    @Override
    public void onloadStart() {
        if (couponAvailableAdapter.getInfos() == null || couponAvailableAdapter.getInfos().size() == 0) {
            ViewUtil.create().setAnimation(this, mFlState);
        }
    }

    @Override
    public void onCouponsMineSuccess(CouponsMineEntity entity, boolean isNotData) {
        List<CouponsMineEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCoupon_type() == 3) {  //代金券
                    if (Double.parseDouble(total) < Double.parseDouble(list.get(i).getDeduct_money())) {
                        list.get(i).setType(1);
                    }
                } else if (list.get(i).getCoupon_type() == 4) {  //满减
                    if (Double.parseDouble(total) < Double.parseDouble(list.get(i).getReach_money())) {
                        list.get(i).setType(1);
                    }
                }
            }
            couponAvailableAdapter.setData(list, listBean);
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
        if (couponAvailableAdapter.getInfos() == null || couponAvailableAdapter.getInfos().size() == 0) {
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

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

}
