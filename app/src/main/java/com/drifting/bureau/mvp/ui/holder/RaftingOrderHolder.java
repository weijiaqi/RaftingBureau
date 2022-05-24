package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.RaftingOrderEntity;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class RaftingOrderHolder  extends BaseRecyclerHolder {
    @BindView(R.id.view_bottm_line)
    View mViewBottomLine;

    private Context context;
    public RaftingOrderHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<RaftingOrderEntity> listBeanList, int position) {
      //  mViewBottomLine.setVisibility(position==listBeanList.size()-1?View.GONE:View.VISIBLE);
    }
}
