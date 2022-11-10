package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.data.entity.CityEntity;
import com.drifting.bureau.di.component.DaggerCitySelectionComponent;
import com.drifting.bureau.mvp.contract.CitySelectionContract;
import com.drifting.bureau.mvp.model.entity.HotCityEntity;
import com.drifting.bureau.mvp.presenter.CitySelectionPresenter;
import com.drifting.bureau.mvp.ui.adapter.CityListAdapter;
import com.drifting.bureau.mvp.ui.adapter.HotListAdapter;
import com.drifting.bureau.util.AssessUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GsonUtil;
import com.drifting.bureau.util.ViewUtil;
import com.drifting.bureau.view.location.QuickLocationBar;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/30 10:49
 *
 * @author 城市选择
 * module name is CitySelectionActivity
 */
public class CitySelectionActivity extends BaseManagerActivity<CitySelectionPresenter> implements CitySelectionContract.View {
    @BindView(R.id.rcy_hotlist)
    RecyclerView mHotList;
    @BindView(R.id.city_list)
    RecyclerView mCityLit;
    @BindView(R.id.city_loactionbar)
    QuickLocationBar mQuicLocationBar;
    @BindView(R.id.frame)
    FrameLayout frame;
    private List<CityEntity> mCityEntity;


    private CityListAdapter cityListAdapter;
    private HotListAdapter hotListAdapter;
    private Handler mHandler = new Handler();

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, CitySelectionActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCitySelectionComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_city_selection; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        initListener();
    }

    public void initListener() {
        mHandler.postDelayed(mRunnable, 1000);
    }


    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHotList.setLayoutManager(new GridLayoutManager(CitySelectionActivity.this, 4));
            hotListAdapter = new HotListAdapter(new ArrayList<>());
            mHotList.setAdapter(hotListAdapter);
            hotListAdapter.setData(getData());
            mQuicLocationBar.setOnTouchLitterChangedListener(new LetterListViewListener());
            mCityLit.setLayoutManager(new LinearLayoutManager(CitySelectionActivity.this));
            cityListAdapter = new CityListAdapter(new ArrayList<>());
            mCityLit.setAdapter(cityListAdapter);
            String cityjson = AssessUtil.getJson("city.json", CitySelectionActivity.this);
            mCityEntity = GsonUtil.getObjectList(cityjson, CityEntity.class);
            Collections.sort(mCityEntity);
            cityListAdapter.setData(mCityEntity);
        }
    };

    public List<HotCityEntity> getData() {
        List<HotCityEntity> list = new ArrayList<>();
        list.add(new HotCityEntity("北京市"));
        list.add(new HotCityEntity("上海市"));
        list.add(new HotCityEntity("广州市"));
        list.add(new HotCityEntity("深圳市"));
        list.add(new HotCityEntity("成都市"));
        return list;
    }

    public Activity getActivity() {
        return this;
    }


    public void showLoading() {
        ViewUtil.create().show(this);
    }

    public void hideLoading() {
        ViewUtil.create().dismiss();
    }


    private class LetterListViewListener implements
            QuickLocationBar.OnTouchLetterChangedListener {

        @Override
        public void touchLetterChanged(String s) {
            // TODO Auto-generated method stub
            Map<String, Integer> alphaIndexer = cityListAdapter.getCityMap();
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                smoothMoveToPosition(mCityLit, position);
            }
        }

    }

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
        }
    }


    @OnClick({R.id.tv_exit})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_exit:
                    finish();
                    break;
            }
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }
}