package com.drifting.bureau.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.AnswerEntity;
import com.drifting.bureau.mvp.model.entity.HotCityEntity;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class AnswerHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    public AnswerHolder(View itemView) {
        super(itemView);
    }

    public void setData(@NonNull List<AnswerEntity> data, int position) {
        TextUtil.setText(mTvTitle, data.get(position).getTitle());
    }
}
