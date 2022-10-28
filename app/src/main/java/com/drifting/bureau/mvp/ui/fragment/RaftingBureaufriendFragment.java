package com.drifting.bureau.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.FriendApplicationEvent;
import com.drifting.bureau.data.event.MessageCenterEvent;
import com.drifting.bureau.di.component.DaggerRaftingBureaufriendComponent;
import com.drifting.bureau.mvp.contract.RaftingBureaufriendContract;
import com.drifting.bureau.mvp.model.entity.RaftingBureaufriendEntity;
import com.drifting.bureau.mvp.presenter.RaftingBureaufriendPresenter;
import com.drifting.bureau.mvp.ui.activity.user.FriendApplicationActivity;
import com.drifting.bureau.mvp.ui.adapter.RaftingBureaufriendAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/18 14:31
 *
 * @author 漂流局友
 * module name is RaftingBureaufriendFragment
 */
public class RaftingBureaufriendFragment extends BaseFragment<RaftingBureaufriendPresenter> implements RaftingBureaufriendContract.View {

    @BindView(R.id.rcy_list)
    RecyclerView mRcyPublic;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    private RaftingBureaufriendAdapter raftingBureaufriendAdapter;

    public static RaftingBureaufriendFragment newInstance() {
        RaftingBureaufriendFragment fragment = new RaftingBureaufriendFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRaftingBureaufriendComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rafting_bureaufriend, container, false);
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
        raftingBureaufriendAdapter = new RaftingBureaufriendAdapter(new ArrayList<>());
        mRcyPublic.setAdapter(raftingBureaufriendAdapter);
        getData(1);
    }


    public void getData(int type) {
        if (mPresenter != null) {
            mPresenter.friendmine(type);
        }
    }

    @OnClick({R.id.rl_top})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.rl_top:
                    FriendApplicationActivity.start(mContext, false);
                    break;
            }
        }
    }

    @Override
    public void onFriendMineSuccess(int type, RaftingBureaufriendEntity entity) {
        if (entity != null) {
            if (entity.getApply()!=null){
                mTvNum.setVisibility(entity.getApply() == 0 ? View.GONE : View.VISIBLE);
                mTvNum.setText(entity.getApply() + "");
            }
            List<RaftingBureaufriendEntity.FriendsBean> list = entity.getFriends();
            if (list.size() > 0) {
                raftingBureaufriendAdapter.setData(list);
                if (entity.getApply() == 0 && type == 2) {
                    EventBus.getDefault().post(new MessageCenterEvent());
                }
            }
        }
    }

    @Override
    public void onNetError() {

    }

    public Fragment getFragment() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void FriendApplicationEvent(FriendApplicationEvent event) {
        if (event != null) {
            getData(2);
        }
    }

    @Override
    public void onDestroyView() {
        RequestUtil.create().disDispose();
        super.onDestroyView();
    }
}