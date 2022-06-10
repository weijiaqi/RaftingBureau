package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.BarrageEntity;
import com.drifting.bureau.mvp.model.entity.MysteryboxEntity;
import com.drifting.bureau.mvp.ui.holder.RaftingDetailsHolder;
import com.drifting.bureau.mvp.ui.holder.SpaceBarrageHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class SpaceBarrageAdapter  extends BaseRecyclerAdapter<MysteryboxEntity.ListBean> {

    public SpaceBarrageAdapter(List<MysteryboxEntity.ListBean> infos) {
        super(infos);
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        SpaceBarrageHolder spaceBarrageHolder=(SpaceBarrageHolder) holder;
        spaceBarrageHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_space_barrage;
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new SpaceBarrageHolder(view);
    }
}
