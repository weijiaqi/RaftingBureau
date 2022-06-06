package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;

import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;

import com.drifting.bureau.mvp.ui.dialog.WriteOffCodeDialog;
import com.drifting.bureau.util.TextUtil;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;


public class OrderRecordHolder extends BaseRecyclerHolder {
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.tv_status)
    ShapeTextView mTvStatus;
    @BindView(R.id.tv_payment)
    TextView mTvPayment;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_write_off)
    TextView mTvWriteOff;
    private WriteOffCodeDialog writeOffCodeDialog;
    private Context context;

    public OrderRecordHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
    }

    public void setData(@NonNull List<OrderRecordEntity> listBeanList, int position) {
        if (listBeanList.get(position).getType() == 1) {
            mTvStatus.getTextColorBuilder().setTextColor(context.getColor(R.color.color_ff)).intoTextColor();
            mTvStatus.setText("平台赠送");
            mTvPayment.setVisibility(View.GONE);
            mTvWriteOff.setText("立即核销");
            mTvWriteOff.setTextColor(context.getColor(R.color.white));
        } else if (listBeanList.get(position).getType() == 2) {
            mTvStatus.getTextColorBuilder().setTextGradientColors(context.getColor(R.color.color_d6), context.getColor(R.color.color_70)).intoTextColor();
            mTvStatus.setText("未付款");
            mTvPayment.setVisibility(View.VISIBLE);
            mTvWriteOff.setText("立即付款");
            mTvWriteOff.setTextColor(context.getColor(R.color.white));
        } else {
            mTvStatus.getTextColorBuilder().setTextGradientColors(context.getColor(R.color.color_6c), context.getColor(R.color.color_6d)).intoTextColor();
            mTvStatus.setText("已付款");
            mTvPayment.setVisibility(View.GONE);
            mTvWriteOff.setText("已核销");
            mTvWriteOff.setTextColor(context.getColor(R.color.color_99));
        }

        TextUtil.setText(mTvPrice, "￥" + listBeanList.get(position).getPricre());
        mLlContent.setOnClickListener(v -> {
            writeOffCodeDialog = new WriteOffCodeDialog(context);
            writeOffCodeDialog.show();
        });
    }
}
