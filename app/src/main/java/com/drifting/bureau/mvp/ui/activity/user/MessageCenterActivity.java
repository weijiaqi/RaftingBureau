package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.data.entity.MessageCenterEntity;
import com.drifting.bureau.data.event.MessageCenterEvent;
import com.drifting.bureau.data.event.MessageEvent;
import com.drifting.bureau.data.event.MessageRefreshEvent;
import com.drifting.bureau.di.component.DaggerMessageCenterComponent;
import com.drifting.bureau.mvp.contract.MessageCenterContract;
import com.drifting.bureau.mvp.presenter.MessageCenterPresenter;
import com.drifting.bureau.mvp.ui.activity.imkit.MyConversationActivity;
import com.drifting.bureau.mvp.ui.adapter.MessageCenterAdapter;
import com.drifting.bureau.mvp.ui.listener.OnTransitionViewListener;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.callback.BaseDataCallBack;
import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.view.NoScrollViewPager;
import com.drifting.bureau.base.BaseManagerActivity;

import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.component.AppComponent;
import com.rb.core.tab.view.indicator.IndicatorViewPager;
import com.rb.core.tab.view.indicator.ScrollIndicatorView;
import com.rb.core.tab.view.indicator.slidebar.LayoutBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.utils.RouteUtils;


/**
 * Created on 2022/06/18 11:51
 *
 * @author 消息中心
 * module name is MessageCenterActivity
 */
public class MessageCenterActivity extends BaseManagerActivity<MessageCenterPresenter> implements MessageCenterContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.indicator_tablayout)
    ScrollIndicatorView mIndicatorTablayout;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    @BindView(R.id.tv_read)
    TextView mTvRead;
    private IndicatorViewPager indicatorViewPager;
    private List<MessageCenterEntity> mTabTitle;
    private MessageCenterAdapter messageCenterAdapter;
    private int Sys_msg = 0;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, MessageCenterActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMessageCenterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_message_center; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("元宇宙电波");
        loadRmName();
        initListener();
        indicatorViewPager.setOnIndicatorPageChangeListener((preItem, currentItem) -> {
            if (currentItem == 1) {
                if (mTabTitle.size() > 0) {
                    if (mTabTitle.get(1).isUnread()) {
                        mTvRead.setVisibility(View.VISIBLE);
                    } else {
                        mTvRead.setVisibility(View.GONE);
                    }
                }
            } else {
                mTvRead.setVisibility(View.GONE);
            }

            setTextTypeface(currentItem, Typeface.BOLD);
            setTextTypeface(preItem, Typeface.NORMAL);
        });

    }

    public void loadRmName() {
        //必须先将自定义的会话 Activity 注册到 RouteUtils 后，才能通过这种方式跳转到自定义会话 Activity，否则跳转的是 SDK 默认会话 Activity，即 RongConversationActivity。
        RouteUtils.registerActivity(RouteUtils.RongActivityType.ConversationActivity, MyConversationActivity.class);
    }


    public void initListener() {
        mIndicatorTablayout.setOnTransitionListener(new OnTransitionViewListener().setValueFromRes(this,
                R.color.white, R.color.color_99, R.dimen.tab_message_nor_size, R.dimen.tab_message_nor_size));
        indicatorViewPager = new IndicatorViewPager(mIndicatorTablayout, viewPager);
        indicatorViewPager.setIndicatorScrollBar(new LayoutBar(this, R.layout.layout_indicator_view));
        setTextTypeface(0, Typeface.BOLD);
        messageCenterAdapter = new MessageCenterAdapter(getSupportFragmentManager());
        getUnread();
    }


    public void getUnread() {
        mTabTitle = new ArrayList();
        mTabTitle.clear();
        RequestUtil.create().unread(entity -> {
            if (entity != null && entity.getCode() == 200) {
                Sys_msg = entity.getData().getSys_msg();
                mTabTitle.add(new MessageCenterEntity(1, "星际会话", false));
                mTabTitle.add(new MessageCenterEntity(2, "接收电波", entity.getData().getSys_msg() == 0 ? false : true));
                mTabTitle.add(new MessageCenterEntity(3, "星际联络人", entity.getData().getFriends() == 0 ? false : true));
                messageCenterAdapter.setData(mTabTitle);
                if (indicatorViewPager != null) {
                    indicatorViewPager.setAdapter(messageCenterAdapter);
                    indicatorViewPager.setPageOffscreenLimit(mTabTitle.size() - 1);
                    if (messageCenterAdapter != null && messageCenterAdapter.getCount() > 0) {
                        indicatorViewPager.setCurrentItem(0, false);
                    }
                }
            }
        });

    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }


    @OnClick({R.id.toolbar_back, R.id.tv_read})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_read:  //一键已读
                    RequestUtil.create().markread(0, new BaseDataCallBack() {
                        @Override
                        public void getData(BaseEntity entity) {
                            if (entity.getCode() == 200) {
                                mTvRead.setVisibility(View.GONE);
                                setRefresh(1);
                            }
                        }
                    });
                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageCenterEvent(MessageCenterEvent event) {
        if (event != null) {
            setRefresh(2);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEvent(MessageEvent event) {
        if (event != null) {
            if (Sys_msg > 0) {
                Sys_msg--;
                if (Sys_msg == 0) {
                    setRefresh(1);
                }
            }
        }
    }


    public void setRefresh(int index) {
        if (mTabTitle.size() > 0) {
            mTvRead.setVisibility(View.GONE);
            mTabTitle.get(index).setUnread(false);
            messageCenterAdapter.setData(mTabTitle);
            EventBus.getDefault().post(new MessageRefreshEvent());
        }
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

    @Override
    protected void onDestroy() {
        RequestUtil.create().disDispose();
        super.onDestroy();
    }
}