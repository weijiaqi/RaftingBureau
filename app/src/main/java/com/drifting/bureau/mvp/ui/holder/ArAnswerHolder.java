package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.AnswerEntity;
import com.drifting.bureau.mvp.model.entity.QuestionEntity;
import com.drifting.bureau.mvp.ui.adapter.ArAnswerAdapter;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class ArAnswerHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ll_one)
    LinearLayout mLlOne;
    @BindView(R.id.ll_two)
    LinearLayout mLlTwo;
    @BindView(R.id.sellectA)
    CheckBox mSelectA;
    @BindView(R.id.sellectB)
    CheckBox mSelectB;
    @BindView(R.id.sellectImgA)
    CheckBox mSelectImgA;
    @BindView(R.id.sellectImgB)
    CheckBox mSelectImgB;
    @BindView(R.id.tv_answerA)
    TextView mTvAnswerA;
    @BindView(R.id.tv_answerB)
    TextView mTvAnswerB;
    @BindView(R.id.iv_picA)
    ImageView mIvpicA;
    @BindView(R.id.iv_picB)
    ImageView mIvpicB;
    private ArAnswerAdapter adapter;
    private Context context;

    public ArAnswerHolder(View itemView, ArAnswerAdapter adapter) {
        super(itemView);
        this.context = itemView.getContext();
        this.adapter = adapter;
    }

    public void setData(@NonNull List<QuestionEntity> data, int position) {
        initial(data.get(position).getQ_type());
        if (data.get(position).getQ_type() == 0) {
            TextUtil.setText(mTvAnswerA, data.get(position).getA());
            TextUtil.setText(mTvAnswerB, data.get(position).getB());
        } else {
            GlideUtil.create().loadLongImage(context, data.get(position).getA(), mIvpicA);
            GlideUtil.create().loadLongImage(context, data.get(position).getB(), mIvpicB);
        }
        TextUtil.setText(mTvTitle,data.get(position).getPostion() + "、"+ data.get(position).getQuestion());

        mSelectA.setOnClickListener(v -> {
            setCheckStatus(1);
            setAnswer(data.get(0).getQuestion_id(), "A");
        });
        mSelectB.setOnClickListener(v -> {
            setCheckStatus(2);
            setAnswer(data.get(0).getQuestion_id(), "B");
        });

        mSelectImgA.setOnClickListener(view -> {
            setCheckImgStatus(1);
            setAnswer(data.get(0).getQuestion_id(), "A");
        });

        mSelectImgB.setOnClickListener(view -> {
            setCheckImgStatus(2);
            setAnswer(data.get(0).getQuestion_id(), "B");
        });
    }

    public void initial(int type) {
        if (type == 0) {
            mLlOne.setVisibility(View.VISIBLE);
            mLlTwo.setVisibility(View.GONE);
            mSelectA.setChecked(false);
            mSelectB.setChecked(false);
        } else {
            mLlOne.setVisibility(View.GONE);
            mLlTwo.setVisibility(View.VISIBLE);
            mSelectImgA.setChecked(false);
            mSelectImgB.setChecked(false);
        }
    }

    public void setCheckStatus(int status) {
        mSelectA.setChecked(status == 1 ? true : false);
        mSelectB.setChecked(status == 1 ? false : true);
    }


    public void setCheckImgStatus(int status) {
        mSelectA.setChecked(status == 1 ? true : false);
        mSelectB.setChecked(status == 1 ? false : true);
    }


    public void setAnswer(int questionid, String value) {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setQuestionid(questionid);
        answerEntity.setValue(value);
        adapter.onItemCheckChange(answerEntity);
    }
}
