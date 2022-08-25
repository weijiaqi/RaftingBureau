package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.MakingRecordEntity;
import com.drifting.bureau.mvp.ui.holder.MakingRecordHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class MakingRecordAdapter extends BaseRecyclerAdapter<MakingRecordEntity.ListBean> {

    private int type;
    private int ITEM_MAKEING = 1;
    private int ITEM_AR_MAKEING = 2;


    public MakingRecordAdapter(List<MakingRecordEntity.ListBean> infos,int type) {
        super(infos);
        this.type=type;
    }



    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        MakingRecordHolder makingRecordHolder=(MakingRecordHolder) holder;
        makingRecordHolder.setData(mDatas, position);
    }

    @Override
    public int getItemViewType(int position) {
        if (type == 1) {
            return ITEM_MAKEING;
        }else {
            return ITEM_AR_MAKEING;
        }
    }

    @Override
    protected int getLayoutId(int viewType) {

        if (viewType == ITEM_MAKEING) {
            return R.layout.item_making_record;
        } else{
            return R.layout.item_ar_making_record;
        }
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new MakingRecordHolder(view);
    }
}
