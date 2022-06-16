package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.PrizeEntity;

import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class PrizeHolder extends BaseRecyclerHolder {

    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_ratio)
    TextView mTvRatio;
    private Context context;

    public PrizeHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<PrizeEntity> listBeanList, int position) {
        GlideUtil.create().loadNormalPic(context, listBeanList.get(position).getImage_url(), mIvPic);
        TextUtil.setText(mTvRatio,listBeanList.get(position).getGet_ratio());
    }
}
