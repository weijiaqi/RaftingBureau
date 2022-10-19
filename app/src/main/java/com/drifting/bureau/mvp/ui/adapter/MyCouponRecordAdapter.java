package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.CouponsMineEntity;
import com.drifting.bureau.mvp.model.entity.OpenBoxListEntity;
import com.drifting.bureau.mvp.ui.holder.MyCouponRecordHolder;
import com.drifting.bureau.mvp.ui.holder.WinningRecordHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class MyCouponRecordAdapter extends BaseRecyclerAdapter<CouponsMineEntity.ListBean> {

    private int type;
    public MyCouponRecordAdapter(List<CouponsMineEntity.ListBean> infos,int type) {
        super(infos);
        this.type=type;
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        MyCouponRecordHolder myCouponRecordHolder=(MyCouponRecordHolder) holder;
        myCouponRecordHolder.setData(mDatas, position,type);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_coupon_record;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new MyCouponRecordHolder(view);
    }
}
