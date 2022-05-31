package com.drifting.bureau.mvp.ui.activity.user;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerOrderRecordComponent;
import com.drifting.bureau.mvp.model.entity.DriftingTrackEntity;

import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;
import com.drifting.bureau.mvp.ui.adapter.OrderRecordAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.OrderRecordContract;
import com.drifting.bureau.mvp.presenter.OrderRecordPresenter;
import com.rb.core.xrecycleview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/28 11:27
 * @author 订单记录
 * module name is OrderRecordActivity
 */
public class OrderRecordActivity extends BaseActivity<OrderRecordPresenter> implements OrderRecordContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;

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
    public int initView(@Nullable Bundle savedInstanceState){
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
        orderRecordAdapter=new OrderRecordAdapter(new ArrayList<>());
        mRcyPublic.setAdapter(orderRecordAdapter);
        orderRecordAdapter.setData(getData());
    }

    public List<OrderRecordEntity> getData(){
        List<OrderRecordEntity> list=new ArrayList<>();
        list.add(new OrderRecordEntity("传递漂-传递杯"));
        list.add(new OrderRecordEntity("传递漂-传递杯"));
        list.add(new OrderRecordEntity("传递漂-传递杯"));
        list.add(new OrderRecordEntity("传递漂-传递杯"));
        return list;
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