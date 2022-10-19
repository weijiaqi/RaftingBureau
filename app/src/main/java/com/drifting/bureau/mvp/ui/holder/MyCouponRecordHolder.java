package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.CouponsMineEntity;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.SpannableUtil;
import com.drifting.bureau.util.TextUtil;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class MyCouponRecordHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_condition)
    TextView mTvCondition;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.iv_coupon_type)
    ImageView mIvCouponType;
    @BindView(R.id.tv_use)
    ShapeTextView mTvUse;
    private Context context;
    private SpannableStringBuilder passerFit;

    public MyCouponRecordHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<CouponsMineEntity.ListBean> listBeanList, int position, int type) {
        if (listBeanList.get(position).getCoupon_type() == 1) {
            if (type == 0) {
                passerFit = SpannableUtil.getBuilder(context, listBeanList.get(position).getDiscount_rate() * 10 + "").setForegroundColor(R.color.color_e5).setTextSize(20).setBold().append("折").setForegroundColor(R.color.color_e5).setTextSize(12).setBold().build();
                mTvCondition.setTextColor(context.getColor(R.color.white));
                mTvName.setTextColor(context.getColor(R.color.white));
                mTvTime.setTextColor(context.getColor(R.color.white));
                mTvDesc.setTextColor(context.getColor(R.color.white));
                mTvUse.setText("可使用");
                mTvUse.getShapeDrawableBuilder().setSolidColor(context.getColor(R.color.color_bd)).intoBackground();
                mIvCouponType.setImageResource(R.drawable.fulltwo);
            } else {
                passerFit = SpannableUtil.getBuilder(context, listBeanList.get(position).getDiscount_rate() * 10 + "").setForegroundColor(R.color.color_99).setTextSize(20).setBold().append("折").setForegroundColor(R.color.color_99).setTextSize(12).setBold().build();
                mTvCondition.setTextColor(context.getColor(R.color.color_99));
                mTvName.setTextColor(context.getColor(R.color.color_99));
                mTvTime.setTextColor(context.getColor(R.color.color_99));
                mTvDesc.setTextColor(context.getColor(R.color.color_99));
                mTvUse.setText("已过期");
                mTvUse.getShapeDrawableBuilder().setSolidColor(context.getColor(R.color.color_99)).intoBackground();
                mIvCouponType.setImageResource(R.drawable.fulltwo_gray);
            }
            mTvPrice.setText(passerFit);
            TextUtil.setText(mTvCondition, "指定折扣");
        } else if (listBeanList.get(position).getCoupon_type() == 2) {   //免单
            if (type == 0) {
                passerFit = SpannableUtil.getBuilder(context, "免").setForegroundColor(R.color.color_e5).setTextSize(20).setBold().build();
                mTvCondition.setTextColor(context.getColor(R.color.white));
                mTvName.setTextColor(context.getColor(R.color.white));
                mTvTime.setTextColor(context.getColor(R.color.white));
                mTvDesc.setTextColor(context.getColor(R.color.white));
                mTvUse.setText("可使用");
                mTvUse.getShapeDrawableBuilder().setSolidColor(context.getColor(R.color.color_bd)).intoBackground();
                mIvCouponType.setImageResource(R.drawable.fullthree);
            } else {
                passerFit = SpannableUtil.getBuilder(context, "免").setForegroundColor(R.color.color_99).setTextSize(20).setBold().build();
                mTvName.setTextColor(context.getColor(R.color.color_99));
                mTvCondition.setTextColor(context.getColor(R.color.color_99));
                mTvTime.setTextColor(context.getColor(R.color.color_99));
                mTvDesc.setTextColor(context.getColor(R.color.color_99));
                mTvUse.setText("已过期");
                mTvUse.getShapeDrawableBuilder().setSolidColor(context.getColor(R.color.color_99)).intoBackground();
                mIvCouponType.setImageResource(R.drawable.fullthree_gray);
            }
            mTvPrice.setText(passerFit);
            TextUtil.setText(mTvCondition, "指定免单");

        } else if (listBeanList.get(position).getCoupon_type() == 3) {   //代金券
            if (type == 0) {
                passerFit = SpannableUtil.getBuilder(context, listBeanList.get(position).getDeduct_money()).setForegroundColor(R.color.color_e5).setTextSize(20).setBold().append("元").setForegroundColor(R.color.color_e5).setTextSize(12).setBold().build();
                mTvName.setTextColor(context.getColor(R.color.white));
                mTvCondition.setTextColor(context.getColor(R.color.white));
                mTvTime.setTextColor(context.getColor(R.color.white));
                mTvDesc.setTextColor(context.getColor(R.color.white));
                mTvUse.setText("可使用");
                mTvUse.getShapeDrawableBuilder().setSolidColor(context.getColor(R.color.color_bd)).intoBackground();
                mIvCouponType.setImageResource(R.drawable.fullfour);
            } else {
                passerFit = SpannableUtil.getBuilder(context, listBeanList.get(position).getDeduct_money()).setForegroundColor(R.color.color_99).setTextSize(20).setBold().append("元").setForegroundColor(R.color.color_99).setTextSize(12).setBold().build();
                mTvName.setTextColor(context.getColor(R.color.color_99));
                mTvCondition.setTextColor(context.getColor(R.color.color_99));
                mTvTime.setTextColor(context.getColor(R.color.color_99));
                mTvDesc.setTextColor(context.getColor(R.color.color_99));
                mTvUse.setText("已过期");
                mTvUse.getShapeDrawableBuilder().setSolidColor(context.getColor(R.color.color_99)).intoBackground();
                mIvCouponType.setImageResource(R.drawable.fullfour_gray);
            }
            mTvPrice.setText(passerFit);
            TextUtil.setText(mTvCondition, "使用代金券");
        } else if (listBeanList.get(position).getCoupon_type() == 4) {   //满减
            if (type == 0) {
                passerFit = SpannableUtil.getBuilder(context, listBeanList.get(position).getDeduct_money()).setForegroundColor(R.color.color_e5).setTextSize(20).setBold().append("元").setForegroundColor(R.color.color_e5).setTextSize(12).setBold().build();
                mTvName.setTextColor(context.getColor(R.color.white));
                mTvCondition.setTextColor(context.getColor(R.color.white));
                mTvTime.setTextColor(context.getColor(R.color.white));
                mTvDesc.setTextColor(context.getColor(R.color.white));
                mTvUse.setText("可使用");
                mTvUse.getShapeDrawableBuilder().setSolidColor(context.getColor(R.color.color_bd)).intoBackground();
                mIvCouponType.setImageResource(R.drawable.fullone);
            } else {
                passerFit = SpannableUtil.getBuilder(context, listBeanList.get(position).getDeduct_money()).setForegroundColor(R.color.color_99).setTextSize(20).setBold().append("元").setForegroundColor(R.color.color_99).setTextSize(12).setBold().build();
                mTvName.setTextColor(context.getColor(R.color.color_99));
                mTvCondition.setTextColor(context.getColor(R.color.color_99));
                mTvTime.setTextColor(context.getColor(R.color.color_99));
                mTvDesc.setTextColor(context.getColor(R.color.color_99));
                mTvUse.setText("已过期");
                mTvUse.getShapeDrawableBuilder().setSolidColor(context.getColor(R.color.color_99)).intoBackground();
                mIvCouponType.setImageResource(R.drawable.fullone_gray);
            }
            mTvPrice.setText(passerFit);
            TextUtil.setText(mTvCondition, context.getString(R.string.full_reduction, listBeanList.get(position).getReach_money() + ""));
        }
        TextUtil.setText(mTvName, listBeanList.get(position).getName());
        if (listBeanList.get(position).getExpired() == 0) {  //永久有效
            TextUtil.setText(mTvTime, "永久有效");
        } else {
            TextUtil.setText(mTvTime, DateUtil.unixToYMD(listBeanList.get(position).getExpired_at_int() + "") + "过期");
        }
        TextUtil.setText(mTvDesc, "使用范围：" +listBeanList.get(position).getUse_range());
    }
}
