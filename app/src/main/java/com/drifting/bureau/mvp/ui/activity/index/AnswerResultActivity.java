package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.data.entity.AnswerColorEntiy;
import com.drifting.bureau.di.component.DaggerAnswerResultComponent;
import com.drifting.bureau.di.component.DaggerAnswerTestComponent;
import com.drifting.bureau.mvp.contract.MoveAwayPlanetaryContract;
import com.drifting.bureau.mvp.model.entity.QuestionAssessEntity;
import com.drifting.bureau.mvp.model.entity.QuestionEntity;
import com.drifting.bureau.mvp.model.entity.QuestionStagesEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.presenter.MoveAwayPlanetaryPresenter;
import com.drifting.bureau.mvp.ui.activity.unity.ARMetaverseCenterActivity;
import com.drifting.bureau.mvp.ui.activity.user.AboutMeActivity;
import com.drifting.bureau.mvp.ui.activity.user.NewAboutMeActivity;
import com.drifting.bureau.mvp.ui.adapter.CharacterTraitsAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.SpannableUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.drifting.bureau.view.radar.RadarItem;
import com.drifting.bureau.view.radar.RadarView;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 卫佳琪1
 * @description 答题结果
 * @time 16:41 16:41
 */

public class AnswerResultActivity extends BaseManagerActivity<MoveAwayPlanetaryPresenter> implements MoveAwayPlanetaryContract.View {
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
    @BindView(R.id.radarview)
    RadarView radarView;
    @BindView(R.id.tv_faction)
    TextView mTvFaction;
    @BindView(R.id.tv_nikename)
    TextView mTvNikeName;
    @BindView(R.id.iv_career_image)
    ImageView mIvCareerImage;
    @BindView(R.id.tv_key_words)
    TextView mTvKeyWords;
    private CharacterTraitsAdapter characterTraitsAdapter;
    private SpannableStringBuilder passerFit, passerAttributes, passerConflict, passerConflictAttributes, passerPersonNum;

    private List<AnswerColorEntiy> list;
    private List<RadarItem> radarItemList;

    private static final String INTENT_TYPE = "intent_type";
    private int mType = -1;

    public static void start(Context context, int type, boolean closePage) {
        Intent intent = new Intent(context, AnswerResultActivity.class);
        intent.putExtra(INTENT_TYPE, type);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAnswerResultComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_answer_result;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        mType = getInt(INTENT_TYPE);
        mToolBarTitle.setText("结果分析");

        list = new ArrayList<>();

        list.add(new AnswerColorEntiy(2, "温和派", R.color.color_d5));
        list.add(new AnswerColorEntiy(3, "严谨派", R.color.color_62));
        list.add(new AnswerColorEntiy(4, "博爱派", R.color.color_ee9));
        list.add(new AnswerColorEntiy(5, "研究派", R.color.color_63));
        list.add(new AnswerColorEntiy(6, "活跃派", R.color.color_c3));
        list.add(new AnswerColorEntiy(7, "无畏派", R.color.color_ff00));
        list.add(new AnswerColorEntiy(8, "知识派", R.color.color_e95));
        list.add(new AnswerColorEntiy(9, "哲理派", R.color.color_a00));
        list.add(new AnswerColorEntiy(10, "创意派", R.color.color_b3));
        list.add(new AnswerColorEntiy(11, "探索派", R.color.color_50a));
        list.add(new AnswerColorEntiy(12, "领袖派", R.color.color_3a));
        list.add(new AnswerColorEntiy(13, "掌控派", R.color.color_971));
        list.add(new AnswerColorEntiy(14, "守序派", R.color.color_7f));
        list.add(new AnswerColorEntiy(15, "谋略派", R.color.color_06a));
        list.add(new AnswerColorEntiy(16, "沟通派", R.color.color_c1));
        list.add(new AnswerColorEntiy(17, "教导派", R.color.color_85d));
        if (mPresenter != null) {
            mPresenter.assessResult();
        }
    }

