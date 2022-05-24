package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.RaftingOrderEntity;
import com.drifting.bureau.mvp.ui.holder.RaftingOrderHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class RaftingOrderAdapter extends BaseRecyclerAdapter<RaftingOrderEntity> {

    public RaftingOrderAdapter(List<RaftingOrderEntity> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        RaftingOrderHolder raftingOrderHolder=(RaftingOrderHolder) holder;
        raftingOrderHolder.setData(mDatas,position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_rafting_order;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new RaftingOrderHolder(view);
    }
}
