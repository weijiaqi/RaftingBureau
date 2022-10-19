package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.RaftingBureaufriendEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.ui.holder.RaftingBureaufriendHolder;
import com.drifting.bureau.mvp.ui.holder.RaftingOrderHolder;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

public class RaftingOrderAdapter extends DefaultAdapter<SkuListEntity.GoodsSkuBean> {

    public RaftingOrderAdapter(List infos) {
        super(infos);
    }

    @NonNull
    @Override
    public BaseHolder<SkuListEntity.GoodsSkuBean> getHolder(@NonNull View v, int viewType) {
        return new RaftingOrderHolder(v,this);
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_rafting_order;
    }

    public void setData(List<SkuListEntity.GoodsSkuBean> data) {
        if (data!=null){
            this.mInfos = data;
            notifyDataSetChanged();
        }

    }

    public void remove(int postion) {
        mInfos.remove(postion);
        notifyDataSetChanged();
    }
}
