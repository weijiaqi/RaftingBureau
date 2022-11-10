package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.ui.adapter.RaftingOrderAdapter;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseHolder;

import butterknife.BindView;

public class RaftingOrderHolder  extends BaseHolder<SkuListEntity.GoodsSkuBean> {

    @BindView(R.id.tv_rafting_type)
    TextView mTvRaftingType;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.view_bottm_line)
    View mViewBottomLine;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    private Context context;

    private RaftingOrderAdapter adapter;
    public RaftingOrderHolder(View itemView, RaftingOrderAdapter adapter) {
        super(itemView);
        context = itemView.getContext();
        this.adapter = adapter;
    }

    public void setData(@NonNull SkuListEntity.GoodsSkuBean goodsSkuBean, int position) {
        TextUtil.setText(mTvRaftingType, goodsSkuBean.getName());
        TextUtil.setText(mTvPrice, "￥" + goodsSkuBean.getPrice());
        TextUtil.setText(mTvDesc, goodsSkuBean.getDesc());
        mViewBottomLine.setVisibility(position == adapter.getItemCount() - 1 ? View.GONE : View.VISIBLE);
        GlideUtil.create().loadNormalPic(context, goodsSkuBean.getSmall_image(), mIvPic);
    }
}
