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
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerDeliveryDetailsComponent;
import com.drifting.bureau.mvp.model.entity.DeliveryDetailsEntity;
import com.drifting.bureau.mvp.ui.adapter.DeliveryDetailsAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.DeliveryDetailsContract;
import com.drifting.bureau.mvp.presenter.DeliveryDetailsPresenter;

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
public class DeliveryDetailsActivity extends BaseActivity<DeliveryDetailsPresenter> implements DeliveryDetailsContract.View {
     @BindView(R.id.toolbar_title)
     TextView mToolbarTitle;
    @BindView(R.id.rcy_delivery)
    RecyclerView mRcyDelivery;

    private DeliveryDetailsAdapter deliveryDetailsAdapter;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, DeliveryDetailsActivity.class);
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
        initListener();
    }

    public void initListener() {
        mRcyDelivery.setNestedScrollingEnabled(false);
        mRcyDelivery.setLayoutManager(new LinearLayoutManager(this));
        deliveryDetailsAdapter = new DeliveryDetailsAdapter();
        mRcyDelivery.setAdapter(deliveryDetailsAdapter);
        deliveryDetailsAdapter.setData(getTimeLine());
    }

    public List<DeliveryDetailsEntity> getTimeLine() {
        List<DeliveryDetailsEntity> list = new ArrayList<>();
        list.add(new DeliveryDetailsEntity(1));
        list.add(new DeliveryDetailsEntity(2));
        list.add(new DeliveryDetailsEntity(3));
        list.add(new DeliveryDetailsEntity(1));
        list.add(new DeliveryDetailsEntity(2));
        list.add(new DeliveryDetailsEntity(3));
        list.add(new DeliveryDetailsEntity(1));
        list.add(new DeliveryDetailsEntity(2));
        list.add(new DeliveryDetailsEntity(3));
        list.add(new DeliveryDetailsEntity(1));
        list.add(new DeliveryDetailsEntity(2));
        list.add(new DeliveryDetailsEntity(3));
        list.add(new DeliveryDetailsEntity(1));
        list.add(new DeliveryDetailsEntity(2));
        list.add(new DeliveryDetailsEntity(3));
        list.add(new DeliveryDetailsEntity(1));
        list.add(new DeliveryDetailsEntity(2));
        list.add(new DeliveryDetailsEntity(3));
        list.add(new DeliveryDetailsEntity(1));
        list.add(new DeliveryDetailsEntity(2));
        list.add(new DeliveryDetailsEntity(3));

        return list;
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