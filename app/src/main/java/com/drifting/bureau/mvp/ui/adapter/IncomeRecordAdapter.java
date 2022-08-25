package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
import com.drifting.bureau.mvp.ui.holder.IncomeRecordHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class IncomeRecordAdapter extends BaseRecyclerAdapter<IncomeRecordEntity.ListBean> {

    private int type;
    private int ITEM_INCOME = 1;
    private int ITEM_AR_INCOME = 2;

    public IncomeRecordAdapter(List<IncomeRecordEntity.ListBean> infos, int type) {
        super(infos);
        this.type = type;
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        IncomeRecordHolder incomeRecordHolder = (IncomeRecordHolder) holder;
        incomeRecordHolder.setData(mDatas, position);
    }

    @Override
    public int getItemViewType(int position) {
        if (type == 1) {
            return ITEM_INCOME;
        } else {
            return ITEM_AR_INCOME;
        }
    }

    @Override
    protected int getLayoutId(int viewType) {
        if (viewType == ITEM_INCOME) {
            return R.layout.item_income_record;
        } else{
            return R.layout.item_ar_income_record;
        }
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new IncomeRecordHolder(view);
    }
}
