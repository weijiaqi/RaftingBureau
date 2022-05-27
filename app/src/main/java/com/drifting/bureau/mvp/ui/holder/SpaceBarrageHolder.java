package com.drifting.bureau.mvp.ui.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.BarrageEntity;
import com.drifting.bureau.util.ColorUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class SpaceBarrageHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_content)
    TextView mTvContent;

    public SpaceBarrageHolder(View itemView) {
        super(itemView);
    }

    public void setData(@NonNull List<BarrageEntity> listBeanList, int position) {
        if (!TextUtils.isEmpty(listBeanList.get(position % listBeanList.size()).getText())) {
            mTvContent.setVisibility(View.VISIBLE);
        } else {
            mTvContent.setVisibility(View.INVISIBLE);
        }
        TextUtil.setText(mTvContent, listBeanList.get(position % listBeanList.size()).getText());
        mTvContent.setTextColor(ColorUtil.getColorByRgb(null));
    }
}
