package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.HotCityEntity;
import com.drifting.bureau.mvp.ui.holder.HotListHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class HotListAdapter extends BaseRecyclerAdapter<HotCityEntity> {


    public HotListAdapter(List<HotCityEntity> infos) { super(infos);}


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        HotListHolder hotListHolder=(HotListHolder) holder;
        hotListHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_hot_list;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new HotListHolder(view);
    }
}
