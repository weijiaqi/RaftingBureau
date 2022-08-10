package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.NebulaListEntity;
import com.drifting.bureau.mvp.ui.holder.NebulaListHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class NebulaeListAdapter extends BaseRecyclerAdapter<NebulaListEntity.ListBean> {

    private String nebulacode;

    public NebulaeListAdapter(List<NebulaListEntity.ListBean> infos, String nebulacode) {
        super(infos);
        this.nebulacode = nebulacode;
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        NebulaListHolder nebulaListHolder = (NebulaListHolder) holder;
        nebulaListHolder.setData(mDatas, position,nebulacode);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_nebula_list;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new NebulaListHolder(view);
    }

    public void update(String nebulacode) {
        this.nebulacode = nebulacode;
        notifyDataSetChanged();
    }
}
