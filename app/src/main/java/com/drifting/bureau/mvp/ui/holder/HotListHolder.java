package com.drifting.bureau.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.HotCityEntity;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class HotListHolder extends BaseRecyclerHolder {
    @BindView(R.id.tv_name)
    TextView mTvName;

    public HotListHolder(View itemView) {
        super(itemView);
    }

    public void setData(@NonNull List<HotCityEntity> data, int position) {
        TextUtil.setText(mTvName, data.get(position).getName());
    }
}
