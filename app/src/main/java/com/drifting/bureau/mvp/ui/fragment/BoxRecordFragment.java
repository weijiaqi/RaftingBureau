package com.drifting.bureau.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerBlindBoxRecordComponent;
import com.drifting.bureau.di.component.DaggerBoxRecordComponent;
import com.drifting.bureau.mvp.contract.BlindBoxRecordContract;
import com.drifting.bureau.mvp.model.entity.BlindBoxRecordEntity;
import com.drifting.bureau.mvp.presenter.BlindBoxRecordPresenter;
import com.drifting.bureau.mvp.ui.adapter.BlindBoxRecordAdapter;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.rb.core.xrecycleview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Description: 盲盒记录
 * @Author     : WeiJiaQI
 * @Time       : 2022/10/13 14:23
 */
public class BoxRecordFragment extends BaseFragment<BlindBoxRecordPresenter> implements BlindBoxRecordContract.View,XRecyclerView.LoadingListener {
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    private int mPage = 1;
    private int limit = 10;
    private BlindBoxRecordAdapter blindBoxRecordAdapter;

    public static BoxRecordFragment newInstance() {
        BoxRecordFragment fragment = new BoxRecordFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerBoxRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mRcyPublic.setLayoutManager(new LinearLayoutManager(mContext));
        mRcyPublic.setLoadingListener(this);
        blindBoxRecordAdapter = new BlindBoxRecordAdapter(new ArrayList<>());
        mRcyPublic.setAdapter(blindBoxRecordAdapter);
        getData(mPage, true);
    }

    public void getData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.openlogs(mPage, limit, loadType);
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onloadStart() {
        if (blindBoxRecordAdapter.getDatas() == null || blindBoxRecordAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(mContext, mFlState);
        }
    }

    @Override
    public void onOpenLogsSuccess(BlindBoxRecordEntity entity, boolean isNotData) {
        if (mPage == 1 && blindBoxRecordAdapter.getItemCount() != 0) {
            blindBoxRecordAdapter.clearData();
        }
        List<BlindBoxRecordEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            if (isNotData) {
                mPage = 2;
                blindBoxRecordAdapter.setData(list);
            } else {
                mPage++;
                blindBoxRecordAdapter.addData(list);
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
        if (blindBoxRecordAdapter.getDatas() == null || blindBoxRecordAdapter.getDatas().size() == 0) {
            if (dataState == ViewUtil.NOT_DATA) {
                ViewUtil.create().setView(mContext, mFlState, ViewUtil.NOT_DATA);
            } else if (dataState == ViewUtil.NOT_SERVER) {
                ViewUtil.create().setView(mContext, mFlState, ViewUtil.NOT_SERVER);
            } else if (dataState == ViewUtil.NOT_NETWORK) {
                ViewUtil.create().setView(mContext, mFlState, ViewUtil.NOT_NETWORK);
            } else {
                ViewUtil.create().setView(mFlState);
            }
        } else {
            ViewUtil.create().setView(mFlState);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
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
}
