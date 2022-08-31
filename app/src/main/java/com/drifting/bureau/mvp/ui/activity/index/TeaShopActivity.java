package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.drifting.bureau.R;
<<<<<<< HEAD
import com.drifting.bureau.base.BaseManagerActivity;
=======
>>>>>>> origin/dev
import com.drifting.bureau.di.component.DaggerTeaShopComponent;
import com.drifting.bureau.mvp.contract.TeaShopContract;
import com.drifting.bureau.mvp.model.entity.TeaShopEntity;
import com.drifting.bureau.mvp.presenter.TeaShopPresenter;
import com.drifting.bureau.mvp.ui.adapter.TeaShopAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ViewUtil;
<<<<<<< HEAD
=======
import com.jess.arms.base.BaseActivity;
>>>>>>> origin/dev
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.DeviceUtils;
import com.rb.core.xrecycleview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/30 09:46
 *
 * @author 实体门店
 * module name is TeaShopActivity
 */
<<<<<<< HEAD
public class TeaShopActivity extends BaseManagerActivity<TeaShopPresenter> implements TeaShopContract.View, XRecyclerView.LoadingListener {
=======
public class TeaShopActivity extends BaseActivity<TeaShopPresenter> implements TeaShopContract.View, XRecyclerView.LoadingListener {
>>>>>>> origin/dev
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    @BindView(R.id.et_shop_name)
    EditText mEtShaopName;
    private TeaShopAdapter teaShopAdapter;

    private int mPage = 1;
    private int limit = 10;
    private String Longitude, Latitude;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, TeaShopActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTeaShopComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_tea_shop; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("实体门店");
        initListener();
    }

    public void initListener() {
        mRcyPublic.setLayoutManager(new LinearLayoutManager(this));
        mRcyPublic.setLoadingListener(this);
        teaShopAdapter = new TeaShopAdapter(new ArrayList<>());
        mRcyPublic.setAdapter(teaShopAdapter);

        if (mPresenter != null) {
            mPresenter.getLocation(getActivity());
        }
        mEtShaopName.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId == 0 || actionId == 3) && event != null) {
                //点击搜索要做的操作
                DeviceUtils.hideSoftKeyboard(this, mEtShaopName);
                mPage = 1;
                getData(mPage, true);
            }
            return false;
        });

    }

    public void getData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.nearby(mEtShaopName.getText().toString(), mPage, limit, Longitude, Latitude, loadType);
        }
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        getData(mPage, true);
    }

    @Override
    public void onLoadMore() {
        getData(mPage, false);
    }


    @Override
    public void onloadStart() {
        if (teaShopAdapter.getDatas() == null || teaShopAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(this, mFlState);
        }
    }

    @Override
    public void onLocation(String lng, String lat) {
        Longitude = lng;
        Latitude = lat;
        getData(mPage, true);
    }

    @Override
    public void onBusinessSuccess(TeaShopEntity entity, boolean isNotData) {
        if (mPage == 1 && teaShopAdapter.getItemCount() != 0) {
            teaShopAdapter.clearData();
        }
        List<TeaShopEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            if (isNotData) {
                mPage = 2;
                teaShopAdapter.setData(list);
            } else {
                mPage++;
                teaShopAdapter.addData(list);
            }
        }
    }

    @Override
    public void loadFinish(boolean loadType, boolean isNotData) {
        if (mRcyPublic == null) {
            return;
        }
        if (!loadType && isNotData) {
            mRcyPublic.loadEndLine();
        } else {
            mRcyPublic.refreshEndComplete();
        }
    }

    @Override
    public void loadState(int type) {
        if (teaShopAdapter.getDatas() == null || teaShopAdapter.getDatas().size() == 0) {
            if (type == ViewUtil.NOT_DATA) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_DATA);
            } else if (type == ViewUtil.NOT_SERVER) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_SERVER);
            } else if (type == ViewUtil.NOT_NETWORK) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_NETWORK);
            } else {
                ViewUtil.create().setView(mFlState);
            }
        } else {
            ViewUtil.create().setView(mFlState);
        }
    }


    @OnClick({R.id.toolbar_back, R.id.ll_location})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.ll_location: //城市选择
                    CitySelectionActivity.start(this, false);
                    break;
            }
        }
    }


    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

}