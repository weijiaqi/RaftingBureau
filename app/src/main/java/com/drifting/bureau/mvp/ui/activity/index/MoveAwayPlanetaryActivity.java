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
        list.add(new AnswerEntity("大多数时候，你更喜欢","随心所欲的做事","做出详细的计划", 1,1));
        list.add(new AnswerEntity("哪一个是你更喜欢的", "","", 2,2));
        list.add(new AnswerEntity("你做事会被情感影响", "是的","不会", 1,3));
        list.add(new AnswerEntity("对猎奇，和最新出现的潮流事物很快掌握", "是的","不是", 1,4));
        list.add(new AnswerEntity("很多时候你宁愿自己看一场电影也不愿意参加聚会", "是的","我喜欢参加聚会", 1,5));
        list.add(new AnswerEntity("你喜欢远离外界的喧嚣", "是的","更喜欢热闹一点", 1,6));
        list.add(new AnswerEntity("你经常考虑是否有外星人", "是的","不是", 1,7));
        list.add(new AnswerEntity("解决问题时，你更喜欢熟悉的方法解决", "是的","不是", 1,8));
        list.add(new AnswerEntity("你比较相信科学", "是的","不是", 1,9));
        list.add(new AnswerEntity("你更喜欢传统的生活方式", "是的","不是", 1,10));

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