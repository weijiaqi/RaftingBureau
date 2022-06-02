package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerMoveAwayPlanetaryComponent;
import com.drifting.bureau.mvp.model.entity.AnswerEntity;
import com.drifting.bureau.mvp.ui.adapter.AnswerAdapter;
import com.drifting.bureau.mvp.ui.adapter.manager.CardSwipeLayoutManager;
import com.drifting.bureau.mvp.ui.dialog.AttributeResultsDialog;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.animator.SwipeItemAnimator;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.MoveAwayPlanetaryContract;
import com.drifting.bureau.mvp.presenter.MoveAwayPlanetaryPresenter;

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
public class MoveAwayPlanetaryActivity extends BaseActivity<MoveAwayPlanetaryPresenter> implements MoveAwayPlanetaryContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_answer)
    RecyclerView mRcyAnswer;

    private AnswerAdapter answerAdapter;
    private List<AnswerEntity> list;

    private AttributeResultsDialog attributeResultsDialog;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, MoveAwayPlanetaryActivity.class);
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
        mToolbarTitle.setText("搬离星球");
        initListener();
    }

    public void initListener() {
        CardSwipeLayoutManager cardSwipeLayoutManager = new CardSwipeLayoutManager();
        cardSwipeLayoutManager.initCardConfig(getApplicationContext());
        mRcyAnswer.setLayoutManager(cardSwipeLayoutManager);
        mRcyAnswer.setItemAnimator(new SwipeItemAnimator());
        answerAdapter = new AnswerAdapter(new ArrayList<>());
        mRcyAnswer.setAdapter(answerAdapter);
        answerAdapter.setData(getData());
    }


    public List<AnswerEntity> getData() {
        list = new ArrayList<>();
        list.add(new AnswerEntity("111", 1));
        list.add(new AnswerEntity("222", 1));
        list.add(new AnswerEntity("333", 1));
        list.add(new AnswerEntity("444", 1));
        return list;
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
                            int currentPosition = 0;
                            View itemView = mRcyAnswer.getLayoutManager().findViewByPosition(currentPosition);
                            itemView.setTag(SwipeItemAnimator.SWIPE_REMOVE_LEFT);
                            answerAdapter.remove(currentPosition);
                        } else {
                            attributeResultsDialog = new AttributeResultsDialog(this);
                            attributeResultsDialog.show();
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

    }


}