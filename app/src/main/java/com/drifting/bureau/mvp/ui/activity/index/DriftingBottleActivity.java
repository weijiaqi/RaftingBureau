package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerDriftingBottleComponent;
import com.drifting.bureau.mvp.model.entity.ExploreTimesEntity;
import com.drifting.bureau.mvp.ui.activity.user.DriftingTrackActivity;
import com.drifting.bureau.mvp.ui.fragment.PostDriftingFragment;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.DriftingBottleContract;
import com.drifting.bureau.mvp.presenter.DriftingBottlePresenter;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/12 16:01
 *
 * @author 漂流发布界面
 * module name is DriftingBottleActivity
 */
public class DriftingBottleActivity extends BaseActivity<DriftingBottlePresenter> implements DriftingBottleContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToobarTitle;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    private static String EXTRA_EXPLORE_ID = "extra_explore_id";
    private static String EXTRA_NAME = "extra_name";
    private String title;
    private int explore_id;

    public static void start(Context context, int explore_id, String name, boolean closePage) {
        Intent intent = new Intent(context, DriftingBottleActivity.class);
        intent.putExtra(EXTRA_EXPLORE_ID, explore_id);
        intent.putExtra(EXTRA_NAME, name);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDriftingBottleComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_drifting_bottle; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        if (getIntent() != null) {
            explore_id = getIntent().getExtras().getInt(EXTRA_EXPLORE_ID);
            title = getIntent().getExtras().getString(EXTRA_NAME);
        }
        mToobarTitle.setText(title);
        if (mPresenter != null) {
            mPresenter.exploreTimes(explore_id);
        }

        Fragment fragment = PostDriftingFragment.newInstance(1, explore_id, 0);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fame, fragment).commitAllowingStateLoss();
    }


    @OnClick({R.id.toolbar_back, R.id.tv_slect_trajectory})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_slect_trajectory: //查看轨迹
                    DriftingTrackActivity.start(this, false);
                    break;
            }
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    @Override
    public void onExploreTimesSuccess(ExploreTimesEntity entity) {
        if (entity != null) {
            mTvNum.setText(entity.getExplore_num()+"次");
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
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.create().disDispose();
    }
}