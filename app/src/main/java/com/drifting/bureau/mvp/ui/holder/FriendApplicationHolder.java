package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.FriendApplicationEvent;
import com.drifting.bureau.mvp.model.entity.FriendApplicationEntity;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseRecyclerHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class FriendApplicationHolder extends BaseRecyclerHolder {

    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_add)
    ShapeTextView mTvAdd;
    private Context context;

    public FriendApplicationHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(List<FriendApplicationEntity.ListBean> list, int postion) {
        GlideUtil.create().loadCirclePic(context, list.get(postion).getProfile_photo(), mIvHead);
        TextUtil.setText(mTvName, list.get(postion).getFrom_user_name());
        TextUtil.setText(mTvContent, list.get(postion).getSay_something());
        if (list.get(postion).getStatus() == 0) {
            mTvAdd.getShapeDrawableBuilder().setSolidGradientColors(context.getColor(R.color.color_6c), context.getColor(R.color.color_6d)).intoBackground();
            mTvAdd.getTextColorBuilder().setTextColor(context.getColor(R.color.color_00)).intoTextColor();
            mTvAdd.setTypeface(null, Typeface.BOLD);
            mTvAdd.setText("添加");

        } else if (list.get(postion).getStatus() == 1) {
            mTvAdd.getShapeDrawableBuilder().setSolidGradientColors(context.getColor(R.color.transparent), context.getColor(R.color.transparent)).intoBackground();
            mTvAdd.getTextColorBuilder().setTextColor(context.getColor(R.color.color_99)).intoTextColor();
            mTvAdd.setTypeface(null, Typeface.NORMAL);
            mTvAdd.setText("已添加");

        } else {
            mTvAdd.getShapeDrawableBuilder().setSolidGradientColors(context.getColor(R.color.transparent), context.getColor(R.color.transparent)).intoBackground();
            mTvAdd.getTextColorBuilder().setTextColor(context.getColor(R.color.color_f1)).intoTextColor();
            mTvAdd.setTypeface(null, Typeface.NORMAL);
            mTvAdd.setText("已拒绝");

        }

        mTvAdd.setOnClickListener(view -> {
            if (list.get(postion).getStatus() == 0){
                RequestUtil.create().agreeRefuse(list.get(postion).getApply_id(), 1, entity -> {
                    if (entity.getCode() == 200) {
                        EventBus.getDefault().post(new FriendApplicationEvent());
                    }
                });
            }
        });
    }
}
