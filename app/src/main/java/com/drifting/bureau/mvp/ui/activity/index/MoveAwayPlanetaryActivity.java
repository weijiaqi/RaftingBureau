package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.animator.SwipeItemAnimator;
import com.drifting.bureau.base.BaseManagerActivity;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

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
    private StringBuilder builder1, builder2;
    private List<AnswerEntity> infos;
    private static String EXTRA_TYPE = "extra_type";
    private int type;

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
        if (getIntent() != null) {
            type = getIntent().getIntExtra(EXTRA_TYPE, 0);
        }
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
            builder1 = new StringBuilder();
            builder2 = new StringBuilder();
            if (!list.isEmpty()) {
                builder1.append(list.get(0).getQuestionid());
                builder2.append(list.get(0).getValue());
                for (int i = 1, n = list.size(); i < n; i++) {
                    builder1.append(",").append(list.get(i).getQuestionid());
                    builder2.append(",").append(list.get(i).getValue());
                }
            }
        });
        mRcyAnswer.setAdapter(answerAdapter);
        if (mPresenter != null) {
            mPresenter.questionlist();
        }
    }

    @Override
    public void onQuestionListSuccess(List<QuestionEntity> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setPostion(i + 1);
            }
            answerAdapter.setData(list);
        }
    }

    @Override
    public void onQuestionAssessSuccess(QuestionAssessEntity entity) {
        if (entity != null) {
            attributeResultsDialog = new AttributeResultsDialog(this, entity);
            attributeResultsDialog.show();
            attributeResultsDialog.setOnClickCallback(status -> {
                if (status == AttributeResultsDialog.SELECT_FINISH) {
                    if (type == 1) {
                        AnswerCompletedEvent answerCompletedEvent=new AnswerCompletedEvent();
                        answerCompletedEvent.setPl_id(entity.getPlanet().getPl_id());
                        EventBus.getDefault().post(answerCompletedEvent);
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
                        if (mRcyAnswer.getChildCount() != 1) {
                            if (infos != null && infos.size() == 10 - answerAdapter.getItemCount() + 1) {
                                int currentPosition = 0;
                                View itemView = mRcyAnswer.getLayoutManager().findViewByPosition(currentPosition);
                                itemView.setTag(SwipeItemAnimator.SWIPE_REMOVE_LEFT);
                                answerAdapter.remove(currentPosition);
                            } else {
                                showMessage("请进行选择!");
                            }
                        } else {
                            if (builder1 != null && builder2 != null) {
                                if (mPresenter != null) {
                                    mPresenter.questionassess(builder1.toString(), builder2.toString());
                                }
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