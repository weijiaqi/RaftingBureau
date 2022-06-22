package com.drifting.bureau.mvp.ui.activity.home;

import static android.view.View.TRANSLATION_X;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.KeyEvent;

import android.view.View;

import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;


import com.drifting.bureau.R;
import com.drifting.bureau.data.event.AnswerCompletedEvent;
import com.drifting.bureau.data.event.BackSpaceEvent;
import com.drifting.bureau.data.event.MessageRefreshEvent;
import com.drifting.bureau.di.component.DaggerDiscoveryTourComponent;
import com.drifting.bureau.mvp.model.entity.FriendInfoEntity;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
import com.drifting.bureau.mvp.model.entity.PlanetEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.ui.activity.index.SpaceCapsuleActivity;
import com.drifting.bureau.mvp.ui.activity.index.ViewRaftingActivity;
import com.drifting.bureau.mvp.ui.activity.user.AboutMeActivity;
import com.drifting.bureau.mvp.ui.activity.user.MessageCenterActivity;
import com.drifting.bureau.mvp.ui.adapter.DiscoveryViewpagerAdapter;
import com.drifting.bureau.mvp.ui.dialog.RaftingInforDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.animator.AnimatorUtil;
import com.drifting.bureau.util.callback.BaseDataCallBack;
import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.view.DiscoveryTransformer;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.DiscoveryTourContract;
import com.drifting.bureau.mvp.presenter.DiscoveryTourPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;


/**
 * Created on 2022/05/10 14:30
 * 探索之旅
 *
 * @author WJQ
 * module name is DiscoveryTourActivity
 */
