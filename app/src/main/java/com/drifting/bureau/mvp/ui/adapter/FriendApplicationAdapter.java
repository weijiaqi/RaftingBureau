package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.FriendApplicationEntity;
import com.drifting.bureau.mvp.ui.holder.FriendApplicationHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class FriendApplicationAdapter   extends BaseRecyclerAdapter<FriendApplicationEntity.ListBean> {

    public FriendApplicationAdapter(List<FriendApplicationEntity.ListBean> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        FriendApplicationHolder friendApplicationHolder=(FriendApplicationHolder) holder;
        friendApplicationHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_friend_application;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new FriendApplicationHolder(view);
    }
}
