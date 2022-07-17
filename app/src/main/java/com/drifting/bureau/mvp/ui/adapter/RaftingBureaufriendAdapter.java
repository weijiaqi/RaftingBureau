package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.RaftingBureaufriendEntity;
import com.drifting.bureau.mvp.ui.holder.RaftingBureaufriendHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class RaftingBureaufriendAdapter extends BaseRecyclerAdapter<RaftingBureaufriendEntity.FriendsBean> {

    public RaftingBureaufriendAdapter(List<RaftingBureaufriendEntity.FriendsBean> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        RaftingBureaufriendHolder raftingBureaufriendHolder=(RaftingBureaufriendHolder) holder;
        raftingBureaufriendHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_rafting_bureaufriend;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new RaftingBureaufriendHolder(view,this);
    }

    public void remove(int postion) {
        mDatas.remove(postion);
        notifyDataSetChanged();
    }
}
