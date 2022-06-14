package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.AnswerEntity;
import com.drifting.bureau.mvp.model.entity.QuestionEntity;
import com.drifting.bureau.mvp.ui.holder.AnswerHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.ArrayList;
import java.util.List;

public class AnswerAdapter extends BaseRecyclerAdapter<QuestionEntity> {

    private List<AnswerEntity> entityList = new ArrayList<>();


    private SeletChangeListener mSeletChangeListener;

    public AnswerAdapter(List<QuestionEntity> infos, SeletChangeListener seletChangeListener) {
        super(infos);
        this.mSeletChangeListener = seletChangeListener;
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
        return new AnswerHolder(view, this);
    }


    public void remove(int position) {
        if (mDatas != null && mDatas.size() > position) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
    }


    public void setData(List data) {
        if (data != null) {
            this.mDatas = data;
            notifyDataSetChanged();
        }
    }


    /**
     * 选中状态更改
     */
    public void onItemCheckChange(AnswerEntity answerEntity) {
        if (answerEntity != null) {
            for (int i = 0; i < entityList.size(); i++) {
                if (entityList.get(i).getQuestionid() == answerEntity.getQuestionid()) {
                    entityList.remove(i);
                }
            }
            entityList.add(answerEntity);
            mSeletChangeListener.onSeletChange(entityList);
        }
    }


    /**
     * 选中监听
     */
    public interface SeletChangeListener {
        void onSeletChange(List<AnswerEntity> value);
    }
}
