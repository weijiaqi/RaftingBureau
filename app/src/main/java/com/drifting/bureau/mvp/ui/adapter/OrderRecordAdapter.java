package com.drifting.bureau.mvp.ui.adapter;


import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.BarrageEntity;
import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;
import com.drifting.bureau.mvp.ui.holder.OrderRecordHolder;
import com.drifting.bureau.mvp.ui.holder.RaftingDetailsHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class OrderRecordAdapter  extends BaseRecyclerAdapter<OrderRecordEntity.ListBean> {

    public OrderRecordAdapter(List<OrderRecordEntity.ListBean> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        OrderRecordHolder orderRecordHolder=(OrderRecordHolder) holder;
        orderRecordHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_order_record;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new OrderRecordHolder(view);
    }
}
