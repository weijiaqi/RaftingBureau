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
import com.drifting.bureau.base.BaseActivity;
import com.drifting.bureau.di.component.DaggerNebulaComponent;
import com.drifting.bureau.mvp.contract.NebulaContract;
import com.drifting.bureau.mvp.model.entity.NebulaEntity;
import com.drifting.bureau.mvp.presenter.NebulaPresenter;
import com.drifting.bureau.mvp.ui.adapter.NebulaAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.jess.arms.di.component.AppComponent;
import com.rb.core.xrecycleview.LRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/07/14 12:09
 *
 * @author 星云
 * module name is NebulaActivity
 */
public class NebulaActivity extends BaseActivity<NebulaPresenter> implements NebulaContract.View, LRecyclerView.LoadingListener {

    @BindView(R.id.tv_channel)
    TextView mTvChannel;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.rcy_nebula)
    LRecyclerView mRcyNebula;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;



    private NebulaAdapter nebulaAdapter;
    private int mPage = 1;
    private int limit = 10;
    private static String EXTRA_MESSAGE_ID = "extra_message_id";
    private int message_id;

    public static void start(Context context, int message_id, boolean closePage) {
        Intent intent = new Intent(context, NebulaActivity.class);
        intent.putExtra(EXTRA_MESSAGE_ID, message_id);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNebulaComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_nebula; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);


        initListener();
    }

    public void initListener() {
        if (getIntent() != null) {
            message_id = getIntent().getExtras().getInt(EXTRA_MESSAGE_ID);
        }
        mRcyNebula.setLoadingListener(this);
        nebulaAdapter = new NebulaAdapter(new ArrayList<>());
        mRcyNebula.setLayoutManager(new LinearLayoutManager(this));
        mRcyNebula.setAdapter(nebulaAdapter);
        getData(mPage, true);
    }

    public void getData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.messagenebula(message_id, mPage, limit, loadType);
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
        if (nebulaAdapter.getDatas() == null || nebulaAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(this, mFlState);
        }
    }

    @Override
    public void onNebulaSuccess(NebulaEntity entity, boolean isNotData) {
        mTvChannel.setText(entity.getCount() + "");
        mTvTime.setText(DateUtil.getDay(entity.getDuration()) + "天");
        List<NebulaEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            if (isNotData) {
                mPage = 2;
                nebulaAdapter.setData(list);
            } else {
                mPage++;
                nebulaAdapter.addData(list);
            }
        }
    }

    @Override
    public void loadFinish(boolean loadType, boolean isNotData) {
        if (mRcyNebula == null) {
            return;
        }
        if (!loadType && isNotData) {
            mRcyNebula.loadEndLine();
        } else {
            mRcyNebula.loadMoreComplete();
        }
    }

    @Override
    public void loadState(int dataState) {
        if (nebulaAdapter.getDatas() == null || nebulaAdapter.getDatas().size() == 0) {
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

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    @Override
    public void onLoadMore() {
        getData(mPage, false);
    }
}