package com.drifting.bureau.mvp.ui.activity.home;

import static android.view.View.TRANSLATION_X;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;

import com.drifting.bureau.data.event.AnswerCompletedEvent;
import com.drifting.bureau.data.event.BackSpaceEvent;
import com.drifting.bureau.data.event.MessageRefreshEvent;
import com.drifting.bureau.di.component.DaggerDiscoveryTourComponent;
import com.drifting.bureau.mvp.contract.DiscoveryTourContract;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
import com.drifting.bureau.mvp.model.entity.PlanetEntity;
import com.drifting.bureau.mvp.model.entity.StarUpIndexEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.presenter.DiscoveryTourPresenter;
import com.drifting.bureau.mvp.ui.activity.index.DriftTrackMapActivity;

import com.drifting.bureau.mvp.ui.activity.index.SpaceCapsuleActivity;
import com.drifting.bureau.mvp.ui.activity.index.StarDistributionActivity;
import com.drifting.bureau.mvp.ui.activity.unity.ARMetaverseCenterActivity;
import com.drifting.bureau.mvp.ui.activity.user.AboutMeActivity;
import com.drifting.bureau.mvp.ui.activity.user.MessageCenterActivity;
import com.drifting.bureau.mvp.ui.activity.user.NewAboutMeActivity;
import com.drifting.bureau.mvp.ui.activity.web.ShowWebViewActivity;
import com.drifting.bureau.mvp.ui.adapter.DiscoveryViewpagerAdapter;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.AppUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.animator.AnimatorUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.view.DiscoveryTransformer;
import com.drifting.bureau.view.guide.IndexGuiView;
import com.jess.arms.di.component.AppComponent;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.UnReadMessageManager;
import io.rong.imlib.model.Conversation;

/**
 * Created on 2022/05/10 14:30
 * ????????????
 *
 * @author WJQ
 * module name is DiscoveryTourActivity
 */
