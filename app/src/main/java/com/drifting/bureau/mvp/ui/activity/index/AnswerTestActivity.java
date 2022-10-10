package com.drifting.bureau.mvp.ui.activity.index;


import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.data.event.AnswerCompletedEvent;
import com.drifting.bureau.di.component.DaggerAnswerTestComponent;
import com.drifting.bureau.mvp.contract.MoveAwayPlanetaryContract;
import com.drifting.bureau.mvp.model.entity.QuestionAssessEntity;
import com.drifting.bureau.mvp.model.entity.QuestionEntity;
import com.drifting.bureau.mvp.model.entity.QuestionStagesEntity;
import com.drifting.bureau.mvp.presenter.MoveAwayPlanetaryPresenter;
import com.drifting.bureau.mvp.ui.dialog.ExitPsychologyDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.SpannableUtil;
import com.drifting.bureau.util.ViewUtil;
import com.drifting.bureau.util.animator.AnimatorUtil;
import com.drifting.bureau.video.EmptyControlVideo;
import com.jess.arms.di.component.AppComponent;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.ll_word)
    LinearLayout mLlWord;
    @BindView(R.id.rl_pic)
    RelativeLayout mRlPic;
    @BindView(R.id.iv_answerA)
    ImageView mIvAnswerA;
    @BindView(R.id.iv_answerB)
    ImageView mIvAnswerB;
    @BindView(R.id.rl_answer)
    RelativeLayout mRlAnswer;
    @BindView(R.id.tv_completeness)
    TextView mTvCompleteness;
    @BindView(R.id.iv_answer)
    ImageView mIvAnswer;
    @BindView(R.id.tv_progress)
    TextView mTvProgress;
    private static final char[] data = new char[]{'一', '二', '三', '四', '五',
            '六', '七', '八','九'};
    private SpannableStringBuilder passer, passerStage;
    private List<QuestionEntity> questionEntityList;
    private List<QuestionStagesEntity> questionStagesEntityList;
    private ExitPsychologyDialog exitPsychologyDialog;
    private int currency = 0;

    private Map<String, String> map;
    private int total;

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
        mTvWord.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTextSelectA.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTextSelectB.setMovementMethod(ScrollingMovementMethod.getInstance());


        if (mPresenter != null) {
            mPresenter.questionStages();
        }

    }


    public void setUnlockProgress(int num, String stage) {
        passer = SpannableUtil.getBuilder(this, num + "").setForegroundColor(R.color.white).setTextSize(14).setBold().append("/" + total).setForegroundColor(R.color.color_99).setTextSize(12).append(getString(R.string.which_stage, stage)).setForegroundColor(R.color.color_99).setTextSize(11).build();
        mTvUnlockProgress.setText(passer);
    }


    public void setStageToggle(String num, String stage) {
        passerStage = SpannableUtil.getBuilder(this, "已成功解锁").setForegroundColor(R.color.color_cc).setTextSize(12).append(getString(R.string.which_stage_num, num) + stage).setForegroundColor(R.color.white).setTextSize(14).setBold().build();
        mTvStage.setText(passerStage);
    }


    @Override
    public void onQuestionListSuccess(List<QuestionEntity> list) {
        if (list != null && list.size() > 0) {
            questionEntityList = new ArrayList<>();
            map = Preferences.getHashMapData();
            if (map != null) {
                List<QuestionEntity> list1 = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (!map.containsKey(list.get(i).getQuestion_id() + "")) {
                        questionEntityList.add(list.get(i));
                    } else {
                        list1.add(list.get(i));
                    }
                }
                questionEntityList.addAll(0, list1);
            } else {
                map = new HashMap<>();
                questionEntityList = list;
            }
            currency = map.size();
            total = questionEntityList.size();
            if (currency == 0) {
                showVideo();
            } else {
                mTvProgress.setText((int) (((float) currency / total) * 100) + "%");
                setQuestion(true, 2);
            }

        }
    }

    //题绑定
    public void setQuestion(boolean status, int type) {

        if (type == 1) {
            mIvAnswer.setVisibility(View.VISIBLE);
        } else {
            mIvAnswer.setVisibility(View.GONE);
        }

        setVaule();
        mTvWord.setText(questionEntityList.get(currency).getQuestion());
        mLlWord.setVisibility(questionEntityList.get(currency).getQ_type() == 0 ? View.VISIBLE : View.GONE);
        mRlPic.setVisibility(questionEntityList.get(currency).getQ_type() == 0 ? View.GONE : View.VISIBLE);

        if (questionEntityList.get(currency).getQ_type() == 0) {
            if (status) {
                mTvTextSelectA.setBackground(getResources().getDrawable(R.drawable.answer_test_select_n));
                mTvTextSelectB.setBackground(getResources().getDrawable(R.drawable.answer_test_select_n));
            }
            mTvTextSelectA.setText(questionEntityList.get(currency).getA());
            mTvTextSelectB.setText(questionEntityList.get(currency).getB());
        } else {
            mIvPicSelectA.setVisibility(View.GONE);
            mIvPicSelectB.setVisibility(View.GONE);
            if (status) {
                GlideUtil.create().loadCornersPic(this, questionEntityList.get(currency).getA(), mIvAnswerA);
                GlideUtil.create().loadCornersPic(this, questionEntityList.get(currency).getB(), mIvAnswerB);
            }
        }

    }


    //设置第几阶段
    public void setVaule() {
        int value = (int) currency / 11;
        mTvCompleteness.setText("完成度");
        setUnlockProgress(currency + 1, String.valueOf(data[value]));
    }

    @Override
    public void onQuestionAssessSuccess(QuestionAssessEntity entity) {
        if (entity != null) {
            Preferences.putHashMapData(null);
            AnswerCompletedEvent answerCompletedEvent = new AnswerCompletedEvent();
            answerCompletedEvent.setPl_id(entity.getPlanet().getPl_id());
            EventBus.getDefault().post(answerCompletedEvent);


            AnswerResultActivity.start(this, 1,true);
        }

    }

    @Override
    public void onAssessResultSuccess(QuestionAssessEntity list) {

    }

    @Override
    public void onQuestionStagesSuccess(List<QuestionStagesEntity> entity) {
        if (entity != null && entity.size() > 0) {
            questionStagesEntityList = entity;
            mPresenter.questionlist();
        }
    }


    @OnClick({R.id.toolbar_back, R.id.iv_answer, R.id.tv_text_selectA, R.id.tv_text_selectB, R.id.fl_answerA, R.id.fl_answerB, R.id.tv_exit, R.id.tv_continue})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                case R.id.tv_exit:
                    exitPsychologyDialog = new ExitPsychologyDialog(this);
                    exitPsychologyDialog.show();
                    exitPsychologyDialog.setOnClickCallback(type -> {
                        if (type == ExitPsychologyDialog.SELECT_FINISH) {
                            finish();
                        }
                    });
                    break;
                case R.id.iv_answer:
                    currency--;
                    setQuestion(false, 2);
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
                case R.id.tv_continue:  //继续
                    mLlStageToggle.setVisibility(View.GONE);
                    mRlAnswer.setVisibility(View.VISIBLE);
                    mIvAnswer.setImageResource(R.drawable.back_question);
                    mIvAnswer.setClickable(true);
                    if (currency != 0) {
                        currency++;
                    }
                    setQuestion(true, 2);

                    break;
            }
        }
    }

    //设置文字选中
    public void setCheckText(int status) {
        if (status == 1) {
            mTvTextSelectA.setBackground(getResources().getDrawable(R.drawable.answer_test_select_y));
        } else {
            mTvTextSelectB.setBackground(getResources().getDrawable(R.drawable.answer_test_select_y));
        }
        isShowVideo(status);
    }

    //设置图片选中
    public void setCheckPic(int status) {
        mIvPicSelectA.setVisibility(status == 1 ? View.VISIBLE : View.GONE);
        mIvPicSelectB.setVisibility(status == 1 ? View.GONE : View.VISIBLE);
        isShowVideo(status);
    }

    //判断是播放视频还是继续答题
    public void isShowVideo(int status) {
        mTvProgress.setText((int) (((float) (currency + 1) / total) * 100) + "%");
        new Handler().postDelayed(() -> {
            map.put(questionEntityList.get(currency).getQuestion_id() + "", status == 1 ? "A" : "B");
            Preferences.putHashMapData(map);

            if (map.size() == total) {  //答完
                showMessage("答题完成");
                if (mPresenter != null) {
                    mPresenter.questionassess(map);
                }
            } else {
                if ((currency + 1) % 11 == 0) {
                    showVideo();
                } else {
                    mIvAnswer.setImageResource(R.drawable.back_question);
                    mIvAnswer.setClickable(true);
                    currency++;
                    setQuestion(true, 1);
                }
            }
        }, 500);
    }


    //视频播放展示
    public void showVideo() {

        if (currency != 0) {
            startStageAnimator();
        } else {
            setBottomText();
        }

        mIvAnswer.setImageResource(R.drawable.unlocked);
        mIvAnswer.setClickable(false);

        mRlAnswer.setVisibility(View.GONE);
        mVieoPlayer.setVisibility(View.VISIBLE);
        mVieoPlayer.setUp(questionStagesEntityList.get(getPostion()).getUrl(), true, "");
        mVieoPlayer.startPlayLogic();

        mVieoPlayer.setVideoAllCallBack(new GSYSampleCallBack() {

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
                mVieoPlayer.setVisibility(View.GONE);
                mLlStageToggle.setVisibility(View.VISIBLE);
                setStageToggle(getVaule(), questionStagesEntityList.get(getPostion()).getSubheading());
            }
        });
    }


    //获取当前阶段
    public String getVaule() {
        return String.valueOf(data[getPostion()]);
    }

    public int getPostion(){
        int value = (int) (currency + 1) / 11;
        return value;
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
                setBottomText();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    //设置底部进度条文字
    public void setBottomText() {
        mTvCompleteness.setText(getString(R.string.which_stage_num, getVaule()));
        mTvUnlockProgress.setText(questionStagesEntityList.get(getPostion()).getSubheading());
    }

    public void showLoading() {
        ViewUtil.create().show(this);
    }

    public void hideLoading() {
        ViewUtil.create().dismiss();
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
