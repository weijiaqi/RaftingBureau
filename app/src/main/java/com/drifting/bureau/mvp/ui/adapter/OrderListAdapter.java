package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;
import com.drifting.bureau.mvp.ui.holder.OrderListHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class OrderListAdapter extends BaseRecyclerAdapter<OrderRecordEntity.ListBean.OrderSubBean> {


    public OrderListAdapter(List<OrderRecordEntity.ListBean.OrderSubBean> mDatas) {
        super(mDatas);

    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        OrderListHolder orderListHolder = (OrderListHolder) holder;
        orderListHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_order_list;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new OrderListHolder(view);
    }
}
