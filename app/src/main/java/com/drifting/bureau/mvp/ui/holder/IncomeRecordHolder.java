package com.drifting.bureau.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
import com.drifting.bureau.mvp.model.entity.MyBlindBoxEntity;
import com.drifting.bureau.util.ColorUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class IncomeRecordHolder extends BaseRecyclerHolder {
    @BindView(R.id.tv_money)
    TextView mTvMoney;

    public IncomeRecordHolder(View itemView) {
        super(itemView);
    }

    public void setData(@NonNull List<IncomeRecordEntity> listBeanList, int position) {
        TextUtil.setText(mTvMoney, "+" + listBeanList.get(position).getMoney());
    }
}
