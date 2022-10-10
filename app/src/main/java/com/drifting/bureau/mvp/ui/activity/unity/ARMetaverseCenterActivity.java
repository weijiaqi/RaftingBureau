package com.drifting.bureau.mvp.ui.activity.unity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.di.component.DaggerArCenterConsoleComponent;
import com.drifting.bureau.mvp.contract.ArCenterConsoleContract;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
import com.drifting.bureau.mvp.model.entity.MakingRecordEntity;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsEntity;
import com.drifting.bureau.mvp.model.entity.OrderOneEntity;
import com.drifting.bureau.mvp.model.entity.QuestionAssessEntity;
import com.drifting.bureau.mvp.model.entity.QuestionEntity;
import com.drifting.bureau.mvp.model.entity.SpaceCheckEntity;
import com.drifting.bureau.mvp.model.entity.SpaceInfoEntity;
import com.drifting.bureau.mvp.model.entity.TeamStatisticEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.presenter.ArCenterConsolePresenter;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;
import com.drifting.bureau.mvp.ui.activity.index.AnswerResultActivity;
import com.drifting.bureau.mvp.ui.activity.index.AnswerTestActivity;
import com.drifting.bureau.mvp.ui.activity.index.DriftTrackMapActivity;
import com.drifting.bureau.mvp.ui.activity.user.AboutMeActivity;
import com.drifting.bureau.mvp.ui.activity.user.MessageCenterActivity;
import com.drifting.bureau.mvp.ui.activity.user.MySpaceStationActivity;
import com.drifting.bureau.mvp.ui.dialog.ArAnnouncementDisplayDialog;
import com.drifting.bureau.mvp.ui.dialog.CityReleaseDialog;
import com.drifting.bureau.mvp.ui.dialog.DriftingPlayDialog;
import com.drifting.bureau.mvp.ui.dialog.ExclusivePlanetDialog;
import com.drifting.bureau.mvp.ui.dialog.JumpPlanetDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GsonUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.view.MyFrameAnimation;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.di.component.AppComponent;
import com.umeng.analytics.MobclickAgent;
import com.unity3d.player.IUnityPlayerLifecycleEvents;
import com.unity3d.player.MultiWindowSupport;
import com.unity3d.player.UnityPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author 卫佳琪1
 * @description 添加AR
 * @time 11:17 11:17
 */

