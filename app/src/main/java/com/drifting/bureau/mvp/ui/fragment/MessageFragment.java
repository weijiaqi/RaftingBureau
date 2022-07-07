package com.drifting.bureau.mvp.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.drifting.bureau.R;
import com.drifting.bureau.data.event.MessageEvent;
import com.drifting.bureau.di.component.DaggerMessageComponent;
import com.drifting.bureau.mvp.model.entity.SysmessageMineEntity;
import com.drifting.bureau.mvp.model.entity.TeaShopEntity;
import com.drifting.bureau.mvp.ui.adapter.MessageAdapter;

import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;


import com.drifting.bureau.mvp.contract.MessageContract;
import com.drifting.bureau.mvp.presenter.MessagePresenter;
import com.rb.core.xrecycleview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created on 2022/06/18 13:11
 *
 * @author元宇宙消息 module name is MessageFragment
 */
public class MessageFragment extends BaseFragment<MessagePresenter> implements MessageContract.View, XRecyclerView.LoadingListener {
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    private static final String EXTRA_ID = "extra_id";
    private int type;

    private MessageAdapter messageAdapter;
    private int mPage = 1;
    private int limit = 10;

    public static MessageFragment newInstance(int id) {
        MessageFragment fragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMessageComponent //如找不到该类,请编译一下项目
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

    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initListener();
    }



    @Override
    public void setData(@Nullable Object data) {

    }


    private void initListener() {
        mRcyPublic.setLayoutManager(new LinearLayoutManager(mContext));
        mRcyPublic.setLoadingListener(this);
        messageAdapter = new MessageAdapter(new ArrayList<>());
        mRcyPublic.setAdapter(messageAdapter);
        getData(mPage, true);
    }


    public void getData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.sysmessage(mPage, limit, loadType);
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
        if (messageAdapter.getDatas() == null || messageAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(mContext, mFlState);
        }
    }

    @Override
    public void onSysmessageSuccess(SysmessageMineEntity entity, boolean isNotData) {
        if (mPage == 1 && messageAdapter.getItemCount() != 0) {
            messageAdapter.clearData();
        }
        List<SysmessageMineEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            if (isNotData) {
                mPage = 2;
                messageAdapter.setData(list);
            } else {
                mPage++;
                messageAdapter.addData(list);
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
        if (messageAdapter.getDatas() == null || messageAdapter.getDatas().size() == 0) {
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

    public Fragment getFragment() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEvent(MessageEvent event) {
        if (event != null &&messageAdapter.getDatas()!= null) {
            List<SysmessageMineEntity.ListBean> list=messageAdapter.getDatas();
            for (int i = 0; i < list.size(); i++) {
                if (event.getSys_msg_id()==list.get(i).getSys_msg_id()) {
                    list.get(i).setIs_read(1);
                    messageAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }
}