package com.jess.arms.base;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.utils.ThirdViewUtil;
import com.zhy.autolayout.utils.AutoUtils;


/**
 * RecyclerViewHolder 基类
 *
 * @author zhaod
 * @date 2018/12/26
 */

public class BaseRecyclerHolder extends RecyclerView.ViewHolder {
    public ImageView mIcon;
    public Button mCreativeButton;
    public TextView mTitle;
    public TextView mDescription;
    public TextView mSource;
    public Button mStopButton;
    public Button mRemoveButton;
    public ImageView ivListitemDislike;

    public BaseRecyclerHolder(View itemView) {
        super(itemView);

        //屏幕适配
        if (ThirdViewUtil.isUseAutolayout()) {
            AutoUtils.autoSize(itemView);
        }
        //绑定 ButterKnife
        ThirdViewUtil.bindTarget(this, itemView);
    }

    /**
     * 点击事件
     *
     * @param listener
     * @return
     */

    public BaseRecyclerHolder setOnClickListener(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
        return this;
    }
}
