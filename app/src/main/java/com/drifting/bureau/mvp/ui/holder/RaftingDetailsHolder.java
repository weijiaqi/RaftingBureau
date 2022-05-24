package com.drifting.bureau.mvp.ui.holder;

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

public class RaftingDetailsHolder extends BaseRecyclerHolder {
   @BindView(R.id.tv_content)
    TextView mTvContent;
    public RaftingDetailsHolder(View itemView) {
        super(itemView);
    }

    public void setData(@NonNull List<BarrageEntity> listBeanList, int position) {
        TextUtil.setText(mTvContent,listBeanList.get(position % listBeanList.size()).getText());
        mTvContent.setTextColor(ColorUtil.getColorByRgb(null));
    }
}
