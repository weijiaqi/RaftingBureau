package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.BlindBoxRecordEntity;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class BlindBoxHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private Context context;

    public BlindBoxHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
    }

    public void setData(@NonNull List<BlindBoxRecordEntity.ListBean> listBeanList, int position) {
        TextUtil.setText(mTvTime, "时间：" + DateUtil.unxiToDateYMDHM(listBeanList.get(position).getCreated_at_int() + ""));
        GlideUtil.create().loadLongImage(context, listBeanList.get(position).getImage(), mIvPic);
        TextUtil.setText(mTvTitle, listBeanList.get(position).getObject_name());
    }
}
