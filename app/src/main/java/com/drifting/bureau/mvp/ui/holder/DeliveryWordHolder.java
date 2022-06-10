package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.DeliveryDetailsEntity;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class DeliveryWordHolder extends BaseRecyclerHolder {
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_planet)
    TextView mTvPlanet;
    @BindView(R.id.iv_select)
    ImageView mIvSelecet;
    @BindView(R.id.iv_add_friend)
    ImageView mIvAddFriend;
    private Context context;
    public DeliveryWordHolder(View itemView) {
        super(itemView);
        context=itemView.getContext();
    }

    public void setData(List<DeliveryDetailsEntity.MessagePathBean> mDatas, int position) {
        TextUtil.setText(mTvName, mDatas.get(position).getUser_name());
        TextUtil.setText(mTvContent, mDatas.get(position).getContent());
        TextUtil.setText(mTvPlanet, mDatas.get(position).getPlanet_level_name());
        if (mDatas.get(position).getUser_id().equals(Preferences.getUserId())) {
            mIvSelecet.setImageResource(R.drawable.delivery_select);
            mIvAddFriend.setVisibility(View.GONE);
        } else {
            if (mDatas.get(position).getFriend_apply()==1){
                mIvAddFriend.setVisibility(View.GONE);
            }else {
                mIvAddFriend.setVisibility(View.VISIBLE);
            }
            mIvSelecet.setImageResource(R.drawable.delivery_unselect);
        }

        mIvAddFriend.setOnClickListener(v -> {
            RequestUtil.create().friendapply(mDatas.get(position).getUser_id(), entity -> {
                if (entity != null) {
                    if (entity.getCode()==200){
                        ToastUtil.showAddFriendDialog(context);
                    }else {
                        ToastUtil.showToast(entity.getMsg());
                    }
                }
            });
        });

    }

}
