package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.ui.holder.BoxPasswordHolder;
import com.drifting.bureau.mvp.ui.holder.SimpleHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class KeyboardAdapter extends BaseRecyclerAdapter<String> {


    /**
     * 数字按钮条目
     */
    public static final int TYPE_NORMAL = 0;
    /**
     * 删除按钮条目
     */
    public static final int TYPE_DELETE = 1;
    /**
     * 空按钮条目
     */
    public static final int TYPE_EMPTY = 2;

    public KeyboardAdapter(List<String> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        if (holder instanceof SimpleHolder) {
            SimpleHolder simpleHolder = (SimpleHolder) holder;
            simpleHolder.setData(mDatas, position);
        } else {
            BoxPasswordHolder boxPasswordHolder = (BoxPasswordHolder) holder;
            boxPasswordHolder.setData(mDatas, position);
        }
    }


    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 9:
                return TYPE_EMPTY;
            case 11:
                return TYPE_DELETE;
            default:
                return TYPE_NORMAL;
        }
    }


    @Override
    protected int getLayoutId(int viewType) {
        if (viewType == TYPE_DELETE) {
            return R.layout.pay_password_delete_item;
        } else if (viewType == TYPE_EMPTY) {
            return R.layout.pay_password_empty_item;
        } else {
            return R.layout.pay_password_normal_item;
        }
    }


    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        if (viewType == TYPE_DELETE) {
            return new SimpleHolder(view);
        } else if (viewType == TYPE_EMPTY) {
            return new SimpleHolder(view);
        } else {
            return new BoxPasswordHolder(view);
        }
    }

}
