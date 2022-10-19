package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.AoubtMeEntity;
import com.drifting.bureau.mvp.ui.activity.index.MyCouponActivity;
import com.drifting.bureau.mvp.ui.activity.index.TeaShopActivity;
import com.drifting.bureau.mvp.ui.activity.user.DriftingTrackActivity;
import com.drifting.bureau.mvp.ui.activity.user.OrderRecordActivity;
import com.drifting.bureau.mvp.ui.activity.user.SpaceMarinesActivity;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class AboutMeHolder extends BaseRecyclerHolder {
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    private Context context;

    public AboutMeHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<AoubtMeEntity> listBeanList, int position) {
        TextUtil.setText(mTvTitle, listBeanList.get(position).getTitle());
        TextUtil.setText(mTvContent, listBeanList.get(position).getContent());
        if (position == 0) {
            mIvPic.setImageResource(R.drawable.drifting_track);
        }else if(position==1){
            mIvPic.setImageResource(R.drawable.me_coupon);
        } else if (position == 2) {
            mIvPic.setImageResource(R.drawable.order_record);
        } else if (position == 3) {
            mIvPic.setImageResource(R.drawable.space_marines);
        } else if (position == 4) {
            mIvPic.setImageResource(R.drawable.nearby_stores);
        }
        mLlContent.setOnClickListener(v -> {
            if (position == 0) {  //漂流轨迹
                DriftingTrackActivity.start(context, false);
            }else if (position == 1) {  //我的卡券
                MyCouponActivity.start(context, false);
            } else if (position == 2) {  //订单记录
                OrderRecordActivity.start(context, false);
            } else if (position == 3) { //星际战队
                SpaceMarinesActivity.start(context, false);
            } else if (position == 4) { //漂流局店
                TeaShopActivity.start(context, false);
            }
        });
    }
}
