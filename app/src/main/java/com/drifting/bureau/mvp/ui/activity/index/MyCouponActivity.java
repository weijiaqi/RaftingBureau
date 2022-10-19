package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.data.entity.WinningRecordEntity;
import com.drifting.bureau.di.component.DaggerMyCouponComponent;
import com.drifting.bureau.mvp.ui.adapter.MyCouponAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.MyCouponContract;
import com.drifting.bureau.mvp.presenter.MyCouponPresenter;
import com.rb.core.tab.view.indicator.IndicatorViewPager;
import com.rb.core.tab.view.indicator.ScrollIndicatorView;
import com.rb.core.tab.view.indicator.slidebar.LayoutBar;
import com.rb.core.tab.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/10/14 11:08
 *
 * @author 我的卡券
 * module name is MyCouponActivity
 */
public class MyCouponActivity extends BaseActivity{

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.indicator_tablayout)
    ScrollIndicatorView mIndicatorTablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private IndicatorViewPager indicatorViewPager;
    private MyCouponAdapter myCouponAdapter;
    private List<WinningRecordEntity> mTabTitle;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, MyCouponActivity.class);
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
        mToolbarTitle.setText("我的卡券");
        initListener();
    }

    public void initListener() {
        mIndicatorTablayout.setOnTransitionListener(new OnTransitionTextListener().setValueFromRes(this,
                R.color.color_6dec, R.color.color_99, R.dimen.tab_message_nor_size, R.dimen.tab_message_nor_size));
        indicatorViewPager = new IndicatorViewPager(mIndicatorTablayout, viewPager);
        indicatorViewPager.setIndicatorScrollBar(new LayoutBar(this, R.layout.layout_indicator_view));
        setTextTypeface(0, Typeface.BOLD);
        myCouponAdapter = new MyCouponAdapter(getSupportFragmentManager());
        myCouponAdapter.setData(getData());
        if (indicatorViewPager != null) {
            indicatorViewPager.setAdapter(myCouponAdapter);
            indicatorViewPager.setPageOffscreenLimit(mTabTitle.size() - 1);
            if (myCouponAdapter != null && myCouponAdapter.getCount() > 0) {
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
        mTabTitle.add(new WinningRecordEntity(0, "拥有卡券"));
        mTabTitle.add(new WinningRecordEntity(2, "过期卡券"));
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