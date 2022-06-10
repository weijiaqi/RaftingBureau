package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;

import com.drifting.bureau.data.event.MyBlindBoxRefreshEvent;
import com.drifting.bureau.data.event.OrderRecordEvent;
import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;

import com.drifting.bureau.mvp.model.entity.WriteOffInfoEntity;
import com.drifting.bureau.mvp.ui.dialog.WriteOffCodeDialog;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.callback.BaseDataCallBack;
import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.view.ClockView;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.base.BaseRecyclerHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;


public class OrderRecordHolder extends BaseRecyclerHolder {
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.tv_status)
    ShapeTextView mTvStatus;

    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_write_off)
    TextView mTvWriteOff;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.ll_payment)
    LinearLayout mLlPayMent;
    @BindView(R.id.clockview)
    ClockView clockview;
    private WriteOffCodeDialog writeOffCodeDialog;
    private Context context;

    public OrderRecordHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
    }

    public void setData(@NonNull List<OrderRecordEntity.ListBean> listBeanList, int position) {

        TextUtil.setText(mTvTime, "订单时间：" + DateUtil.unxiToDateYMDHM(listBeanList.get(position).getCreated_at_int() + ""));
        TextUtil.setText(mTvTitle, listBeanList.get(position).getSku_name());
        TextUtil.setText(mTvDesc, listBeanList.get(position).getDesc());

        if (listBeanList.get(position).getPlatform_gift() == 1) { //是平台赠送
            mTvStatus.getTextColorBuilder().setTextColor(context.getColor(R.color.color_ff)).intoTextColor();
            mTvStatus.setText("平台赠送");
            mLlPayMent.setVisibility(View.GONE);
        } else {
            if (listBeanList.get(position).getStatus() == 0) {  //未支付
                mTvStatus.getTextColorBuilder().setTextGradientColors(context.getColor(R.color.color_d6), context.getColor(R.color.color_70)).intoTextColor();
                mTvStatus.setText("未付款");
                mLlPayMent.setVisibility(View.VISIBLE);
                clockview.setType(1);
                clockview.setEndTime(System.currentTimeMillis() + (listBeanList.get(position).getTimeout() * 1000));
                clockview.setClockListener(new ClockView.ClockListener() {
                    @Override
                    public void timeEnd() {
                        EventBus.getDefault().post(new OrderRecordEvent());
                    }

                    @Override
                    public void remainFiveMinutes() {

                    }
                });
                mTvWriteOff.setText("立即付款");
                mTvWriteOff.setTextColor(context.getColor(R.color.white));
            } else if (listBeanList.get(position).getStatus() == 1) {  //已支付
                mTvStatus.getTextColorBuilder().setTextGradientColors(context.getColor(R.color.color_6c), context.getColor(R.color.color_6d)).intoTextColor();
                mTvStatus.setText("已付款");
                mLlPayMent.setVisibility(View.GONE);
            }
        }

        if (listBeanList.get(position).getWrite_off() == 1) {
            mTvWriteOff.setText("已核销");
            mTvWriteOff.setClickable(false);
            mTvWriteOff.setTextColor(context.getColor(R.color.color_99));
        } else {
            if (listBeanList.get(position).getStatus() == 0) {  //未支付
                mTvWriteOff.setText("立即付款");
            } else {
                mTvWriteOff.setText("立即核销");
            }
            mTvWriteOff.setClickable(true);
            mTvWriteOff.setTextColor(context.getColor(R.color.white));
        }
        TextUtil.setText(mTvPrice, "￥" + listBeanList.get(position).getMoney());
        mTvWriteOff.setOnClickListener(v -> {
            if (listBeanList.get(position).getStatus() == 0) {  //未支付
                ToastUtil.showToast("立即付款");
            } else {
                RequestUtil.create().writeOffInfo(listBeanList.get(position).getOrder_sub_id(), entity -> {
                    if (entity != null && entity.getCode() == 200) {
                        writeOffCodeDialog = new WriteOffCodeDialog(context,entity.getData().getToken());
                        writeOffCodeDialog.show();
                    }
                });
            }
        });
    }
}
