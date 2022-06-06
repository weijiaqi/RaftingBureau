package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.MySpaceStationEntity;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class MySpaceStationHolder extends BaseRecyclerHolder {
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_rights)
    TextView mTvRights;

    private Context context;
    public MySpaceStationHolder(View itemView) {
        super(itemView);
        context=itemView.getContext();
    }

    public void setData(@NonNull List<MySpaceStationEntity.OwnRightsBean> listBeanList, int position) {
        GlideUtil.create().loadNormalPic(context,listBeanList.get(position).getImage_url(),mIvPic);
        TextUtil.setText(mTvTitle, listBeanList.get(position).getName());
        TextUtil.setText(mTvRights, listBeanList.get(position).getRights());
    }
}
