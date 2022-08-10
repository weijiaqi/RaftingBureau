package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.NebulaListEntity;
import com.drifting.bureau.util.TextUtil;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class NebulaListHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_name)
    ShapeTextView mTvName;
    @BindView(R.id.iv_clock)
    ImageView mIvClock;

    private Context context;

    public NebulaListHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<NebulaListEntity.ListBean> listBeanList, int position, String nebula) {
        TextUtil.setText(mTvName, listBeanList.get(position).getNebula());
        if (TextUtils.equals(listBeanList.get(position).getNebula_code(), nebula)) {
            mTvName.setAlpha(1);
            mTvName.getShapeDrawableBuilder().setSolidGradientColors(context.getColor(R.color.color_d6_7f), context.getColor(R.color.color_70_7f)).intoBackground();
            mIvClock.setVisibility(View.GONE);
        } else if (listBeanList.get(position).getUnlock() == 1) { //1解锁了
            mTvName.setAlpha(1);
            mTvName.getShapeDrawableBuilder().setSolidGradientColors(context.getColor(R.color.color_0de), context.getColor(R.color.color_0de)).intoBackground();
            mIvClock.setVisibility(View.GONE);
        } else {
            mTvName.setAlpha((float) 0.1);
            mTvName.getShapeDrawableBuilder().setSolidGradientColors(context.getColor(R.color.color_00_cc), context.getColor(R.color.color_00_cc)).intoBackground();
            mIvClock.setVisibility(View.VISIBLE);
        }
    }
}
