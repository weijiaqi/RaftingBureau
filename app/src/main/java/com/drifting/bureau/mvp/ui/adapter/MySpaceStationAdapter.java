package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.MyBlindBoxEntity;
import com.drifting.bureau.mvp.model.entity.MySpaceStationEntity;
import com.drifting.bureau.mvp.ui.holder.MySpaceStationHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class MySpaceStationAdapter extends BaseRecyclerAdapter<MySpaceStationEntity.OwnRightsBean> {

    public MySpaceStationAdapter(List<MySpaceStationEntity.OwnRightsBean> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        MySpaceStationHolder mySpaceStationHolder=(MySpaceStationHolder) holder;
        mySpaceStationHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_space_station;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new MySpaceStationHolder(view);
    }
}
