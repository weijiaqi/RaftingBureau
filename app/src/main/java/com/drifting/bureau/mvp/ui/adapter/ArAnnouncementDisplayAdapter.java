package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.AnnouncementEntity;
import com.drifting.bureau.mvp.ui.holder.ArAnnouncementHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class ArAnnouncementDisplayAdapter extends BaseRecyclerAdapter<AnnouncementEntity> {

    public ArAnnouncementDisplayAdapter(List<AnnouncementEntity> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        ArAnnouncementHolder arAnnouncementHolder=(ArAnnouncementHolder) holder;
        arAnnouncementHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_ar_announcement;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new ArAnnouncementHolder(view);
    }

}
