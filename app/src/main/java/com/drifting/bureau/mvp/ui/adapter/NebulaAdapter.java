package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.NebulaEntity;
import com.drifting.bureau.mvp.ui.holder.NebulaHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class NebulaAdapter extends BaseRecyclerAdapter<NebulaEntity.ListBean> {

    public NebulaAdapter(List<NebulaEntity.ListBean> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        NebulaHolder nebulaHolder=(NebulaHolder) holder;
        nebulaHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_nebula;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new NebulaHolder(view);
    }


}
