package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.RaftingBureaufriendEntity;
import com.drifting.bureau.mvp.ui.holder.RaftingBureaufriendHolder;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

public class RaftingBureaufriendAdapter extends DefaultAdapter<RaftingBureaufriendEntity.FriendsBean> {

    public RaftingBureaufriendAdapter(List infos) {
        super(infos);
    }


    @NonNull
    @Override
    public BaseHolder<RaftingBureaufriendEntity.FriendsBean> getHolder(@NonNull View v, int viewType) {
        return new RaftingBureaufriendHolder(v, this);
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_rafting_bureaufriend;
    }


    public void setData(List<RaftingBureaufriendEntity.FriendsBean> data) {
        if (data!=null){
            this.mInfos = data;
            notifyDataSetChanged();
        }

    }

    public void remove(int postion) {
        mInfos.remove(postion);
        notifyDataSetChanged();
    }
}
