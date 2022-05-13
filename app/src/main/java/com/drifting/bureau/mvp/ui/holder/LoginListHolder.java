package com.drifting.bureau.mvp.ui.holder;


import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.drifting.bureau.R;
import com.drifting.bureau.data.entity.LoginLocallyEntity;
import com.drifting.bureau.view.SDAdaptiveTextView;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class LoginListHolder extends BaseRecyclerHolder {
    @BindView(R.id.tv_content)
    TextView mTvContent;
    private Context context;
    public LoginListHolder(View itemView) {
        super(itemView);
        context=itemView.getContext();
    }

    public void setData(List<LoginLocallyEntity> mDatas, int position) {
        mTvContent.setTextColor(mDatas.get(position).getType()==1?context.getColor(R.color.color_01):context.getColor(R.color.color_fe));
        mTvContent.setText(mDatas.get(position).getContent());
    }
}
