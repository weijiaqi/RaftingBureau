package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.MakingRecordEntity;
import com.drifting.bureau.mvp.ui.holder.MakingRecordHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class MakingRecordAdapter extends BaseRecyclerAdapter<MakingRecordEntity> {

    public MakingRecordAdapter(List<MakingRecordEntity> infos) {
        super(infos);
    }



    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        MakingRecordHolder makingRecordHolder=(MakingRecordHolder) holder;
        makingRecordHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_making_record;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new MakingRecordHolder(view);
    }
}
