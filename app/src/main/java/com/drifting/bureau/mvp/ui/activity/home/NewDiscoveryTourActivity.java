package com.drifting.bureau.mvp.ui.activity.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.data.event.AnswerCompletedEvent;
import com.drifting.bureau.data.event.BackSpaceEvent;
import com.drifting.bureau.data.event.MessageRefreshEvent;
import com.drifting.bureau.di.component.DaggerDiscoveryTourComponent;
import com.drifting.bureau.di.component.DaggerNewDiscoveryTourComponent;
import com.drifting.bureau.mvp.contract.DiscoveryTourContract;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
import com.drifting.bureau.mvp.model.entity.PlanetEntity;
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
import com.drifting.bureau.mvp.ui.dialog.ExpectDialog;
import com.drifting.bureau.storageinfo.MMKVUtils;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.AppUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.animator.AnimatorUtil;
import com.drifting.bureau.util.request.RequestUtil;
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
import retrofit2.http.PUT;

/**
 * @Description: 新版首页
 * @Author : WeiJiaQI
 * @Time : 2022/10/25 9:46
 */
public class NewDiscoveryTourActivity extends BaseManagerActivity<DiscoveryTourPresenter> implements DiscoveryTourContract.View {
    @BindView(R.id.ll_step_star)
    LinearLayout mLlStepStar;
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.iv_find_box)
    ImageView mIvFindBox;
    @BindView(R.id.iv_received_message)
    ImageView mIvReceivedMessage;
    @BindView(R.id.iv_hot)
    ImageView mIvHot;
    @BindView(R.id.tv_about_me)
    TextView mTvAboutMe;
    @BindView(R.id.tv_energy)
    TextView mTvEnergy;
    @BindView(R.id.rl_ship)
    RelativeLayout mRlShip;
    @BindView(R.id.iv_public_floaters)
    ImageView mIvPublic;
    @BindView(R.id.iv_encounter_drift)
    ImageView mIvEncounter;
    @BindView(R.id.iv_enter)
    ImageView mIvEnter;
    @BindView(R.id.rl_my_faction)
    RelativeLayout mRlMyFaction;
    private Handler handler;
    private UserInfoEntity userInfoEntity;
    private int id, explore_id;
    private boolean isAnmiation = true;
    private ExpectDialog expectDialog;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, NewDiscoveryTourActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNewDiscoveryTourComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_new_discovery_tour;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        mLlStepStar.setVisibility(View.GONE);
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
        //动画
        AnimatorUtil.floatAnim(mIvFindBox, 2000, 6);
        statFloatAnim(mRlShip, 0);
        statFloatAnim(mIvPublic, 1);
        statFloatAnim(mIvEncounter, 2);
        AnimatorUtil.ScaleAnim(mIvEnter, 2000, 1f, 0.9f, 0.9f);
        AnimatorUtil.ScaleAnim(mRlMyFaction, 2000, 1f, 0.9f, 0.9f);

        if (mPresenter != null) {
            mPresenter.getLocation(this);
        }
    }


    public void statFloatAnim(View view, int type) {
        AnimatorUtil.ObjectAnim(view, type, 2000, (int) (Math.random() * (9 - 1 + 5) + 5));
    }


    Runnable mAdRunnable = () -> getMessage();

    //获取新消息
    public void getMessage() {
        if (mPresenter != null) {
            mPresenter.getMessage();
        }
    }


    @OnClick({R.id.iv_received_message, R.id.rl_ship, R.id.tv_space_capsule, R.id.rl_info, R.id.iv_enter, R.id.rl_right, R.id.rl_my_faction, R.id.tv_about_me, R.id.iv_public_floaters, R.id.iv_encounter_drift})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.rl_right:  //右边
                    ShowWebViewActivity.start(this, 4, false);
                    break;
                case R.id.iv_received_message: //开启新消息
                    DriftTrackMapActivity.start(this, explore_id, id, false);
                    break;
                case R.id.rl_ship:  //传递漂
                    DriftTrackMapActivity.start(this, 1, 0, false);
                    break;
                case R.id.iv_public_floaters:  //公众漂
                    PublicFloatersActivity.start(this,false);
                    break;
                case R.id.iv_encounter_drift:  //遇见漂
                    expectDialog = new ExpectDialog(this);
                    expectDialog.show();
                    break;
                case R.id.tv_space_capsule: //太空舱
                    SpaceCapsuleActivity.start(this, false);
                    break;
                case R.id.rl_info:
                    MessageCenterActivity.start(this, false);
                    break;
                case R.id.iv_enter:
                    MMKVUtils.getInstance().setARModel(true);
                    ARMetaverseCenterActivity.start(this, true);
                    break;
                case R.id.rl_my_faction:  //进入主页
                    if (userInfoEntity != null) {
                        if (userInfoEntity.getPlanet().getLevel() == 1) {  //荒芜星
                            StarDistributionActivity.start(this, false);
                        } else {
                            NewAboutMeActivity.start(this, false);
                        }
                    }
                    break;
                case R.id.tv_about_me:  //关于我们
                    if (userInfoEntity != null) {
                        AboutMeActivity.start(this, userInfoEntity, false);
                    }
                    break;
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        RongIM.getInstance().addUnReadMessageCountChangedObserver(observer, Conversation.ConversationType.PRIVATE);
        handler = new Handler();
        handler.postDelayed(mAdRunnable, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //移除监听，防止内存泄漏
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(observer);
        if (handler != null) {
            handler.removeCallbacks(mAdRunnable);
        }
    }

    /**
     * 未读消息监听回调
     *
     * @param i
     */
    private UnReadMessageManager.IUnReadMessageObserver observer = i -> {
        if (i > 0) {   //有未读消息
            mIvHot.setVisibility(View.VISIBLE);
        } else {
            unread();
        }
    };


    public void unread() {
        RequestUtil.create().unread(entity1 -> {
            if (entity1 != null && entity1.getCode() == 200) {
                if (mIvHot != null) {
                    mIvHot.setVisibility(entity1.getData().getIndex_msg() == 0 ? View.GONE : View.VISIBLE);
                }
            }
        });
    }




    @Override
    public void onMessageReceiveSuccess(MessageReceiveEntity entity) {
        if (entity != null && entity.getId() != null) {
            if (entity.getId() != 0) {
                id = entity.getId();
                explore_id = entity.getExplore_id();
                handler.postDelayed(mAdRunnable, entity.getDrift_rest() * 1000);
                if (isAnmiation) {
                    isAnmiation = false;
                    setNewMessage();
                }
            } else {
                IntoSpace();
            }
        }
    }

    @Override
    public void onCheckVersionSuccess() {
        //获取用户信息
        getUserInfo();
    }


    //设置新消息展示状态
    public void setNewMessage() {
        mIvReceivedMessage.setVisibility(View.VISIBLE);
        AnimatorUtil.ScaleAnim(mIvReceivedMessage, 2500, 1f, 0.2f, 0.7f);
    }

    @Override
    public void onLocationSuccess() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BackSpaceEvent(BackSpaceEvent backSpaceEvent) {
        if (backSpaceEvent != null) {
            isAnmiation = true;
            IntoSpace();
        }
    }

    public void IntoSpace() {
        mIvReceivedMessage.setVisibility(View.INVISIBLE);
        handler.postDelayed(mAdRunnable, 1000 * 10);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageRefreshEvent(MessageRefreshEvent event) {
        if (event != null) {
            unread();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void AnswerCompletedEvent(AnswerCompletedEvent answerCompletedEvent) {
        getUserInfo();
    }


    public void getUserInfo() {
        RequestUtil.create().userplayer(Preferences.getUserId(), entity -> {
            if (entity != null && entity.getData() != null && entity.getCode() == 200) {
                userInfoEntity = entity.getData();
                mTvAboutMe.setVisibility(userInfoEntity.getPlanet().getLevel() == 1 ? View.VISIBLE : View.GONE);
                mTvEnergy.setText(userInfoEntity.getUser().getMeta_power());
            }
        });

    }


    @Override
    public void finishSuccess() {
        // 友盟统计退出
        MobclickAgent.onKillProcess(this);
        finish();
    }


    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
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
        //移除监听，防止内存泄漏
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(observer);
        if (handler != null) {
            handler.removeCallbacks(mAdRunnable);
        }
        RequestUtil.create().disDispose();
    }
}
