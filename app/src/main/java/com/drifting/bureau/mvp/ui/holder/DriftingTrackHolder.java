package com.drifting.bureau.mvp.ui.holder;

import android.view.View;

import androidx.annotation.NonNull;

import com.drifting.bureau.mvp.model.entity.DriftingTrackEntity;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class DriftingTrackHolder extends BaseRecyclerHolder {
    public DriftingTrackHolder(View itemView) {
        super(itemView);
    }
    public void setData(@NonNull List<DriftingTrackEntity> listBeanList, int position) {
        //  mViewBottomLine.setVisibility(position==listBeanList.size()-1?View.GONE:View.VISIBLE);
    }
}
