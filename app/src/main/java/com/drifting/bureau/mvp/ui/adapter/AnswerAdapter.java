package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.AnswerEntity;
import com.drifting.bureau.mvp.ui.holder.AnswerHolder;
import com.drifting.bureau.mvp.ui.holder.HotListHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class AnswerAdapter extends BaseRecyclerAdapter<AnswerEntity> {

    public AnswerAdapter(List<AnswerEntity> infos) {
        super(infos);
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        AnswerHolder answerHolder = (AnswerHolder) holder;
        answerHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_answer;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new AnswerHolder(view);
    }


    public void remove(int position) {
        if (mDatas != null &&mDatas.size()>position) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

}
