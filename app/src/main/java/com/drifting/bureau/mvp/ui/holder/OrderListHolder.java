package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class OrderListHolder extends BaseRecyclerHolder {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;

    private Context context;
    public OrderListHolder(View itemView) {
        super(itemView);
        context=itemView.getContext();
    }

    public void setData(List<OrderRecordEntity.ListBean.OrderSubBean> listBeanList, int position) {
        TextUtil.setText(mTvTitle, StringUtil.getCupNama(listBeanList.get(position).getExplore_id(),listBeanList.get(position).getSku_name(),listBeanList.get(position).getGoods_num()));
        TextUtil.setText(mTvDesc, listBeanList.get(position).getDesc());
        GlideUtil.create().loadLongImage(context,listBeanList.get(position).getSmall_image(),mIvPic);
    }
}
