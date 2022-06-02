package com.drifting.bureau.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.MakingRecordEntity;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class MakingRecordHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_people)
    TextView mTvPeople;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_status)
    TextView mTvStatus;

    public MakingRecordHolder(View itemView) {
        super(itemView);
    }

    public void setData(@NonNull List<MakingRecordEntity.ListBean> listBeanList, int position) {
        TextUtil.setText(mTvTime, "接收时间：" + StringUtil.stampToDate(listBeanList.get(position).getMade_at()));
        TextUtil.setText(mTvPeople, "漂流人：" + listBeanList.get(position).getBuyer_name());
        TextUtil.setText(mTvPrice, listBeanList.get(position).getMoney());
        if (listBeanList.get(position).getMade() == 1) {
            TextUtil.setText(mTvStatus, "已制作");
        } else {
            if (listBeanList.get(position).getIs_throw() == 1 || listBeanList.get(position).getIs_throw() == 2) {
                TextUtil.setText(mTvStatus, "已丢回");
            } else {
                TextUtil.setText(mTvStatus, "未制作");
            }
        }
    }
}
