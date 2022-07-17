package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.BarrageEntity;
import com.drifting.bureau.mvp.ui.holder.RaftingDetailsHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class RaftingDetailsAdapter extends BaseRecyclerAdapter<BarrageEntity.CommentsBean> {

    private RaftingChangeListener mSeletChangeListener;

    public RaftingDetailsAdapter(List<BarrageEntity.CommentsBean> infos,RaftingChangeListener mSeletChangeListener) {
        super(infos);
        this.mSeletChangeListener = mSeletChangeListener;
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        RaftingDetailsHolder raftingDetailsHolder=(RaftingDetailsHolder) holder;
        raftingDetailsHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_rafting_details;
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new RaftingDetailsHolder(view,this);
    }


    /**
     * 选中状态更改
     */
    public void onItemCheckChange(BarrageEntity.CommentsBean object) {
        if (object != null) {
            mSeletChangeListener.onSeletChange(object);
        }
    }

    /**
     * 选中监听
     */
    public interface RaftingChangeListener {
        void onSeletChange(BarrageEntity.CommentsBean entity);
    }
}
