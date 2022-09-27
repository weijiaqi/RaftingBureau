package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.mvp.ui.adapter.CharacterTraitsAdapter;
import com.drifting.bureau.util.SpannableUtil;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 卫佳琪1
 * @description 答题结果
 * @time 16:41 16:41
 */

public class AnswerResultActivity extends BaseManagerActivity {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.toolbar_title)
    TextView mToolBarTitle;
    @BindView(R.id.tv_fit)
    TextView mTvFit;
    @BindView(R.id.tv_attributes)
    TextView mTvAttributes;
    @BindView(R.id.tv_person_num)
    TextView mTvPersonNum;
    @BindView(R.id.tv_conflict)
    TextView mTvConflict;
    @BindView(R.id.tv_conflict_attributes)
    TextView mTvConflictAttributes;
    @BindView(R.id.rcy_list)
    RecyclerView mRcyList;
    private CharacterTraitsAdapter characterTraitsAdapter;
    private SpannableStringBuilder passerFit, passerAttributes, passerConflict, passerConflictAttributes, passerPersonNum;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, AnswerResultActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_answer_result;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        mToolBarTitle.setText("结果分析");
        passerFit = SpannableUtil.getBuilder(this, "契合派系: ").setForegroundColor(R.color.color_cc).setTextSize(11).append("谋略派").setForegroundColor(R.color.color_15ae).setTextSize(14).setBold().build();
        mTvFit.setText(passerFit);
        passerAttributes = SpannableUtil.getBuilder(this, "契合属性: ").setForegroundColor(R.color.color_cc).setTextSize(11).append("组织管理；客观公正；责任感。").setForegroundColor(R.color.white).setTextSize(11).setBold().build();
        mTvAttributes.setText(passerAttributes);

        passerConflict = SpannableUtil.getBuilder(this, "冲突派系: ").setForegroundColor(R.color.color_cc).setTextSize(11).append("活跃派").setForegroundColor(R.color.color_eea).setTextSize(14).setBold().build();
        mTvConflict.setText(passerConflict);
        passerConflictAttributes = SpannableUtil.getBuilder(this, "冲突属性: ").setForegroundColor(R.color.color_cc).setTextSize(11).append("完美主义与追求舒适的冲突。").setForegroundColor(R.color.white).setTextSize(11).setBold().build();
        mTvConflictAttributes.setText(passerConflictAttributes);

        passerPersonNum = SpannableUtil.getBuilder(this, "当前派别共有").setForegroundColor(R.color.color_66).setTextSize(12).append("158254").setForegroundColor(R.color.color_223d).setTextSize(19).setBold().append("人").setForegroundColor(R.color.color_66).setTextSize(12).build();
        mTvPersonNum.setText(passerPersonNum);


        mRcyList.setLayoutManager(new LinearLayoutManager(this));


        characterTraitsAdapter = new CharacterTraitsAdapter(new ArrayList<>());
        mRcyList.setAdapter(characterTraitsAdapter);
        characterTraitsAdapter.setData(getList());
    }

    public List<String> getList() {

        List<String> list = new ArrayList<>();
        list.add("严谨派的人总是一丝不苟，他们极度严谨，极度自律，同时也极度可靠。");
        list.add("严谨派往往比较传统、保守，他们在工作场所会带来严肃和保守的气氛。这有时会使其他类型的人感到这样的工作环境很无趣。");
        list.add("他们具有很强的条理性和准确性，对于细节有很强的记忆和判断。");

        return list;
    }
}
