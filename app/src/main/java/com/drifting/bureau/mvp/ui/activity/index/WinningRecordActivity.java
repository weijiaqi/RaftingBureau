package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.data.entity.WinningRecordEntity;
import com.drifting.bureau.mvp.ui.adapter.WinningAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.di.component.AppComponent;
import com.rb.core.tab.view.indicator.IndicatorViewPager;
import com.rb.core.tab.view.indicator.ScrollIndicatorView;
import com.rb.core.tab.view.indicator.slidebar.LayoutBar;
import com.rb.core.tab.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/10/12 14:31
 *
 * @author 中奖记录
 * module name is WinningRecordActivity
 */
public class WinningRecordActivity extends BaseManagerActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.indicator_tablayout)
    ScrollIndicatorView mIndicatorTablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private IndicatorViewPager indicatorViewPager;
    private WinningAdapter winningRecordAdapter;

    private List<WinningRecordEntity> mTabTitle;


    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, WinningRecordActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_winning_record; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("中奖记录");
        initListener();
    }

    public void initListener() {
        mIndicatorTablayout.setOnTransitionListener(new OnTransitionTextListener().setValueFromRes(this,
                R.color.color_6dec, R.color.color_97a, R.dimen.tab_message_nor_size, R.dimen.tab_message_nor_size));
        indicatorViewPager = new IndicatorViewPager(mIndicatorTablayout, viewPager);
        indicatorViewPager.setIndicatorScrollBar(new LayoutBar(this, R.layout.layout_indicator_view));
        setTextTypeface(0, Typeface.BOLD);
        winningRecordAdapter = new WinningAdapter(getSupportFragmentManager());
        winningRecordAdapter.setData(getData());
        if (indicatorViewPager != null) {
            indicatorViewPager.setAdapter(winningRecordAdapter);
            indicatorViewPager.setPageOffscreenLimit(mTabTitle.size() - 1);
            if (winningRecordAdapter != null && winningRecordAdapter.getCount() > 0) {
                indicatorViewPager.setCurrentItem(0, false);
            }
        }
        indicatorViewPager.setOnIndicatorPageChangeListener((preItem, currentItem) -> {
            setTextTypeface(currentItem, Typeface.BOLD);
            setTextTypeface(preItem, Typeface.NORMAL);
        });

    }

    public List<WinningRecordEntity> getData() {
        mTabTitle = new ArrayList();
        mTabTitle.add(new WinningRecordEntity(1, "魔法柜"));
        mTabTitle.add(new WinningRecordEntity(2, "盲盒"));
        return mTabTitle;
    }

    /**
     * 设置字体粗细
     *
     * @param position
     * @param style
     */
    private void setTextTypeface(int position, int style) {
        View view = mIndicatorTablayout.getItemView(position);
        if (view != null) {
            TextView mTitle = view.findViewById(R.id.tv_tab_top_title);
            mTitle.setTypeface(Typeface.defaultFromStyle(style));
        }
    }


    @OnClick({R.id.toolbar_back})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
            }
        }
    }


    public Activity getActivity() {
        return this;
    }


}