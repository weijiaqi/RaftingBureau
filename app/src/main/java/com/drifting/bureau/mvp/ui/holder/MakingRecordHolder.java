package com.drifting.bureau.mvp.ui.holder;

import android.view.View;

import androidx.annotation.NonNull;

import com.drifting.bureau.mvp.model.entity.MakingRecordEntity;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class MakingRecordHolder  extends BaseRecyclerHolder {
    public MakingRecordHolder(View itemView) {
        super(itemView);
    }

    public void setData(@NonNull List<MakingRecordEntity> listBeanList, int position) {
        //  mViewBottomLine.setVisibility(position==listBeanList.size()-1?View.GONE:View.VISIBLE);
    }
}
