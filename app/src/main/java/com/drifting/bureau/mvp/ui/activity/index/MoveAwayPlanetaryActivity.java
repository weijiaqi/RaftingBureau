package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.AnswerCompletedEvent;
import com.drifting.bureau.di.component.DaggerMoveAwayPlanetaryComponent;
import com.drifting.bureau.mvp.contract.MoveAwayPlanetaryContract;
import com.drifting.bureau.mvp.model.entity.AnswerEntity;
import com.drifting.bureau.mvp.model.entity.QuestionAssessEntity;
import com.drifting.bureau.mvp.model.entity.QuestionEntity;
import com.drifting.bureau.mvp.presenter.MoveAwayPlanetaryPresenter;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;
import com.drifting.bureau.mvp.ui.adapter.AnswerAdapter;
import com.drifting.bureau.mvp.ui.adapter.manager.CardSwipeLayoutManager;
import com.drifting.bureau.mvp.ui.dialog.AttributeResultsDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.animator.SwipeItemAnimator;
import com.drifting.bureau.base.BaseManagerActivity;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/01 15:22
 *
 * @author 搬离星球
 * module name is MoveAwayPlanetaryActivity
 */
public class MoveAwayPlanetaryActivity extends BaseManagerActivity<MoveAwayPlanetaryPresenter> implements MoveAwayPlanetaryContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_answer)
    RecyclerView mRcyAnswer;
    private AnswerAdapter answerAdapter;
    private AttributeResultsDialog attributeResultsDialog;

    private List<AnswerEntity> infos;
    private static String EXTRA_TYPE = "extra_type";
    private int type;

    private List<QuestionEntity> questionEntityList;
    private Map<String, String> map;
    private int total;
    public static void start(Context context, int type, boolean closePage) {
        Intent intent = new Intent(context, MoveAwayPlanetaryActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMoveAwayPlanetaryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_move_away_planetary; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        type = getInt(EXTRA_TYPE);
        mToolbarTitle.setText(type == 1 ? "搬离星球" : "完善星球属性");
        initListener();
    }

    public void initListener() {
        CardSwipeLayoutManager cardSwipeLayoutManager = new CardSwipeLayoutManager();
        cardSwipeLayoutManager.initCardConfig(getApplicationContext());
        mRcyAnswer.setLayoutManager(cardSwipeLayoutManager);
        mRcyAnswer.setItemAnimator(new SwipeItemAnimator());
        answerAdapter = new AnswerAdapter(new ArrayList<>(), list -> {
            infos = list;
        });
        mRcyAnswer.setAdapter(answerAdapter);
        if (mPresenter != null) {
            mPresenter.questionlist();
        }
    }

    @Override
    public void onQuestionListSuccess(List<QuestionEntity> list) {
        if (list != null && list.size() > 0) {
            questionEntityList = new ArrayList<>();
            map = Preferences.getHashMapData();
            if (map != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (!map.containsKey(list.get(i).getQuestion_id() + "")) {
                        questionEntityList.add(list.get(i));
                    }
                }
            } else {
                map = new HashMap<>();
                questionEntityList = list;
            }
            for (int i = 0; i < questionEntityList.size(); i++) {
                if (map != null) {
                    questionEntityList.get(i).setPostion(map.size() + i + 1);
                } else {
                    questionEntityList.get(i).setPostion(i + 1);
                }
            }
            total = questionEntityList.size();
            answerAdapter.setData(questionEntityList);
        }
    }

    @Override
    public void onQuestionAssessSuccess(QuestionAssessEntity entity) {
        if (entity != null) {
            Preferences.putHashMapData(null);
            AnswerCompletedEvent answerCompletedEvent=new AnswerCompletedEvent();
            answerCompletedEvent.setPl_id(entity.getPlanet().getPl_id());
            EventBus.getDefault().post(answerCompletedEvent);

            attributeResultsDialog = new AttributeResultsDialog(this, entity);
            attributeResultsDialog.show();
            attributeResultsDialog.setOnClickCallback(status -> {
                if (status == AttributeResultsDialog.SELECT_FINISH) {
                    if (type == 1) {
                        finish();
                    } else {
                        DiscoveryTourActivity.start(this, true);
                    }
                }
            });
        }
    }

    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.toolbar_back, R.id.tv_next})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_next:
                    if (mRcyAnswer != null && mRcyAnswer.getChildCount() > 0) {
                        if (infos != null) {
                            map.put(infos.get(infos.size() - 1).getQuestionid() + "", infos.get(infos.size() - 1).getValue());
                            Preferences.putHashMapData(map);
                        }
                        if (answerAdapter.getItemCount() != 1) {
                            if (infos!=null&&infos.size() == total - answerAdapter.getItemCount() + 1) {
                                int currentPosition = 0;
                                View itemView = mRcyAnswer.getLayoutManager().findViewByPosition(currentPosition);
                                itemView.setTag(SwipeItemAnimator.SWIPE_REMOVE_LEFT);
                                answerAdapter.remove(currentPosition);
                            } else {
                                showMessage("请进行选择!");
                            }
                        } else {
                            if (mPresenter != null) {
                                mPresenter.questionassess(map);
                            }
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }


}