public class DiscoveryTourActivity extends BaseManagerActivity<DiscoveryTourPresenter> implements DiscoveryTourContract.View {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.tv_energy)
    TextView mTvEnergy;
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
    @BindView(R.id.tv_youth_camp)
    TextView mTvYouthCamp;
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.guide_view)
    IndexGuiView mGuideView;
    private List<PlanetEntity> list;
    private AnimatorSet animatorSet;
    private Handler handler;
    private boolean isAnmiation = true;
    private int id, explore_id;
    private DiscoveryViewpagerAdapter discoveryViewpagerAdapter;
    private StarUpIndexEntity starUpIndexEntity;
    private UserInfoEntity userInfoEntity;


    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, DiscoveryTourActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDiscoveryTourComponent //??????????????????,?????????????????????
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
        return R.layout.activity_discovery_tour; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    @SuppressLint("Range")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        loadUI();
    }


    @Override
    protected void initVisible() {
        super.initVisible();
        if (mPresenter != null) {
            mPresenter.getVersionInfo(this);
        }
    }


    public void loadUI() {
        //??????????????????
        mGuideView.setVisibility(!Preferences.isOrdinaryGuide() ? View.VISIBLE : View.GONE);
        mGuideView.setOnClickCallback(() -> {
            DriftTrackMapActivity.start(DiscoveryTourActivity.this, 2, 1, 0, false);
            mGuideView.setVisibility(View.GONE);
        });

        if (mPresenter != null) {
            mPresenter.getExploreList();
            mPresenter.getLocation(this);
        }
        frame.setOnTouchListener((view, motionEvent) -> viewPager.onTouchEvent(motionEvent));
    }


    public void unread() {
        RequestUtil.create().unread(entity1 -> {
            if (entity1 != null && entity1.getCode() == 200) {
                if (mIvHot!=null){
                    mIvHot.setVisibility(entity1.getData().getIndex_msg() == 0?View.GONE:View.VISIBLE);
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


    @OnClick({R.id.rl_message, R.id.tv_space_capsule, R.id.rl_info, R.id.ll_step_star, R.id.rl_right, R.id.tv_youth_camp, R.id.iv_enter_main, R.id.tv_about_me})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.rl_right:  //??????
                    ShowWebViewActivity.start(this, 4, false);
                    break;
                case R.id.rl_message: //???????????????
                    DriftTrackMapActivity.start(this, explore_id, id, false);
                    break;
                case R.id.tv_space_capsule: //?????????
                    SpaceCapsuleActivity.start(this, false);
                    break;
                case R.id.rl_info:
                    MessageCenterActivity.start(this, false);
                    break;
                case R.id.ll_step_star:
                    Preferences.setARModel(true);
                    ARMetaverseCenterActivity.start(this, true);
                    break;
                case R.id.tv_youth_camp:  //???????????????
                    if (starUpIndexEntity != null) {
                        ShowWebViewActivity.start(this, 0, "???????????????", starUpIndexEntity.getUrl() + "?Sign=" + StringUtil.formatNullString(AppUtil.getSign(Preferences.getPhone())) + "&token=" + StringUtil.formatNullString(Preferences.getToken()), false);
                    }
                    break;
                case R.id.iv_enter_main:  //????????????
                    if (userInfoEntity != null) {
                        if (userInfoEntity.getPlanet().getLevel() == 1) {  //?????????
                            StarDistributionActivity.start(this, false);
                        } else {
                            NewAboutMeActivity.start(this, false);
                        }
                    }
                    break;
                case R.id.tv_about_me:  //????????????
                    if (userInfoEntity != null) {
                        AboutMeActivity.start(this, userInfoEntity, false);
                    }
                    break;
            }
        }
    }

    Runnable mAdRunnable = () -> getMessage();

    //???????????????
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
                explore_id = entity.getExplore_id();
                mTvMessage.setText(getString(R.string.from_nebula, entity.getNebula_name()));
                handler.postDelayed(mAdRunnable, entity.getDrift_rest() * 1000);
                if (isAnmiation) {
                    isAnmiation = false;
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
        objectAnimation(2, mIvRocket, mRlMessage, 0, 0, 1500, -600, 500);
    }


    /**
     * @description ??????????????????
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
                    AnimatorUtil.floatAnim(view, 2000, 6);
                    AnimatorUtil.floatAnim(tagetview, 2000, 6);
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

            getUserInfo(1);

        }
    }


    public void getUserInfo(int status) {
        RequestUtil.create().userplayer(Preferences.getUserId(), entity -> {
            if (entity != null && entity.getCode() == 200) {
                userInfoEntity = entity.getData();
                mTvAboutMe.setVisibility(userInfoEntity.getPlanet().getLevel() == 1 ? View.VISIBLE : View.GONE);
                mTvAboutMe.setText(userInfoEntity.getPlanet().getName());
                mTvEnergy.setText(userInfoEntity.getUser().getMeta_power());

                if (status == 1) {
                    RequestUtil.create().startup(entity1 -> {
                        if (entity1 != null && entity1.getCode() == 200) {
                            starUpIndexEntity = entity1.getData();
                            if (mTvYouthCamp!=null){
                                mTvYouthCamp.setVisibility(!TextUtils.isEmpty(starUpIndexEntity.getUrl()) ? View.VISIBLE : View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageRefreshEvent(MessageRefreshEvent event) {
        if (event != null) {
            unread();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void AnswerCompletedEvent(AnswerCompletedEvent answerCompletedEvent) {
        getUserInfo(2);
    }


    @Override
    public void onResume() {
        super.onResume();
        RongIM.getInstance().addUnReadMessageCountChangedObserver(observer, Conversation.ConversationType.PRIVATE);
        handler = new Handler();
        handler.postDelayed(mAdRunnable, 1000);
    }


    /**
     * ????????????????????????
     *
     * @param i
     */
    private UnReadMessageManager.IUnReadMessageObserver observer = i -> {
        if (i > 0) {   //???????????????
            mIvHot.setVisibility(View.VISIBLE);
        } else {
            unread();
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        //?????????????????????????????????
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(observer);
        if (handler != null) {
            handler.removeCallbacks(mAdRunnable);
        }
    }

    @Override
    public void finishSuccess() {
        // ??????????????????
        MobclickAgent.onKillProcess(this);
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
        //?????????????????????????????????
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(observer);
        if (handler != null) {
            handler.removeCallbacks(mAdRunnable);
        }
        RequestUtil.create().disDispose();
    }
}