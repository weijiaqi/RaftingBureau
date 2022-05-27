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
import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
import com.drifting.bureau.mvp.ui.adapter.IncomeRecordAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.di.component.DaggerIncomeRecordComponent;

import com.drifting.bureau.mvp.contract.IncomeRecordContract;
import com.drifting.bureau.mvp.presenter.IncomeRecordPresenter;
import com.rb.core.xrecycleview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/27 11:12
 *
 * @author 收支记录
 * module name is IncomeRecordActivity
 */
public class IncomeRecordActivity extends BaseActivity<IncomeRecordPresenter> implements IncomeRecordContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;

    private IncomeRecordAdapter incomeRecordAdapter;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, IncomeRecordActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerIncomeRecordComponent //如找不到该类,请编译一下项目
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
        mToolbarTitle.setText("收支记录");
        initListener();
    }

    public void initListener() {
        mRcyPublic.setLayoutManager(new LinearLayoutManager(this));
        incomeRecordAdapter = new IncomeRecordAdapter(new ArrayList<>());
        mRcyPublic.setAdapter(incomeRecordAdapter);
        incomeRecordAdapter.setData(getData());
    }


    public List<IncomeRecordEntity> getData() {
        List<IncomeRecordEntity> list = new ArrayList<>();
        list.add(new IncomeRecordEntity("15.2"));
        list.add(new IncomeRecordEntity("15.2"));
        list.add(new IncomeRecordEntity("15.2"));
        list.add(new IncomeRecordEntity("15.2"));
        list.add(new IncomeRecordEntity("15.2"));
        list.add(new IncomeRecordEntity("15.2"));
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