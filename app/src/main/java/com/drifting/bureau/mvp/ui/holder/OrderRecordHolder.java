package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.OrderRecordEvent;
import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;
import com.drifting.bureau.mvp.ui.activity.pay.PaymentInfoActivity;
import com.drifting.bureau.mvp.ui.adapter.OrderListAdapter;
import com.drifting.bureau.mvp.ui.dialog.WriteOffCodeDialog;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.view.ClockView;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseRecyclerHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class OrderRecordHolder extends BaseRecyclerHolder {
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.tv_status)
    ShapeTextView mTvStatus;
    @BindView(R.id.order_list)
    RecyclerView mRcyOrderList;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_write_off)
    TextView mTvWriteOff;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.ll_payment)
    LinearLayout mLlPayMent;
    @BindView(R.id.clockview)
    ClockView clockview;
    @BindView(R.id.tv_time_remaining)
    TextView mTvTimeRemaining;
    private WriteOffCodeDialog writeOffCodeDialog;
    private Context context;
    private OrderListAdapter orderListAdapter;
    private long Remaining_time;

    public OrderRecordHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        orderListAdapter = new OrderListAdapter(new ArrayList<>());
        mRcyOrderList.setLayoutManager(new LinearLayoutManager(context));
        mRcyOrderList.setAdapter(orderListAdapter);
    }

    public void setData(@NonNull List<OrderRecordEntity.ListBean> listBeanList, int position) {
        TextUtil.setText(mTvTime, "???????????????" + DateUtil.unxiToDateYMDHM(listBeanList.get(position).getCreated_at_int() + ""));
        orderListAdapter.setData(listBeanList.get(position).getOrder_sub());

        if (listBeanList.get(position).getExplore_id() == 0) {  //0?????????
            if (listBeanList.get(position).getStatus() == 0 && listBeanList.get(position).getTimeout() != 0) {  //?????????
                mTvWriteOff.setVisibility(View.VISIBLE);
                mTvWriteOff.setText("????????????");
            } else {
                mTvWriteOff.setVisibility(View.GONE);
            }
        } else {
            mTvWriteOff.setVisibility(View.VISIBLE);
            if (listBeanList.get(position).getWrite_off() == 1) {
                mTvWriteOff.setText("?????????");
                mTvWriteOff.setClickable(false);
                mTvWriteOff.setTextColor(context.getColor(R.color.color_99));
            } else {
                if (listBeanList.get(position).getStatus() == 0) {  //?????????
                    mTvWriteOff.setText("????????????");
                } else {
                    mTvWriteOff.setText("????????????");
                }
                mTvWriteOff.setClickable(true);
                mTvWriteOff.setTextColor(context.getColor(R.color.white));
            }
        }

        if (listBeanList.get(position).getPlatform_gift() == 1) { //???????????????
            mTvStatus.getTextColorBuilder().setTextColor(context.getColor(R.color.color_ff)).intoTextColor();
            mTvStatus.setText("????????????");
            mLlPayMent.setVisibility(View.GONE);
            mTvWriteOff.setVisibility(View.GONE);
        } else {
            if (listBeanList.get(position).getStatus() == 0) {  //?????????
                mTvStatus.getTextColorBuilder().setTextGradientColors(context.getColor(R.color.color_d6), context.getColor(R.color.color_70)).intoTextColor();
                mTvStatus.setText("?????????");
                mLlPayMent.setVisibility(View.VISIBLE);
                clockview.setType(1);
                if (listBeanList.get(position).getTimeout() == 0) {
                    mTvWriteOff.setVisibility(View.GONE);
                    clockview.setVisibility(View.GONE);
                    mTvTimeRemaining.setText("???????????????");
                    mTvTimeRemaining.setTextColor(context.getColor(R.color.color_99));
                } else {
                    clockview.setVisibility(View.VISIBLE);
                    mTvTimeRemaining.setText("??????????????????");
                    mTvTimeRemaining.setTextColor(context.getColor(R.color.color_cc));
                    clockview.setEndTime(System.currentTimeMillis() + (listBeanList.get(position).getTimeout() * 1000));
                    clockview.setClockListener(new ClockView.ClockListener() {
                        @Override
                        public void timeEnd() {
                            EventBus.getDefault().post(new OrderRecordEvent());
                        }

                        @Override
                        public void timeRemaining(long time) {
                            Remaining_time = time;
                        }

                        @Override
                        public void remainFiveMinutes() {

                        }
                    });
                    mTvWriteOff.setVisibility(View.VISIBLE);
                    mTvWriteOff.setText("????????????");
                    mTvWriteOff.setTextColor(context.getColor(R.color.white));
                }

            } else if (listBeanList.get(position).getStatus() == 1) {  //?????????
                mTvStatus.getTextColorBuilder().setTextGradientColors(context.getColor(R.color.color_6c), context.getColor(R.color.color_6d)).intoTextColor();
                mTvStatus.setText("?????????");
                mLlPayMent.setVisibility(View.GONE);
                mTvWriteOff.setVisibility(View.GONE);
            }
        }

        TextUtil.setText(mTvPrice, "???" + listBeanList.get(position).getOrder_money());
        mTvWriteOff.setOnClickListener(v -> {
            if (listBeanList.get(position).getStatus() == 0) {  //?????????
                PaymentInfoActivity.start(context, 4, listBeanList.get(position).getSn(), listBeanList.get(position).getOrder_money(), Remaining_time, listBeanList.get(position).getWake_up_pay(),listBeanList.get(position).getCoupon_name(),"",listBeanList.get(position).getCoupon_money(),listBeanList.get(position).getUse_scene(),false);
            } else {
                if (listBeanList.get(position).getWrite_off() != 1) {
                    RequestUtil.create().writeOffInfo(listBeanList.get(position).getOrder_id(), entity -> {
                        if (entity != null && entity.getCode() == 200) {
                            writeOffCodeDialog = new WriteOffCodeDialog(context, entity.getData().getToken());
                            writeOffCodeDialog.show();
                        }
                    });
                }
            }
        });
    }
}
