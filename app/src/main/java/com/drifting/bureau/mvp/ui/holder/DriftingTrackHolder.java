package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.DriftingTrackEntity;
import com.drifting.bureau.mvp.ui.activity.index.RaftingDetailsActivity;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.VerifyUtil;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class DriftingTrackHolder extends BaseRecyclerHolder {
     @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.tv_transfer_style)
    TextView mTvTransferStyle;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_originator)
    TextView mTvOriginator;
    @BindView(R.id.tv_to_name)
    TextView mTvToName;
    @BindView(R.id.tv_state)
    ShapeTextView mTvState;
    @BindView(R.id.tv_transmit_num)
    TextView mTvTransmitNum;
    @BindView(R.id.tv_transmit_state)
    TextView mTvTransmitState;
    private Context context;

    public DriftingTrackHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<DriftingTrackEntity.ListBean> listBeanList, int position) {

        TextUtil.setText(mTvTime, DateUtil.unxiToDateYMDHM(listBeanList.get(position).getCreated_at_int()));
        TextUtil.setText(mTvOriginator, listBeanList.get(position).getUser_name());
        if (listBeanList.get(position).getCreated_by_me() == 0) {
            mTvState.getShapeDrawableBuilder().setSolidGradientColors(context.getColor(R.color.color_d6), context.getColor(R.color.color_70)).intoBackground();
            mTvState.getTextColorBuilder().setTextColor(context.getColor(R.color.color_fe)).intoTextColor();
            mTvState.setText("我参与");
        } else {
            mTvState.getShapeDrawableBuilder().setSolidGradientColors(context.getColor(R.color.color_6c), context.getColor(R.color.color_6d)).intoBackground();
            mTvState.getTextColorBuilder().setTextColor(context.getColor(R.color.color_01)).intoTextColor();
            mTvState.setText("我发起");
        }

        if (listBeanList.get(position).getIs_drifting() == 0) {
            mTvTransmitState.setText("已结束");
        } else {
            mTvTransmitState.setText("传递中");
        }

        TextUtil.setText(mTvToName, listBeanList.get(position).getWho_hold());

        TextUtil.setText(mTvTransmitNum, context.getString(R.string.transmit, listBeanList.get(position).getTotal_attend() + ""));
        mLlContent.setOnClickListener(v -> {
            RaftingDetailsActivity.start(context, listBeanList.get(position).getMessage_id(), false);
        });

    }
}
