package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.DriftingTrackEntity;
import com.drifting.bureau.mvp.ui.holder.DriftingTrackHolder;
import com.drifting.bureau.mvp.ui.holder.MakingRecordHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class DriftingTrackAdapter  extends BaseRecyclerAdapter<DriftingTrackEntity> {

    public DriftingTrackAdapter(List<DriftingTrackEntity> infos) {
        super(infos);
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        DriftingTrackHolder driftingTrackHolder=(DriftingTrackHolder) holder;
        driftingTrackHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_drifting_track;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new DriftingTrackHolder(view);
    }
}