public class ARMetaverseCenterActivity extends BaseManagerActivity<ArCenterConsolePresenter> implements IUnityPlayerLifecycleEvents, ArCenterConsoleContract.View {
    @BindView(R.id.ll_toolbar)
    LinearLayout mLlToolBar;
    @BindView(R.id.rl_info)
    RelativeLayout mRlInfo;
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.tv_energy)
    TextView mTvEnergy;
    @BindView(R.id.ll_add)
    LinearLayout mLlAdd;
    @BindView(R.id.iv_hot)
    ImageView mIvHot;
    @BindView(R.id.tv_about_me)
    TextView mTvAboutMe;
    @BindView(R.id.rl_anim)
    ImageView mRlAnim;
    private int toggleType = 1;
    protected UnityPlayer mUnityPlayer;
    private UserInfoEntity userInfoEntity;
    private DriftingPlayDialog driftingPlayDialog;
    private ArAnnouncementDisplayDialog arAnnouncementDisplayDialog;
    private ExclusivePlanetDialog exclusivePlanetDialog;

    private JumpPlanetDialog jumpPlanetDialog;
    private List<QuestionEntity> questionEntityList;
    private Map<String, String> map;
    private int questionid, total, paixitype;
    private String value;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, ARMetaverseCenterActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerArCenterConsoleComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ar_metaverse_center;
    }

    protected String updateUnityCommandLineArguments(String cmdLine) {
        return cmdLine;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        setStatusBarHeight(mTvBar);
        getUserInfo();
        String cmdLine = updateUnityCommandLineArguments(getIntent().getStringExtra("unity"));
        getIntent().putExtra("unity", cmdLine);
        mUnityPlayer = new UnityPlayer(this, this);
        View playerView = mUnityPlayer.getView();
        mLlAdd.addView(playerView);
        mUnityPlayer.requestFocus();

        AnimationDrawable anim = (AnimationDrawable) mRlAnim.getBackground();
        anim.start();
        new Handler().postDelayed(() -> {
            anim.stop();
            mRlAnim.setVisibility(View.GONE);
        }, 4500);
    }


    //小熊的点击事件
    public void LittleBear() {
        runOnUiThread(() -> {
            if (driftingPlayDialog == null) {
                driftingPlayDialog = new DriftingPlayDialog(ARMetaverseCenterActivity.this);
            }
            driftingPlayDialog.show();
            driftingPlayDialog.setOnClickCallback(type -> {
                if (type == DriftingPlayDialog.OPEN_PLAY) {//开启玩法
                    DriftTrackMapActivity.start(ARMetaverseCenterActivity.this, 1, 1, 0, false);
                } else if (type == DriftingPlayDialog.START_SPACE) {//空间站
                    if (mPresenter != null) {  //检测是否有空间站
                        mPresenter.spacecheck();
                    }
                }
            });
        });
    }

    //Logo点击
    public void Logo() {
        runOnUiThread(() -> {
            if (arAnnouncementDisplayDialog == null) {
                arAnnouncementDisplayDialog = new ArAnnouncementDisplayDialog(ARMetaverseCenterActivity.this);
            }
            arAnnouncementDisplayDialog.show();
        });
    }

    //飞机
    public void Aircraft() {
        if (userInfoEntity != null) {
            if (userInfoEntity.getPlanet().getLevel() == 1) {
                toggleType = 2;
                mUnityPlayer.UnitySendMessage("Main Camera", "OpenAircraft", userInfoEntity.getPlanet().getLevel() + "");
            } else {
                runOnUiThread(() -> {
                    if (jumpPlanetDialog == null) {
                        jumpPlanetDialog = new JumpPlanetDialog(ARMetaverseCenterActivity.this);
                    }
                    jumpPlanetDialog.show();
                    jumpPlanetDialog.setOnClickCallback(type -> {
                        toggleType = 2;
                        if (type == JumpPlanetDialog.OPEN_PLAY) {  //跃迁派系主星
                            paixitype = 1;
                            mUnityPlayer.UnitySendMessage("Main Camera", "OpenPaiXiXingQiu", userInfoEntity.getPlanet().getLevel() + "");
                        } else if (type == JumpPlanetDialog.OPEN_JUMP) { //前往重新鉴别
                            mUnityPlayer.UnitySendMessage("Main Camera", "OpenAircraft", "");
                        }
                    });

                });
            }
        }


    }


    //第二场景机器人点击
    public void Psychological() {

        runOnUiThread(() -> {
            if (exclusivePlanetDialog == null) {
                exclusivePlanetDialog = new ExclusivePlanetDialog(ARMetaverseCenterActivity.this);
            }
            exclusivePlanetDialog.show();
            exclusivePlanetDialog.setOnClickCallback(type -> {
                if (type == ExclusivePlanetDialog.OPEN_PLAY) {
                    if (mPresenter != null) {
                        mPresenter.questionlist();
                    }
                    //    AnswerTestActivity.start(this, false);
                }
            });

        });
    }


    //答题结果
    public void ReadAndWrite(int id, String vaule) {
        this.questionid = id;
        this.value = vaule;
//        showMessage(value);
    }

    //点击确定按钮
    public void Areyousure() {
        map.put(questionid + "", value);
        Preferences.putHashMapData(map);
        if (map.size() == total) {
            if (mPresenter != null) {
                mPresenter.questionassess(map);
            }
        }
    }

    @OnClick({R.id.tv_change_mode, R.id.rl_info, R.id.tv_about_me})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_change_mode:
                    if (toggleType == 3) {
                        toggleType = 2;
                        mUnityPlayer.UnitySendMessage("Main Camera", "ClosePsychological", "");
                    } else if (toggleType == 2) {
                        toggleType = 1;
                        if (paixitype == 1) {
                            paixitype = 0;
                            mUnityPlayer.UnitySendMessage("Main Camera", "ClosePaiXiXingQiu", "");
                        } else {
                            mUnityPlayer.UnitySendMessage("Main Camera", "CloseAircraft", "");
                        }
                    } else {
                        Preferences.setARModel(false);
                        DiscoveryTourActivity.start(this, true);
                    }
                    break;
                case R.id.rl_info:
                    MessageCenterActivity.start(this, false);
                    break;
                case R.id.tv_about_me:
                    if (userInfoEntity != null) {
                        AboutMeActivity.start(this, userInfoEntity, false);
                    }
                    break;
            }
        }
    }


    public void getUserInfo() {
        RequestUtil.create().userplayer(Preferences.getUserId(), entity -> {
            if (entity != null && entity.getCode() == 200) {
                userInfoEntity = entity.getData();
                mTvAboutMe.setText(userInfoEntity.getPlanet().getName());
                mTvEnergy.setText(userInfoEntity.getUser().getMeta_power());
                unread();
            }
        });
    }


    @Override
    public void onUnityPlayerUnloaded() {
        moveTaskToBack(true);
    }

    @Override
    public void onUnityPlayerQuitted() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        // To support deep linking, we need to make sure that the client can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
        super.onNewIntent(intent);
        setIntent(intent);
        mUnityPlayer.newIntent(intent);
    }


    // If the activity is in multi window mode or resizing the activity is allowed we will use
    // onStart/onStop (the visibility callbacks) to determine when to pause/resume.
    // Otherwise it will be done in onPause/onResume as Unity has done historically to preserve
    // existing behavior.
    @Override
    protected void onStop() {
        super.onStop();

        if (!MultiWindowSupport.getAllowResizableWindow(this))
            return;

        mUnityPlayer.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!MultiWindowSupport.getAllowResizableWindow(this))
            return;

        mUnityPlayer.resume();
    }

    // Pause Unity
    @Override
    protected void onPause() {
        super.onPause();
        MultiWindowSupport.saveMultiWindowMode(this);
        if (MultiWindowSupport.getAllowResizableWindow(this))
            return;

        mUnityPlayer.pause();
    }


    // Resume Unity
    @Override
    protected void onResume() {
        super.onResume();

        if (MultiWindowSupport.getAllowResizableWindow(this) && !MultiWindowSupport.isMultiWindowModeChangedToTrue(this))
            return;

        mUnityPlayer.resume();
    }


    /**
     * @description 未读消息
     */
    public void unread() {
        RequestUtil.create().unread(entity1 -> {
            if (entity1 != null && entity1.getCode() == 200) {
                if (entity1.getData().getIndex_msg() == 0) {
                    mIvHot.setVisibility(View.GONE);
                } else {
                    mIvHot.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    // Quit Unity
    @Override
    protected void onDestroy() {
        mUnityPlayer.destroy();
        super.onDestroy();
    }


    // Low Memory Unity
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL) {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mPresenter.exitBy2Click();
        }
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public void onMessageReceiveSuccess(MessageReceiveEntity entity) {

    }

    @Override
    public void OnTeamSuccess(TeamStatisticEntity entity) {

    }

    @Override
    public void loadFinish(boolean loadType, boolean isNotData) {

    }

    @Override
    public void loadState(int dataState) {

    }

    @Override
    public void myBillLogsSuccess(IncomeRecordEntity entity, boolean isNotData) {

    }

    @Override
    public void onMoreDetailsSuccess(MoreDetailsEntity entity, int type) {

    }

    @Override
    public void onSpcaeInfoSuccess(SpaceInfoEntity entity) {

    }

    @Override
    public void onOrderOneSuccess(OrderOneEntity entity) {

    }

    @Override
    public void onCommentDetailsSuccess(CommentDetailsEntity entity) {

    }

    @Override
    public void onOrderThrowSuccess() {

    }

    @Override
    public void onOrderMakingSuccess() {

    }

    @Override
    public void myOrderMadeSuccess(MakingRecordEntity entity, boolean isNotData) {

    }

    @Override
    public void loadMakeFinish(boolean loadType, boolean isNotData) {

    }

    @Override
    public void loadMakeState(int dataState) {

    }

    @Override
    public void myIncomeSuccess(IncomeRecordEntity entity, boolean isNotData) {

    }

    @Override
    public void loadIncomeFinish(boolean loadType, boolean isNotData) {

    }

    @Override
    public void loadIncomeState(int dataState) {

    }

    @Override
    public void onSpaceCheck(SpaceCheckEntity entity) {
        if (entity != null) {
            if (entity.getStatus() == 0) {
                showMessage("检测到您还没拥有空间站,请去获取!");
            } else {
                MySpaceStationActivity.start(this, false);
            }
        }
    }

    @Override
    public void onQuestionListSuccess(List<QuestionEntity> list) {
        if (list != null && list.size() > 0) {
            total = list.size();
            toggleType = 3;
            questionEntityList = new ArrayList<>();
            map = Preferences.getHashMapData();
            if (map != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (!map.containsKey(list.get(i).getQuestion_id() + "")) {
                        questionEntityList.add(list.get(i));
                    }
                }
            } else {
                map = new HashMap<>();
                questionEntityList = list;
            }
            for (int i = 0; i < questionEntityList.size(); i++) {
                if (map != null) {
                    questionEntityList.get(i).setPostion(map.size() + i + 1);
                } else {
                    questionEntityList.get(i).setPostion(i + 1);
                }
            }
            Log.e("11111111", map.size() + "-------------" + questionEntityList.size());
            mUnityPlayer.UnitySendMessage("Main Camera", "OpenPsychological", GsonUtil.toJson(questionEntityList));
        }
    }

    @Override
    public void onQuestionAssessSuccess(QuestionAssessEntity entity) {
        if (entity != null) {
            Preferences.putHashMapData(null);
            AnswerResultActivity.start(this, 2,true);
        }
    }

    @Override
    public void finishSuccess() {
        // 友盟统计退出
        MobclickAgent.onKillProcess(this);
        finish();
    }

    @Override
    public void onNetError() {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }
}
