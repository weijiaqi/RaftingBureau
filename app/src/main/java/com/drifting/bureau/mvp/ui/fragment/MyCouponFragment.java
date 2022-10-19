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
import com.drifting.bureau.di.component.DaggerMyCouponComponent;
import com.drifting.bureau.mvp.contract.MyCouponContract;
import com.drifting.bureau.mvp.model.entity.CouponsMineEntity;
import com.drifting.bureau.mvp.model.entity.OpenBoxListEntity;
import com.drifting.bureau.mvp.presenter.MyCouponPresenter;
import com.drifting.bureau.mvp.ui.adapter.MyCouponRecordAdapter;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.rb.core.xrecycleview.XRecyclerView;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Description:  我的卡券
 * @Author     : WeiJiaQI
 * @Time       : 2022/10/14 11:15
 */
public class MyCouponFragment extends BaseFragment<MyCouponPresenter> implements MyCouponContract.View, XRecyclerView.LoadingListener{
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;

    private static final String EXTRA_ID = "extra_id";
    private int type;

    private int mPage = 1;
    private int limit = 10;
    private MyCouponRecordAdapter myCouponRecordAdapter;

    public static MyCouponFragment newInstance(int id) {
        MyCouponFragment fragment = new MyCouponFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMyCouponComponent //如找不到该类,请编译一下项目
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        type = args.getInt(EXTRA_ID);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mRcyPublic.setLayoutManager(new LinearLayoutManager(mContext));
        mRcyPublic.setLoadingListener(this);
        myCouponRecordAdapter = new MyCouponRecordAdapter(new ArrayList<>(),type);
        mRcyPublic.setAdapter(myCouponRecordAdapter);
        getData(mPage,type, true);
    }

    public void getData(int mPage,int type, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.openboxlist(mPage, limit,type,"", loadType);
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
    public void onRefresh() {
        mPage = 1;
        getData(mPage, type,true);
    }

    @Override
    public void onLoadMore() {
        getData(mPage,  type,false);
    }

    @Override
    public void onloadStart() {
        if (myCouponRecordAdapter.getDatas() == null || myCouponRecordAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(mContext, mFlState);
        }
    }

    @Override
    public void onCouponsMineSuccess(CouponsMineEntity entity, boolean isNotData) {
        if (mPage == 1 && myCouponRecordAdapter.getItemCount() != 0) {
            myCouponRecordAdapter.clearData();
        }
        List<CouponsMineEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            if (isNotData) {
                mPage = 2;
                myCouponRecordAdapter.setData(list);
            } else {
                mPage++;
                myCouponRecordAdapter.addData(list);
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
        if (myCouponRecordAdapter.getDatas() == null || myCouponRecordAdapter.getDatas().size() == 0) {
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
}
