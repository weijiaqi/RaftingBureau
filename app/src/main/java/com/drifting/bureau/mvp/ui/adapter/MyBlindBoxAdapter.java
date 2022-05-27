package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;
import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.MyBlindBoxEntity;
import com.drifting.bureau.mvp.ui.holder.MyBlindBoxHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class MyBlindBoxAdapter extends BaseRecyclerAdapter<MyBlindBoxEntity> {

    public MyBlindBoxAdapter(List<MyBlindBoxEntity> infos) {
        super(infos);
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        MyBlindBoxHolder myBlindBoxHolder=(MyBlindBoxHolder) holder;
        myBlindBoxHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_my_blind_box;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new MyBlindBoxHolder(view);
    }
}
