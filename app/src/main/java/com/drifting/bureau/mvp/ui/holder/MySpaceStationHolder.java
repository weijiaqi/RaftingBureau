package com.drifting.bureau.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.MySpaceStationEntity;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class MySpaceStationHolder extends BaseRecyclerHolder {
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    public MySpaceStationHolder(View itemView) {
        super(itemView);
    }

    public void setData(@NonNull List<MySpaceStationEntity> listBeanList, int position) {
        TextUtil.setText(mTvTitle, listBeanList.get(position).getTitle());
    }
}
