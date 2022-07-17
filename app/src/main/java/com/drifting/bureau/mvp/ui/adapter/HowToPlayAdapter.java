package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.SpaceAboutEntity;
import com.drifting.bureau.mvp.ui.holder.HowToPlayHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class HowToPlayAdapter extends BaseRecyclerAdapter<SpaceAboutEntity> {

    public HowToPlayAdapter(List<SpaceAboutEntity> infos) { super(infos);}



    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        HowToPlayHolder howToPlayHolder=(HowToPlayHolder) holder;
        howToPlayHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_how_to_play;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new HowToPlayHolder(view);
    }
}
