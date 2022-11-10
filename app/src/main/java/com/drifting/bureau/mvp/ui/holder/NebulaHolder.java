package com.drifting.bureau.mvp.ui.holder;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.NebulaEntity;
import com.drifting.bureau.mvp.ui.activity.index.NebulaeActivity;
import com.drifting.bureau.util.TextUtil;
import com.hjq.shape.view.ShapeView;
import com.jess.arms.base.BaseRecyclerHolder;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;

public class NebulaHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_nebulae)
    TextView mTvNebulae;
    @BindView(R.id.shpe_circle1)
    ShapeView mShapeCircle1;
    @BindView(R.id.shpe_circle)
    ShapeView mShapeCircle;
    @BindView(R.id.rl_circle)
    RelativeLayout mRlCircle;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    private Context context;
    private ObjectAnimator objectAnimator;

    public NebulaHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }


    public void setData(List<NebulaEntity.ListBean> list, int postion) {
        TextUtil.setText(mTvName, list.get(postion).getNebula()+"星云");
        TextUtil.setText(mTvNum, list.get(postion).getMeta_power());
        mTvNebulae.setOnClickListener(v -> {
            NebulaeActivity.start(context, list.get(postion).getNebula_code(), false);
        });

        if (TextUtils.equals(list.get(postion).getNebula(), "未知星云")) {
            mTvNebulae.setVisibility(View.GONE);
            mRlCircle.setVisibility(View.GONE);
            mLlBottom.setVisibility(View.GONE);
        } else {
            mTvNebulae.setVisibility(View.VISIBLE);
            mRlCircle.setVisibility(View.VISIBLE);
            mLlBottom.setVisibility(View.VISIBLE);
        }

        RelativeLayout.LayoutParams returnLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        returnLP.setMargins(0, ArmsUtils.dip2px(context, list.get(postion).getNebula_y() * 9), ArmsUtils.dip2px(context, list.get(postion).getNebula_x() * 2), 0);
        mRlCircle.setLayoutParams(returnLP);

        startAnim(mShapeCircle);
        startAnim(mShapeCircle1);
    }

    public void startAnim(View view) {
        objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();
    }

}
