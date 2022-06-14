package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.AnswerEntity;
import com.drifting.bureau.mvp.model.entity.QuestionEntity;
import com.drifting.bureau.mvp.ui.adapter.AnswerAdapter;
import com.drifting.bureau.util.TextUtil;
import com.hjq.shape.layout.ShapeRelativeLayout;
import com.jess.arms.base.BaseRecyclerHolder;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import retrofit2.http.PUT;

public class AnswerHolder extends BaseRecyclerHolder {
    @BindView(R.id.rl_a)
    ShapeRelativeLayout mRla;
    @BindView(R.id.rl_b)
    ShapeRelativeLayout mRlb;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_num)
    TextView mTvnum;
    @BindView(R.id.tv_answerA)
    TextView mTvAnswerA;
    @BindView(R.id.tv_answerB)
    TextView mTvAnswerB;
    @BindView(R.id.iv_selectA)
    ImageView mIvSelectA;
    @BindView(R.id.iv_selectB)
    ImageView mIvSelectB;
    @BindView(R.id.sellectA)
    CheckBox mSelectA;
    @BindView(R.id.sellectB)
    CheckBox mSelectB;
    @BindView(R.id.rl_one)
    RelativeLayout mRlOne;
    @BindView(R.id.ll_two)
    LinearLayout mLlTwo;

    private Context context;
    private AnswerAdapter answerAdapter;

    public AnswerHolder(View itemView, AnswerAdapter answerAdapter) {
        super(itemView);
        context = itemView.getContext();
        this.answerAdapter = answerAdapter;
    }

    public void setData(@NonNull List<QuestionEntity> data, int position) {
        initial(data.get(position).getQ_type());
        TextUtil.setText(mTvTitle, data.get(position).getQuestion());
        TextUtil.setText(mTvnum, data.get(position).getPostion() + "");
        TextUtil.setText(mTvAnswerA, "A." + data.get(position).getA());
        TextUtil.setText(mTvAnswerB, "B." + data.get(position).getB());

        mRla.setOnClickListener(v -> {
            setRlStatus(true);
            setAnswer(data.get(0).getQuestion_id(),"A");
        });
        mRlb.setOnClickListener(v -> {
            setRlStatus(false);
            setAnswer(data.get(0).getQuestion_id(),"B");
        });
        mSelectA.setOnClickListener(v -> {
            setCheckStatus(1);
            setAnswer(data.get(0).getQuestion_id(),"A");
        });
        mSelectB.setOnClickListener(v -> {
            setCheckStatus(2);
            setAnswer(data.get(0).getQuestion_id(),"B");
        });
    }

    public void initial(int type) {
        mRla.getShapeDrawableBuilder().setStrokeColor(context.getColor(R.color.color_6d4)).setStrokeWidth(ArmsUtils.dip2px(context, 1)).setSolidColor(context.getColor(R.color.transparent)).intoBackground();
        mRlb.getShapeDrawableBuilder().setStrokeColor(context.getColor(R.color.color_6d4)).setStrokeWidth(ArmsUtils.dip2px(context, 1)).setSolidColor(context.getColor(R.color.transparent)).intoBackground();
        mIvSelectA.setVisibility(View.GONE);
        mIvSelectB.setVisibility(View.GONE);
        mTvAnswerA.setTextColor(context.getColor(R.color.color_6d4));
        mTvAnswerB.setTextColor(context.getColor(R.color.color_6d4));
        if (type == 0) {
            mRlOne.setVisibility(View.VISIBLE);
            mLlTwo.setVisibility(View.GONE);
        } else {
            mRlOne.setVisibility(View.GONE);
            mLlTwo.setVisibility(View.VISIBLE);
            mSelectA.setChecked(false);
            mSelectB.setChecked(false);
        }
    }

    public void setCheckStatus(int status) {
        mSelectA.setChecked(status == 1 ? true : false);
        mSelectB.setChecked(status == 1 ? false : true);
    }


    public void setRlStatus(boolean type) {
        if (type) {
            mRla.getShapeDrawableBuilder().setStrokeColor(context.getColor(R.color.transparent)).setSolidGradientColors(context.getColor(R.color.color_d6), context.getColor(R.color.color_70)).intoBackground();
            mRlb.getShapeDrawableBuilder().setStrokeColor(context.getColor(R.color.color_6d4)).setStrokeWidth(ArmsUtils.dip2px(context, 1)).setSolidColor(context.getColor(R.color.transparent)).intoBackground();
            mTvAnswerA.setTextColor(context.getColor(R.color.white));
            mTvAnswerB.setTextColor(context.getColor(R.color.color_6d4));
            mIvSelectA.setVisibility(View.VISIBLE);
            mIvSelectB.setVisibility(View.GONE);
        } else {
            mRlb.getShapeDrawableBuilder().setStrokeColor(context.getColor(R.color.transparent)).setSolidGradientColors(context.getColor(R.color.color_d6), context.getColor(R.color.color_70)).intoBackground();
            mRla.getShapeDrawableBuilder().setStrokeColor(context.getColor(R.color.color_6d4)).setStrokeWidth(ArmsUtils.dip2px(context, 1)).setSolidColor(context.getColor(R.color.transparent)).intoBackground();
            mTvAnswerB.setTextColor(context.getColor(R.color.white));
            mTvAnswerA.setTextColor(context.getColor(R.color.color_6d4));
            mIvSelectA.setVisibility(View.GONE);
            mIvSelectB.setVisibility(View.VISIBLE);
        }
    }

    public void setAnswer(int questionid,String value) {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setQuestionid(questionid);
        answerEntity.setValue(value);
        answerAdapter.onItemCheckChange(answerEntity);
    }
}