    public List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add("严谨派的人总是一丝不苟，他们极度严谨，极度自律，同时也极度可靠。");
        list.add("严谨派往往比较传统、保守，他们在工作场所会带来严肃和保守的气氛。这有时会使其他类型的人感到这样的工作环境很无趣。");
        list.add("他们具有很强的条理性和准确性，对于细节有很强的记忆和判断。");
        return list;
    }

    @Override
    public void onQuestionListSuccess(List<QuestionEntity> list) {

    }

    @Override
    public void onQuestionAssessSuccess(QuestionAssessEntity entity) {

    }

    @Override
    public void onAssessResultSuccess(QuestionAssessEntity entity) {
        if (entity != null) {
            mTvNikeName.setText("昵称：" + entity.getUser_name());
            mTvFaction.setText(entity.getPlanet().getName());

            Iterator<AnswerColorEntiy> iterator = list.iterator();
            while (iterator.hasNext()) {
                AnswerColorEntiy next = iterator.next();
                if (next.getPl_id() == entity.getPlanet().getPl_id()) {
                    mTvFaction.setTextColor(getResources().getColor(next.getColor()));
                }
                if (next.getPl_id() == entity.getPlanet().getAgree_id()) {
                    passerFit = SpannableUtil.getBuilder(this, "契合派系: ").setForegroundColor(R.color.color_cc).setTextSize(11).append(entity.getPlanet().getAgree_with()).setForegroundColor(next.getColor()).setTextSize(14).setBold().build();
                    mTvFit.setText(passerFit);
                }
                if (next.getPl_id() == entity.getPlanet().getConflict_id()) {
                    passerConflict = SpannableUtil.getBuilder(this, "冲突派系: ").setForegroundColor(R.color.color_cc).setTextSize(11).append(entity.getPlanet().getConflict_with()).setForegroundColor(next.getColor()).setTextSize(14).setBold().build();
                    mTvConflict.setText(passerConflict);
                }
            }

            passerAttributes = SpannableUtil.getBuilder(this, "契合属性: ").setForegroundColor(R.color.color_cc).setTextSize(11).append(entity.getPlanet().getAgree_attr()).setForegroundColor(R.color.white).setTextSize(11).setBold().build();
            mTvAttributes.setText(passerAttributes);
            passerConflictAttributes = SpannableUtil.getBuilder(this, "冲突属性: ").setForegroundColor(R.color.color_cc).setTextSize(11).append(entity.getPlanet().getConflict_attr()).setForegroundColor(R.color.white).setTextSize(11).setBold().build();
            mTvConflictAttributes.setText(passerConflictAttributes);
            passerPersonNum = SpannableUtil.getBuilder(this, "当前派别共有 ").setForegroundColor(R.color.color_66).setTextSize(12).append(entity.getPlanet().getPeople() + "").setForegroundColor(R.color.color_223d).setTextSize(19).setBold().append(" 人").setForegroundColor(R.color.color_66).setTextSize(12).build();
            mTvPersonNum.setText(passerPersonNum);


            List<Integer> list = new LinkedList<>();
            list.add(entity.getE());
            list.add(entity.getI());
            list.add(entity.getS());
            list.add(entity.getN());
            list.add(entity.getT());
            list.add(entity.getF());
            list.add(entity.getJ());
            list.add(entity.getP());
            Integer max = Collections.max(list) + 2;
            radarItemList = new ArrayList<>();
            radarItemList.add(new RadarItem("火焰", entity.getE(), entity.getE() != 0 ? (float) entity.getE() / (float) max : 0));
            radarItemList.add(new RadarItem("土壤", entity.getI(), entity.getI() != 0 ? (float) entity.getI() / (float) max : 0));
            radarItemList.add(new RadarItem("能量", entity.getS(), entity.getS() != 0 ? (float) entity.getS() / (float) max : 0));
            radarItemList.add(new RadarItem("动物", entity.getN(), entity.getN() != 0 ? (float) entity.getN() / (float) max : 0));
            radarItemList.add(new RadarItem("天空", entity.getT(), entity.getT() != 0 ? (float) entity.getT() / (float) max : 0));
            radarItemList.add(new RadarItem("大海", entity.getF(), entity.getF() != 0 ? (float) entity.getF() / (float) max : 0));
            radarItemList.add(new RadarItem("风", entity.getJ(), entity.getJ() != 0 ? (float) entity.getJ() / (float) max : 0));
            radarItemList.add(new RadarItem("温度", entity.getP(), entity.getP() != 0 ? (float) entity.getP() / (float) max : 0));
            radarView.setRadarItemList(radarItemList);


            //用逗号将字符串分开，得到字符串数组
            String[] strs = entity.getPlanet().getDisposition().split("\n");
            //将字符串数组转换成集合
            List<String> stringList = Arrays.asList(strs);
            mRcyList.setLayoutManager(new LinearLayoutManager(this));
            characterTraitsAdapter = new CharacterTraitsAdapter(new ArrayList<>());
            mRcyList.setAdapter(characterTraitsAdapter);
            characterTraitsAdapter.setData(stringList);

            mTvKeyWords.setText(entity.getPlanet().getKey_words());
            GlideUtil.create().loadLongImage(this, entity.getPlanet().getCareer_image(), mIvCareerImage);

        }
    }

    @Override
    public void onQuestionStagesSuccess(List<QuestionStagesEntity> list) {

    }

    public void showLoading() {
        ViewUtil.create().show(this);
    }

    public void hideLoading() {
        ViewUtil.create().dismiss();
    }



    @OnClick({R.id.toolbar_back, R.id.iv_enter_prime})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finshActivity();
                    break;
                case R.id.iv_enter_prime:
                    NewAboutMeActivity.start(this, false);
                    break;
            }
        }
    }

    /**
     * 注意:
     * super.onBackPressed()会自动调用finish()方法,关闭当前Activity.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finshActivity();
    }


    public void finshActivity(){
        if (mType == 2) {
            ARMetaverseCenterActivity.start(this, true);
        } else {
            finish();
        }
    }

    @Override
    public void onNetError() {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }
}
