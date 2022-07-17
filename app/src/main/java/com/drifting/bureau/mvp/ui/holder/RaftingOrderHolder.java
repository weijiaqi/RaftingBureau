package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class RaftingOrderHolder extends BaseRecyclerHolder {

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

    public RaftingOrderHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<SkuListEntity.GoodsSkuBean> listBeanList, int position) {
        TextUtil.setText(mTvRaftingType, listBeanList.get(position).getName());
        TextUtil.setText(mTvPrice, "￥" + listBeanList.get(position).getPrice());
        TextUtil.setText(mTvDesc, listBeanList.get(position).getDesc());
        mViewBottomLine.setVisibility(position == listBeanList.size() - 1 ? View.GONE : View.VISIBLE);
        GlideUtil.create().loadNormalPic(context,  listBeanList.get(position).getSmall_image(), mIvPic);
    }
}
