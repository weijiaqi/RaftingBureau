package com.drifting.bureau.mvp.ui.activity.index;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.di.component.DaggerAnswerTestComponent;
import com.drifting.bureau.mvp.contract.MoveAwayPlanetaryContract;
import com.drifting.bureau.mvp.model.entity.QuestionAssessEntity;
import com.drifting.bureau.mvp.model.entity.QuestionEntity;
import com.drifting.bureau.mvp.presenter.MoveAwayPlanetaryPresenter;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.SpannableUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.animator.AnimatorUtil;
import com.drifting.bureau.video.EmptyControlVideo;
import com.jess.arms.di.component.AppComponent;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 答题测试
 * @Author : WeiJiaQI
 * @Time : 2022/9/24 10:11
 */
public class AnswerTestActivity extends BaseManagerActivity<MoveAwayPlanetaryPresenter> implements MoveAwayPlanetaryContract.View {

    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.toolbar_title)
    TextView mToolBarTitle;
    @BindView(R.id.tv_word)
    TextView mTvWord;
    @BindView(R.id.tv_text_selectA)
    TextView mTvTextSelectA;
    @BindView(R.id.tv_text_selectB)
    TextView mTvTextSelectB;
    @BindView(R.id.tv_unlock_progress)
    TextView mTvUnlockProgress;
    @BindView(R.id.video_player)
    EmptyControlVideo mVieoPlayer;
    @BindView(R.id.tv_stage)
    TextView mTvStage;
    @BindView(R.id.ll_stage_toggle)
    LinearLayout mLlStageToggle;
    @BindView(R.id.ll_center)
    LinearLayout mLlCenter;
    @BindView(R.id.iv_pic_selectA)
    ImageView mIvPicSelectA;
    @BindView(R.id.iv_pic_selectB)
    ImageView mIvPicSelectB;
    private SpannableStringBuilder passer, passerStage;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, AnswerTestActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAnswerTestComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_answer_test;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        mToolBarTitle.setText("测试中");
        setUnlockProgress(10, "一");

        mTvWord.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTextSelectA.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTextSelectB.setMovementMethod(ScrollingMovementMethod.getInstance());

//        mVieoPlayer.setUp("https://v.metagatestar.com/mp4/test.mp4", true, "");
//        mVieoPlayer.startPlayLogic();
//
//        mVieoPlayer.setVideoAllCallBack(new GSYSampleCallBack(){
//
//            @Override
//            public void onAutoComplete(String url, Object... objects) {
//                super.onAutoComplete(url, objects);
//                mVieoPlayer.setVisibility(View.GONE);
//                mLlStageToggle.setVisibility(View.VISIBLE);
//            }
//        });

    }


    public void setUnlockProgress(int num, String stage) {
        passer = SpannableUtil.getBuilder(this, num + "").setForegroundColor(R.color.white).setTextSize(14).setBold().append("/80").setForegroundColor(R.color.color_99).setTextSize(12).append(getString(R.string.which_stage, stage)).setForegroundColor(R.color.color_99).setTextSize(11).build();
        mTvUnlockProgress.setText(passer);
    }


    public void setStageToggle(String num, String stage) {
        passerStage = SpannableUtil.getBuilder(this, "已成功解锁").setForegroundColor(R.color.color_cc).setTextSize(12).append("第一阶段 新的文明").setForegroundColor(R.color.white).setTextSize(14).setBold().build();
        mTvUnlockProgress.setText(passerStage);
    }


    @Override
    public void onQuestionListSuccess(List<QuestionEntity> list) {

    }

    @Override
    public void onQuestionAssessSuccess(QuestionAssessEntity entity) {

    }


    @OnClick({R.id.toolbar_back, R.id.iv_answer, R.id.tv_text_selectA, R.id.tv_text_selectB, R.id.fl_answerA, R.id.fl_answerB})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_answer:
                    AnswerResultActivity.start(this, false);
                    break;
                case R.id.tv_text_selectA:
                    setCheckText(1);
                    break;
                case R.id.tv_text_selectB:
                    setCheckText(2);
                    break;
                case R.id.fl_answerA:  //图片A
                    setCheckPic(1);
                    break;
                case R.id.fl_answerB://图片B
                    setCheckPic(2);
                    break;
            }
        }
    }

    //设置文字选中
    public void setCheckText(int status) {
        mTvTextSelectA.setBackground(status == 1 ? getResources().getDrawable(R.drawable.answer_test_select_y) : getResources().getDrawable(R.drawable.answer_test_select_n));
        mTvTextSelectB.setBackground(status == 1 ? getResources().getDrawable(R.drawable.answer_test_select_n) : getResources().getDrawable(R.drawable.answer_test_select_y));
    }

    //设置图片选中
    public void setCheckPic(int status){
        mIvPicSelectA.setVisibility(status == 1 ?View.VISIBLE:View.GONE);
        mIvPicSelectB.setVisibility(status == 1 ?View.GONE:View.VISIBLE);
    }

    /**
     * 开启翻转动画
     */
    public void startStageAnimator() {
        AnimatorUtil.start3DRotateAnimator(mLlCenter, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                // 开启硬件加速
                mLlCenter.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // 关闭硬件加速
                mLlCenter.setLayerType(View.LAYER_TYPE_NONE, null);
                // 这里做页面翻转后的操作
                setUnlockProgress(20, "二");
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }


    @Override
    public void onNetError() {

    }

    @Override
    public Activity getActivity() {
        return null;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }


    @Override
    protected void onPause() {
        super.onPause();
        mVieoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVieoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }
}
