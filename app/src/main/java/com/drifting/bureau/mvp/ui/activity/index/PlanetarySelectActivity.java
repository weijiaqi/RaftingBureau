package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.AnswerCompletedEvent;
import com.drifting.bureau.di.component.DaggerPlanetarySelectComponent;
import com.drifting.bureau.mvp.contract.PlanetarySelectContract;
import com.drifting.bureau.mvp.presenter.PlanetarySelectPresenter;
import com.drifting.bureau.mvp.ui.fragment.PlanetaryDisFragment;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.animator.AnimatorUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.base.BaseManagerActivity;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/01 11:04
 *
 * @author 星球分布
 * module name is PlanetarySelectActivity
 */
public class PlanetarySelectActivity extends BaseManagerActivity<PlanetarySelectPresenter> implements PlanetarySelectContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.scroll_view)
    ScrollView mRootScrollView;
    @BindView(R.id.iv_rocket)
    ImageView mIvRocket;
    @BindView(R.id.tv_seek)
    TextView mTvSeek;
    @BindView(R.id.iv_open_search)
    ImageView mIvOpenSearch;
    @BindView(R.id.tv_three_day)
    TextView mTvThreeDay;
    @BindView(R.id.ll_move_away)
    LinearLayout mLlMoveAway;
    private static String EXTRA_POSTION = "extra_postion";
    private int postion;
//    private int assess_after, assess_status;

    public static void start(Context context, int postion, boolean closePage) {
        Intent intent = new Intent(context, PlanetarySelectActivity.class);
        intent.putExtra(EXTRA_POSTION, postion);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPlanetarySelectComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_planetary_select; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        mToolbarTitle.setText("派系星球分布");
        if (getIntent() != null) {
            postion = getIntent().getIntExtra(EXTRA_POSTION, 0);
        }
        initListener();
    }

    public void initListener() {

//        RequestUtil.create().planetlocation(entity -> {
//            if (entity != null && entity.getCode() == 200) {
//                if (entity.getData().getShow() == 0) {  //不显示
//                    mLlMoveAway.setVisibility(View.GONE);
//                } else { //显示
//                    mLlMoveAway.setVisibility(View.VISIBLE);
//                    assess_after = entity.getData().getAssess_after();
//                    assess_status = entity.getData().getAssess_status();
//                    if (assess_status == 1) {//可以答题
//                        mTvSeek.setText("可探寻星球");
//                        mIvOpenSearch.setVisibility(View.VISIBLE);
//                        mTvThreeDay.setVisibility(View.GONE);
//                    } else {
//                        mTvSeek.setText("探寻星球中...");
//                        mTvThreeDay.setVisibility(View.VISIBLE);
//                        mTvThreeDay.setText(getString(R.string.three_day, assess_after + ""));
//                        mIvOpenSearch.setVisibility(View.GONE);
//                        statScaleAnim(mTvSeek);
//                        statFloatAnim(mIvRocket);
//                    }
//                }
//            }
//        });

        Fragment fragment = PlanetaryDisFragment.newInstance(postion);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fame, fragment).commitAllowingStateLoss();
        //设置默认滚动到顶部
        mRootScrollView.post(() -> {
            // TODO Auto-generated method stub
            if (postion == 1 || postion == 9 || postion == 3 || postion == 8 || postion == 2 || postion == 6) {
                mRootScrollView.fullScroll(ScrollView.FOCUS_UP);
            } else if (postion == 11 || postion == 17 || postion == 10 || postion == 12 || postion == 7) {
                mRootScrollView.scrollTo(0, mRootScrollView.getBottom() / 3);
            } else {
                mRootScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }

    public void statScaleAnim(View view) {
        AnimatorUtil.AlphaAnim(view, 3000);
    }

    public void statFloatAnim(View view) {
        AnimatorUtil.floatAnim(view, 1000, 3);
    }


    public Activity getActivity() {
        return this;
    }


    @OnClick({R.id.toolbar_back, R.id.ll_move_away})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.ll_move_away:
                    MoveAwayPlanetaryActivity.start(PlanetarySelectActivity.this, 1, false);
                    break;
            }
        }
    }


    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void AnswerCompletedEvent(AnswerCompletedEvent answerCompletedEvent) {
        if (answerCompletedEvent != null) {
            postion = answerCompletedEvent.getPl_id();
            initListener();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.create().disDispose();
    }
}