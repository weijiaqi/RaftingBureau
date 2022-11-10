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
import com.drifting.bureau.data.event.WinningRecordEvent;
import com.drifting.bureau.di.component.DaggerWinningRecordComponent;
import com.drifting.bureau.mvp.contract.WinningRecordContract;
import com.drifting.bureau.mvp.model.entity.OpenBoxListEntity;
import com.drifting.bureau.mvp.presenter.WinningRecordPresenter;
import com.drifting.bureau.mvp.ui.adapter.WinningRecordAdapter;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.rb.core.xrecycleview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class WinningRecordFragment extends BaseFragment<WinningRecordPresenter> implements WinningRecordContract.View,XRecyclerView.LoadingListener {
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;

    private int mPage = 1;
    private int limit = 10;
    private WinningRecordAdapter winningRecordAdapter;
    public static WinningRecordFragment newInstance() {
        WinningRecordFragment fragment = new WinningRecordFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWinningRecordComponent //如找不到该类,请编译一下项目
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
        winningRecordAdapter = new WinningRecordAdapter(new ArrayList<>());
        mRcyPublic.setAdapter(winningRecordAdapter);
        getData(mPage, true);
    }

    public void getData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.openboxlist(mPage, limit, loadType);
        }
    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    @Override
    public void onloadStart() {
        if (winningRecordAdapter.getDatas() == null || winningRecordAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(mContext, mFlState);
        }
    }

    @Override
    public void onOpenBoxSuccess(OpenBoxListEntity entity, boolean isNotData) {
        if (mPage == 1 && winningRecordAdapter.getItemCount() != 0) {
            winningRecordAdapter.clearData();
        }
        List<OpenBoxListEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            if (isNotData) {
                mPage = 2;
                winningRecordAdapter.setData(list);
            } else {
                mPage++;
                winningRecordAdapter.addData(list);
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
        if (winningRecordAdapter.getDatas() == null || winningRecordAdapter.getDatas().size() == 0) {
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
    public void onRefresh() {
        mPage = 1;
        getData(mPage, true);
    }

    @Override
    public void onLoadMore() {
        getData(mPage, false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void WinningRecordEvent(WinningRecordEvent winningRecordEvent) {
        if (winningRecordEvent != null) {
            onRefresh();
        }
    }
}
