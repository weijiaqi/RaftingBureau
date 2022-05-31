package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.TeaShopEntity;
import com.drifting.bureau.mvp.ui.holder.OrderRecordHolder;
import com.drifting.bureau.mvp.ui.holder.TeaShopHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class TeaShopAdapter  extends BaseRecyclerAdapter<TeaShopEntity> {

    public TeaShopAdapter(List<TeaShopEntity> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        TeaShopHolder teaShopHolder=(TeaShopHolder) holder;
        teaShopHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_tea_shop;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new TeaShopHolder(view);
    }
}
