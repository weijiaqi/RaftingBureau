package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.AoubtMeEntity;
import com.drifting.bureau.mvp.ui.holder.AboutMeHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class AboutMeAdapter  extends BaseRecyclerAdapter<AoubtMeEntity> {

    public AboutMeAdapter(List<AoubtMeEntity> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        AboutMeHolder aboutMeHolder=(AboutMeHolder) holder;
        aboutMeHolder.setData(mDatas,position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_about_me;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new AboutMeHolder(view);
    }


}