public class DiscoveryTourActivity extends BaseActivity<DiscoveryTourPresenter> implements DiscoveryTourContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToobarBack;
    @BindView(R.id.rl_info)
    RelativeLayout mRlInfo;
    @BindView(R.id.iv_rocket)
    ImageView mIvRocket;
    @BindView(R.id.rl_message)
    RelativeLayout mRlMessage;
    @BindView(R.id.vp_img)
    ViewPager viewPager;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.tv_about_me)
    TextView mTvAboutMe;
    @BindView(R.id.iv_hot)
    ImageView mIvHot;
    private List<PlanetEntity> list;
    private RaftingInforDialog raftingInforDialog;
    private AnimatorSet animatorSet;

    private Handler handler;
    private boolean isAnmiation = true;
    private int id, user_id, explore_id;
    private DiscoveryViewpagerAdapter discoveryViewpagerAdapter;
    private UserInfoEntity userInfoEntity;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, DiscoveryTourActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDiscoveryTourComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadUI();
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_discovery_tour; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @SuppressLint("Range")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToobarBack.setVisibility(View.GONE);
        mRlInfo.setVisibility(View.VISIBLE);
        loadUI();
    }



    public void loadUI() {
        if (mPresenter != null) {
            mPresenter.getExploreList();
            mPresenter.getLocation(this);
            mPresenter.getRongIM(Preferences.getRcToken());
        }
        frame.setOnTouchListener((view, motionEvent) -> viewPager.onTouchEvent(motionEvent));
        getUserInfo();
    }


    public void getUserInfo() {
        RequestUtil.create().userplayer(Preferences.getUserId(), entity -> {
            if (entity != null && entity.getCode() == 200) {
                userInfoEntity = entity.getData();
                mTvAboutMe.setText(userInfoEntity.getPlanet().getName());
                unread();
            }
        });
    }


    public void unread() {
        RequestUtil.create().unread(entity1 -> {
            if (entity1.getCode() == 200) {
                if (entity1.getData().getIndex_msg() == 0) {
                    mIvHot.setVisibility(View.GONE);
                } else {
                    mIvHot.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }


    @OnClick({R.id.rl_message, R.id.tv_about_me, R.id.tv_space_capsule, R.id.rl_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_message: //开启新消息
                RequestUtil.create().userplayer(user_id + "", entity -> {
                    if (entity != null && entity.getCode() == 200) {
                        raftingInforDialog = new RaftingInforDialog(DiscoveryTourActivity.this, entity.getData(), explore_id);
                        raftingInforDialog.show();
                        raftingInforDialog.setOnClickCallback(type -> {
                            if (type == RaftingInforDialog.CLICK_FINISH) {
                                RequestUtil.create().messagethrow(id, entity1 -> {
                                    if (entity1.getCode() == 200) {
                                        IntoSpace();
                                    } else {
                                        showMessage(entity1.getMsg());
                                    }
                                });
                            } else if (type == RaftingInforDialog.CLICK_SELECT) {
                                ViewRaftingActivity.start(DiscoveryTourActivity.this, user_id, id, explore_id, entity.getData(), false);
                            }
                        });
                    }
                });
                break;
            case R.id.tv_about_me: //关于我
                if (userInfoEntity!=null){
                    AboutMeActivity.start(this, userInfoEntity, false);
                }
                break;
            case R.id.tv_space_capsule: //太空舱
                SpaceCapsuleActivity.start(this, false);
                break;
            case R.id.rl_info:
                MessageCenterActivity.start(this, false);
        }

    }

    Runnable mAdRunnable = () -> getMessage();

    //获取新消息
    public void getMessage() {
        if (mPresenter != null) {
            mPresenter.getMessage();
        }
    }

    @Override
    public void onMessageReceiveSuccess(MessageReceiveEntity entity) {
        if (entity != null && entity.getId() != null) {
            if (entity.getId() != 0) {
                id = entity.getId();
                user_id = entity.getUser_id();
                explore_id = entity.getExplore_id();
                if (isAnmiation) {
                    isAnmiation = false;
                    handler.postDelayed(mAdRunnable, entity.getDrift_rest() * 10);
                   objectAnimation(1, mIvRocket, mRlMessage, -500, 200, 0, 6, 1000);
                }
            } else {
                mIvRocket.setVisibility(View.INVISIBLE);
                mRlMessage.setVisibility(View.INVISIBLE);
                handler.postDelayed(mAdRunnable, 1000 * 10);
            }
        }
    }

    @Override
    public void onLocationSuccess() {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BackSpaceEvent(BackSpaceEvent backSpaceEvent) {
        if (backSpaceEvent != null) {
            IntoSpace();
        }
    }

    public void IntoSpace() {
        isAnmiation = true;
        objectAnimation(2, mIvRocket, mRlMessage, 0, 0, 1500, -500, 500);
    }


    /**
     * @description 属性组合动画
     */
    public void objectAnimation(int type, View view, View tagetview, int values1, int values2, int x, int y, long duration) {
        if (animatorSet != null) {
            animatorSet.cancel();
            view.animate().rotation(0).setDuration(60).start();
            tagetview.animate().rotation(0).setDuration(60).start();
        }
        if (type == 1) {
            view.setVisibility(View.VISIBLE);
        }
        tagetview.setVisibility(View.INVISIBLE);

        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, TRANSLATION_X, values1, x);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, values2, y);
        animatorSet = new AnimatorSet();
        animatorSet.play(translationX).with(translationY);
        animatorSet.setDuration(duration);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (type == 1) {
                    tagetview.setVisibility(View.VISIBLE);
                    AnimatorUtil.floatAnim(view, 2000);
                    AnimatorUtil.floatAnim(tagetview, 2000);
                } else {
                    tagetview.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    @Override
    public void onExploretypeSuccess(List<PlanetEntity> entityList) {
        if (entityList != null && entityList.size() > 0) {
            list = entityList;
            discoveryViewpagerAdapter = new DiscoveryViewpagerAdapter(this, list);
            viewPager.setAdapter(discoveryViewpagerAdapter);
            viewPager.setCurrentItem(list.size() * 100);
            viewPager.setOffscreenPageLimit(list.size());
            viewPager.setClipChildren(false);
            viewPager.setPageTransformer(true, new DiscoveryTransformer());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void AnswerCompletedEvent(AnswerCompletedEvent answerCompletedEvent) {
        if (answerCompletedEvent != null) {
            getUserInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageRefreshEvent(MessageRefreshEvent event) {
        if (event != null) {
            unread();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new Handler();
        handler.postDelayed(mAdRunnable, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(mAdRunnable);
        }
    }

    @Override
    public void finishSuccess() {
        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            killApp();
        }
        return false;
    }

    public void killApp() {
        if (mPresenter != null) {
            mPresenter.exitBy2Click();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(mAdRunnable);
        }
        RequestUtil.create().disDispose();
    }
}