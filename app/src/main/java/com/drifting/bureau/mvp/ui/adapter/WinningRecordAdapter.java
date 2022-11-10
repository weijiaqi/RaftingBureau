package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.OpenBoxListEntity;
import com.drifting.bureau.mvp.ui.holder.WinningRecordHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class WinningRecordAdapter  extends BaseRecyclerAdapter<OpenBoxListEntity.ListBean> {


    public WinningRecordAdapter(List<OpenBoxListEntity.ListBean> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        WinningRecordHolder winningRecordHolder=(WinningRecordHolder) holder;
        winningRecordHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_winning_record;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new WinningRecordHolder(view);
    }
}
