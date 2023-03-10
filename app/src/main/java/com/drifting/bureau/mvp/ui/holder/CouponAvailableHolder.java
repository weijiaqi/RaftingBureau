package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.CouponsMineEntity;
import com.drifting.bureau.mvp.model.entity.MyTreasuryEntity;
import com.drifting.bureau.mvp.ui.adapter.CouponAvailableAdapter;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.SpannableUtil;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.ToastUtil;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseHolder;

import butterknife.BindView;

public class CouponAvailableHolder extends BaseHolder<CouponsMineEntity.ListBean> {
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
     @BindView(R.id.rl_content)
    RelativeLayout mRlContent;
    @BindView(R.id.ck_sellect)
    CheckBox mCkSelect;
    private Context context;

    private CouponAvailableAdapter mAdapter;
    private SpannableStringBuilder passerFit;
    public CouponAvailableHolder(View itemView, CouponAvailableAdapter myTreasuryAdapter) {
        super(itemView);
        context = itemView.getContext();
        this.mAdapter = myTreasuryAdapter;
    }

    @Override
    public void setData(@NonNull CouponsMineEntity.ListBean data, int position) {

        if (data.getCoupon_type() == 1) {
            if (data.getType() == 0) {
                passerFit = SpannableUtil.getBuilder(context, data.getDiscount_rate() * 10 + "").setForegroundColor(R.color.color_e5).setTextSize(20).setBold().append("???").setForegroundColor(R.color.color_e5).setTextSize(12).setBold().build();
                mTvCondition.setTextColor(context.getColor(R.color.white));
                mTvName.setTextColor(context.getColor(R.color.white));
                mTvTime.setTextColor(context.getColor(R.color.white));
                mTvDesc.setTextColor(context.getColor(R.color.white));
                mCkSelect.setVisibility(View.VISIBLE);
                mIvCouponType.setImageResource(R.drawable.fulltwo);
            } else {
                passerFit = SpannableUtil.getBuilder(context, data.getDiscount_rate() * 10 + "").setForegroundColor(R.color.color_99).setTextSize(20).setBold().append("???").setForegroundColor(R.color.color_99).setTextSize(12).setBold().build();
                mTvCondition.setTextColor(context.getColor(R.color.color_99));
                mTvName.setTextColor(context.getColor(R.color.color_99));
                mTvTime.setTextColor(context.getColor(R.color.color_99));
                mTvDesc.setTextColor(context.getColor(R.color.color_99));
                mCkSelect.setVisibility(View.GONE);
                mIvCouponType.setImageResource(R.drawable.fulltwo_gray);
            }
            mTvPrice.setText(passerFit);
            TextUtil.setText(mTvCondition, "????????????");
        } else if (data.getCoupon_type() == 2) {   //??????
            if (data.getType() == 0) {
                passerFit = SpannableUtil.getBuilder(context, "???").setForegroundColor(R.color.color_e5).setTextSize(20).setBold().build();
                mTvCondition.setTextColor(context.getColor(R.color.white));
                mTvName.setTextColor(context.getColor(R.color.white));
                mTvTime.setTextColor(context.getColor(R.color.white));
                mTvDesc.setTextColor(context.getColor(R.color.white));
                mCkSelect.setVisibility(View.VISIBLE);
                mIvCouponType.setImageResource(R.drawable.fullthree);
            } else {
                passerFit = SpannableUtil.getBuilder(context, "???").setForegroundColor(R.color.color_99).setTextSize(20).setBold().build();
                mTvName.setTextColor(context.getColor(R.color.color_99));
                mTvCondition.setTextColor(context.getColor(R.color.color_99));
                mTvTime.setTextColor(context.getColor(R.color.color_99));
                mTvDesc.setTextColor(context.getColor(R.color.color_99));
                mCkSelect.setVisibility(View.GONE);
                mIvCouponType.setImageResource(R.drawable.fullthree_gray);
            }
            mTvPrice.setText(passerFit);
            TextUtil.setText(mTvCondition, "????????????");

        } else if (data.getCoupon_type() == 3) {   //?????????
            if (data.getType() == 0) {
                passerFit = SpannableUtil.getBuilder(context,data.getDeduct_money()).setForegroundColor(R.color.color_e5).setTextSize(20).setBold().append("???").setForegroundColor(R.color.color_e5).setTextSize(12).setBold().build();
                mTvName.setTextColor(context.getColor(R.color.white));
                mTvCondition.setTextColor(context.getColor(R.color.white));
                mTvTime.setTextColor(context.getColor(R.color.white));
                mTvDesc.setTextColor(context.getColor(R.color.white));
                mCkSelect.setVisibility(View.VISIBLE);
                mIvCouponType.setImageResource(R.drawable.fullfour);
            } else {
                passerFit = SpannableUtil.getBuilder(context,data.getDeduct_money()).setForegroundColor(R.color.color_99).setTextSize(20).setBold().append("???").setForegroundColor(R.color.color_99).setTextSize(12).setBold().build();
                mTvName.setTextColor(context.getColor(R.color.color_99));
                mTvCondition.setTextColor(context.getColor(R.color.color_99));
                mTvTime.setTextColor(context.getColor(R.color.color_99));
                mTvDesc.setTextColor(context.getColor(R.color.color_99));
                mCkSelect.setVisibility(View.GONE);
                mIvCouponType.setImageResource(R.drawable.fullfour_gray);
            }
            mTvPrice.setText(passerFit);
            TextUtil.setText(mTvCondition, "???????????????");
        } else if (data.getCoupon_type() == 4) {   //??????
            if (data.getType() == 0) {
                passerFit = SpannableUtil.getBuilder(context, data.getDeduct_money()).setForegroundColor(R.color.color_e5).setTextSize(20).setBold().append("???").setForegroundColor(R.color.color_e5).setTextSize(12).setBold().build();
                mTvName.setTextColor(context.getColor(R.color.white));
                mTvCondition.setTextColor(context.getColor(R.color.white));
                mTvTime.setTextColor(context.getColor(R.color.white));
                mTvDesc.setTextColor(context.getColor(R.color.white));
                mCkSelect.setVisibility(View.VISIBLE);
                mIvCouponType.setImageResource(R.drawable.fullone);
            } else {
                passerFit = SpannableUtil.getBuilder(context, data.getDeduct_money()).setForegroundColor(R.color.color_99).setTextSize(20).setBold().append("???").setForegroundColor(R.color.color_99).setTextSize(12).setBold().build();
                mTvName.setTextColor(context.getColor(R.color.color_99));
                mTvCondition.setTextColor(context.getColor(R.color.color_99));
                mTvTime.setTextColor(context.getColor(R.color.color_99));
                mTvDesc.setTextColor(context.getColor(R.color.color_99));
                mCkSelect.setVisibility(View.GONE);
                mIvCouponType.setImageResource(R.drawable.fullone_gray);
            }
            mTvPrice.setText(passerFit);
            TextUtil.setText(mTvCondition, context.getString(R.string.full_reduction, data.getReach_money() + ""));
        }
        TextUtil.setText(mTvName, data.getName());
        if (data.getExpired() == 0) {  //????????????
            TextUtil.setText(mTvTime, "????????????");
        } else {
            TextUtil.setText(mTvTime, DateUtil.unixToYMD(data.getExpired_at_int() + "") + "??????");
        }
        TextUtil.setText(mTvDesc, "???????????????" +data.getUse_range());
        if (mAdapter.getSelectEntities() != null) {
            if ( mAdapter.getSelectEntities().size()>0&&TextUtils.equals( mAdapter.getSelectEntities().get(0).getCode(),data.getCode())) {
                mCkSelect.setChecked(true);
            } else {
                mCkSelect.setChecked(false);
            }
        }

        mRlContent.setOnClickListener(view -> {
            if (data.getType()==0){
                mAdapter.onItemCheckChange(data);
            }
        });
    }
}
