package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.BlindBoxRecordEntity;
import com.drifting.bureau.mvp.ui.holder.BlindBoxHolder;
import com.drifting.bureau.mvp.ui.holder.OrderRecordHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class BlindBoxRecordAdapter extends BaseRecyclerAdapter<BlindBoxRecordEntity.ListBean> {

    public BlindBoxRecordAdapter(List<BlindBoxRecordEntity.ListBean> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        BlindBoxHolder blindBoxHolder=(BlindBoxHolder) holder;
        blindBoxHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_blind_box_record;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new BlindBoxHolder(view);
    }
}
