package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.DeliveryDetailsEntity;
import com.drifting.bureau.mvp.ui.holder.DeliveryVideoHolder;
import com.drifting.bureau.mvp.ui.holder.DeliveryVoiceHolder;
import com.drifting.bureau.mvp.ui.holder.DeliveryWordHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

public class DeliveryDetailsAdapter extends BaseRecyclerAdapter<DeliveryDetailsEntity> {

    private int ITEM_WORD = 1;
    private int ITEM_VOICE = 2;
    private int ITEM_VIDEO = 3;

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        if (holder instanceof DeliveryWordHolder) {
            DeliveryWordHolder deliveryWordHolder = (DeliveryWordHolder) holder;
            deliveryWordHolder.setData(mDatas, position);
        } else if (holder instanceof DeliveryVoiceHolder) {
            DeliveryVoiceHolder deliveryVoiceHolder = (DeliveryVoiceHolder) holder;
            deliveryVoiceHolder.setData(mDatas, position);
        } else if (holder instanceof DeliveryVideoHolder) {
            DeliveryVideoHolder deliveryVideoHolder = (DeliveryVideoHolder) holder;
            deliveryVideoHolder.setData(mDatas, position);
        }
    }

    @Override
    protected int getLayoutId(int viewType) {
        if (viewType == ITEM_WORD) {
            return R.layout.item_delivery_word;
        } else if (viewType == ITEM_VOICE) {
            return R.layout.item_delivery_voice;
        } else {
            return R.layout.item_delivery_video;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position).getType() == 1) {
            return ITEM_WORD;
        } else if (mDatas.get(position).getType() == 2) {
            return ITEM_VOICE;
        } else {
            return ITEM_VIDEO;
        }
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        if (viewType == ITEM_WORD) {
            return new DeliveryWordHolder(view);
        } else if (viewType == ITEM_VOICE) {
            return new DeliveryVoiceHolder(view);
        } else {
            return new DeliveryVideoHolder(view);
        }
    }
}
