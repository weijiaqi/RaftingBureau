package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.AnnouncementEntity;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class ArAnnouncementHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_receive_waves)
    TextView mTvReceiveWaves;
    @BindView(R.id.tv_create_time)
    TextView mTvCreateTime;

    private Context context;
    public ArAnnouncementHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<AnnouncementEntity> listBeanList, int position) {
        TextUtil.setText(mTvReceiveWaves, listBeanList.get(position).getTitle());
        TextUtil.setText(mTvCreateTime, DateUtil.unxiToDateYMD(listBeanList.get(position).getCreated_at_int()+""));
    }
}
