package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerMakingRecordComponent;
import com.drifting.bureau.mvp.model.entity.MakingRecordEntity;
import com.drifting.bureau.mvp.ui.adapter.MakingRecordAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.MakingRecordContract;
import com.drifting.bureau.mvp.presenter.MakingRecordPresenter;
import com.rb.core.xrecycleview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created on 2022/05/27 14:39
 *
 * @author 制作记录
 * module name is MakingRecordActivity
 */
public class MakingRecordActivity extends BaseActivity<MakingRecordPresenter> implements MakingRecordContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;

    private MakingRecordAdapter makingRecordAdapter;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, MakingRecordActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMakingRecordComponent //如找不到该类,请编译一下项目
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
        mToolbarTitle.setText("制作记录");
        initListener();
    }

    public void initListener() {
        mRcyPublic.setLayoutManager(new LinearLayoutManager(this));
        makingRecordAdapter = new MakingRecordAdapter(new ArrayList<>());
        mRcyPublic.setAdapter(makingRecordAdapter);
        makingRecordAdapter.setData(getdata());
    }

    public List<MakingRecordEntity> getdata(){
        List<MakingRecordEntity>  list=new ArrayList<>();
        list.add(new MakingRecordEntity("10.5"));
        list.add(new MakingRecordEntity("10.5"));
        list.add(new MakingRecordEntity("10.5"));
        list.add(new MakingRecordEntity("10.5"));
        list.add(new MakingRecordEntity("10.5"));
        list.add(new MakingRecordEntity("10.5"));
        return  list;
    }
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}