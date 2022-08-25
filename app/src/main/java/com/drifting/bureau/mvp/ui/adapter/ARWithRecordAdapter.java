package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
import com.drifting.bureau.mvp.ui.holder.IncomeRecordHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class ARWithRecordAdapter extends BaseRecyclerAdapter<IncomeRecordEntity.ListBean> {

    public ARWithRecordAdapter(List<IncomeRecordEntity.ListBean> infos) {
        super(infos);
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        IncomeRecordHolder incomeRecordHolder=(IncomeRecordHolder) holder;
        incomeRecordHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_ar_with_record;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new IncomeRecordHolder(view);
    }
}
