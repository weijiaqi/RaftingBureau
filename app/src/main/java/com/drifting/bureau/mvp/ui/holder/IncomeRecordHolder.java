package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
import com.drifting.bureau.mvp.model.entity.MyBlindBoxEntity;
import com.drifting.bureau.util.ColorUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class IncomeRecordHolder extends BaseRecyclerHolder {
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    private Context context;
    public IncomeRecordHolder(View itemView) {
        super(itemView);
        context=itemView.getContext();
    }

    public void setData(@NonNull List<IncomeRecordEntity.ListBean> listBeanList, int position) {
        TextUtil.setText(mTvMoney, "+" + listBeanList.get(position).getMoney());
        TextUtil.setText(mTvTime, "创建时间：" + StringUtil.stampToDate(listBeanList.get(position).getCreated_at_int() ));
        if (listBeanList.get(position).getStatus()==1){
            mTvStatus.setText("已完成");
            mTvStatus.setTextColor(context.getColor(R.color.color_6d) );
        }else {
            mTvStatus.setText("审核中");
            mTvStatus.setTextColor(context.getColor(R.color.color_f8) );
        }
    }
}
