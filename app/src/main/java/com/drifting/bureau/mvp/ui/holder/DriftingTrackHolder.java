package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.DriftingTrackEntity;
import com.drifting.bureau.mvp.ui.activity.index.RaftingDetailsActivity;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.VerifyUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class DriftingTrackHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_transmit_num)
    TextView mTvTransmitNum;
    private Context context;
    public DriftingTrackHolder(View itemView) {
        super(itemView);
        context=itemView.getContext();
    }

    public void setData(@NonNull List<DriftingTrackEntity> listBeanList, int position) {
        //  mViewBottomLine.setVisibility(position==listBeanList.size()-1?View.GONE:View.VISIBLE);

        TextUtil.setText(mTvTransmitNum,  context.getString(R.string.transmit,position+8+""));
        mTvTransmitNum.setOnClickListener(v -> {
            RaftingDetailsActivity.start(context, false);
        });

    }
}
