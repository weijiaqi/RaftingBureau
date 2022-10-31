package com.drifting.bureau.mvp.ui.activity.unity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.data.event.PaymentEvent;
import com.drifting.bureau.di.component.DaggerArCenterConsoleComponent;
import com.drifting.bureau.mvp.contract.ArCenterConsoleContract;
import com.drifting.bureau.mvp.model.entity.BoxEntity;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderOpenBoxEntity;
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
import com.drifting.bureau.mvp.ui.activity.home.NewDiscoveryTourActivity;
import com.drifting.bureau.mvp.ui.activity.index.AnswerResultActivity;
import com.drifting.bureau.mvp.ui.activity.index.DriftTrackMapActivity;
import com.drifting.bureau.mvp.ui.activity.pay.PaymentInfoActivity;
import com.drifting.bureau.mvp.ui.activity.user.AboutMeActivity;
import com.drifting.bureau.mvp.ui.activity.user.IncomeRecordActivity;
import com.drifting.bureau.mvp.ui.activity.user.MakingRecordActivity;
import com.drifting.bureau.mvp.ui.activity.user.MessageCenterActivity;
import com.drifting.bureau.mvp.ui.activity.user.WithdrawalActivity;
import com.drifting.bureau.mvp.ui.activity.web.ShowWebViewActivity;
import com.drifting.bureau.mvp.ui.dialog.ArAnnouncementDisplayDialog;
import com.drifting.bureau.mvp.ui.dialog.BoxPasswordDialog;
import com.drifting.bureau.mvp.ui.dialog.DriftingPlayDialog;
import com.drifting.bureau.mvp.ui.dialog.EnablePrivilegesDialog;
import com.drifting.bureau.mvp.ui.dialog.ExclusivePlanetDialog;
import com.drifting.bureau.mvp.ui.dialog.JumpPlanetDialog;
import com.drifting.bureau.mvp.ui.dialog.MakeScheduleDialog;
import com.drifting.bureau.mvp.ui.dialog.MakingTeaDialog;
import com.drifting.bureau.mvp.ui.dialog.MyTreasuryDialog;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.drifting.bureau.mvp.ui.dialog.WelfareVoucherDialog;
import com.drifting.bureau.mvp.ui.dialog.WinningAddressDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GsonUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.di.component.AppComponent;
import com.umeng.analytics.MobclickAgent;
import com.unity3d.player.IUnityPlayerLifecycleEvents;
import com.unity3d.player.MultiWindowSupport;
import com.unity3d.player.UnityPlayer;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private MakingTeaDialog makingTeaDialog;
    private MakeScheduleDialog makeScheduleDialog;
    private JumpPlanetDialog jumpPlanetDialog;
    private MyTreasuryDialog myTreasuryDialog;
    private BoxPasswordDialog boxPasswordDialog;
    private WelfareVoucherDialog welfareVoucherDialog;
    private WinningAddressDialog winningAddressDialog;
    private EnablePrivilegesDialog enablePrivilegesDialog;
    private PublicDialog publicDialog;
    private List<QuestionEntity> questionEntityList;
    private Map<String, String> map;
    private int questionid, total, paixitype, keys, types, is_kongtous;
    private String value;
    private OrderOneEntity orderOneEntity;
    private SpaceInfoEntity spaceInfoEntity;

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
            getBox();
        }, 4500);
    }


    /**
     * @description 获取盲盒列表
     * @time 14:28 14:28
     */

    public void getBox() {
        if (mPresenter != null) {
            mPresenter.getBox();
        }
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

    @OnClick({R.id.tv_change_mode, R.id.rl_info, R.id.tv_about_me, R.id.rl_right})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.rl_right:
                    ShowWebViewActivity.start(this, 4, false);
                    break;
                case R.id.tv_change_mode:
                    if (toggleType == 4) {
                        toggleType = 1;
                        mUnityPlayer.UnitySendMessage("Main Camera", "CloseKongJianZhan", "");
                    } else if (toggleType == 3) {
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
                        NewDiscoveryTourActivity.start(this, 1, true);
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
        if (entity != null) {
            toggleType = 4;
            spaceInfoEntity = entity;
            mUnityPlayer.UnitySendMessage("Main Camera", "OpenKongJianZhan", GsonUtil.toJson(entity));
            mPresenter.orderone();
        }
    }

    @Override
    public void onOrderOneSuccess(OrderOneEntity entity) {
        if (entity != null) {
            orderOneEntity = entity;
            mUnityPlayer.UnitySendMessage("Main Camera", "DaPingDingDan", GsonUtil.toJson(entity));
            mPresenter.spacebillogs(1, true);
        }
    }

    /**
     * @description 查看订单消息
     */
    public void ChaKanDingDan() {
        if (mPresenter != null & orderOneEntity != null) {
            mPresenter.details(orderOneEntity.getLog_id(), orderOneEntity.getLevel(), orderOneEntity.getUser_id());
        }
    }

    /**
     * @description 立即提现
     */
    public void LiJiTiXian() {
        if (!TextUtils.isEmpty(StringUtil.frontValue(spaceInfoEntity.getWithdrawable()))) {
            WithdrawalActivity.start(this, 1, StringUtil.frontValue(spaceInfoEntity.getWithdrawable()), false);
        }
    }

    @Override
    public void onCommentDetailsSuccess(CommentDetailsEntity entity) {
        if (entity != null) {
            makingTeaDialog = new MakingTeaDialog(this, entity);
            makingTeaDialog.show();
            if (orderOneEntity != null) {
                makingTeaDialog.setOnClickCallback(type -> {
                    if (type == MakingTeaDialog.SELECT_CANCEL) { //丢回太空
                        if (mPresenter != null) {
                            mPresenter.orderthrow(orderOneEntity.getSpace_order_id());
                        }
                    } else if (type == MakingTeaDialog.SELECT_FINISH) { //为他制作
                        makeScheduleDialog = new MakeScheduleDialog(this);
                        makeScheduleDialog.show();
                        makeScheduleDialog.setCancelable(false);
                        makeScheduleDialog.setOnClickCallback(type1 -> {
                            if (type1 == MakingTeaDialog.SELECT_FINISH) {
                                if (mPresenter != null) {
                                    mPresenter.ordermaking(orderOneEntity.getSpace_order_id());
                                }
                            }
                        });
                    }
                });
            }
        }

    }

    @Override
    public void onOrderThrowSuccess() {//丢回太空
        makingTeaDialog.dismiss();
        showDialog(2, "订单已丢回太空", "已经把该订单丢回太空\n该订单的收益将无法拥有");
    }

    @Override
    public void onOrderMakingSuccess() {//制作成功
        showDialog(1, "制作完成", "已经制作完成并发往太空了\n拥有该订单带来的收益");
    }


    public void showDialog(int status, String title, String content) {
        publicDialog = new PublicDialog(this);
        publicDialog.show();
        publicDialog.setCancelable(false);
        publicDialog.setTitleText(title);
        publicDialog.setContentText(content);
        publicDialog.setOnClickCallback(type -> {
            if (type == PublicDialog.SELECT_FINISH) {
                if (status == 1) {
                    mPresenter.spaceinfo(Preferences.getUserId());
                } else {
                    mPresenter.orderone();
                }
            }
        });
    }

    @Override
    public void myOrderMadeSuccess(MakingRecordEntity entity, boolean isNotData) {
        List<MakingRecordEntity.ListBean> list = entity.getList();
        if (list.size() > 0) {
            mUnityPlayer.UnitySendMessage("Main Camera", "ZhiZuoJilu", GsonUtil.toJson(list));
        }
    }

    /**
     * @Description: 查看制作记录
     * @Author : WeiJiaQI
     * @Time : 2022/10/13 19:34
     */
    public void ChaKanZhiZuoJiLu() {
        MakingRecordActivity.start(this, false);
    }


    /**
     * @Description: 查询库藏
     * @Author : WeiJiaQI
     * @Time : 2022/10/13 19:34
     */
    public void ChaXunKuCun() {
        runOnUiThread(() -> {
            if (myTreasuryDialog == null) {
                myTreasuryDialog = new MyTreasuryDialog(this);
            }
            myTreasuryDialog.show();
        });
    }

    @Override
    public void loadMakeFinish(boolean loadType, boolean isNotData) {

    }

    @Override
    public void loadMakeState(int dataState) {

    }

    @Override
    public void myIncomeSuccess(IncomeRecordEntity entity, boolean isNotData) {
        if (entity != null) {
            List<IncomeRecordEntity.ListBean> list = entity.getList();
            if (list.size() > 0) {
                mUnityPlayer.UnitySendMessage("Main Camera", "ShouZhiJiLu", GsonUtil.toJson(list));
            }
            mPresenter.ordermadelog(1, true);
        }
    }

    /**
     * @Description: 查看收支记录
     * @Author : WeiJiaQI
     * @Time : 2022/10/13 19:34
     */
    public void ChaKanShouZhiJiLu() {
        IncomeRecordActivity.start(this, false);
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
//                MySpaceStationActivity.start(this, false);
                mPresenter.spaceinfo(Preferences.getUserId());
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
            mUnityPlayer.UnitySendMessage("Main Camera", "OpenPsychological", GsonUtil.toJson(questionEntityList));
        }
    }

    @Override
    public void onQuestionAssessSuccess(QuestionAssessEntity entity) {
        if (entity != null) {
            Preferences.putHashMapData(null);
            AnswerResultActivity.start(this, 2, true);
        }
    }


    /**
     * @description 点击盲盒
     * @author 卫佳琪1
     * @time 14:00 14:00
     */
    public void DianJiMangHe(int key, int type, int equity, int is_kongtou) {
        this.keys = key;
        this.types = type;
        this.is_kongtous = is_kongtou;
        if (types == 1) {  //免费
            runOnUiThread(() -> {
                boxPasswordDialog = new BoxPasswordDialog(this);
                boxPasswordDialog.show();
                boxPasswordDialog.setOnContentClickCallback(content -> {
                    openBox(keys, types, content, is_kongtous);
                });
            });
        } else {
            if (equity == 1) {  //有锁
                runOnUiThread(() -> {
                    enablePrivilegesDialog = new EnablePrivilegesDialog(this);
                    enablePrivilegesDialog.show();
                    enablePrivilegesDialog.setOnClickCallback(type2 -> {
                        if (type2 == EnablePrivilegesDialog.OPEN_PRIVILEGE) {
                            if (mPresenter != null) {
                                mPresenter.createOrderOpenBoxDaily(keys + "", 1);
                            }
                        }
                    });
                });
            } else {
                openBox(keys, types, "", is_kongtous);
            }
        }
    }


    public void openBox(int key, int type, String content, int is_kongtou) {
        RequestUtil.create().openbox(key, type, content, is_kongtou, entity1 -> {
            if (entity1.getCode() == 200) {
                if (boxPasswordDialog != null) {
                    boxPasswordDialog.dismiss();
                }

                //刷新展示盲盒列表
                getBox();

                if (entity1.getData().getIs_fictitious() == 1) {  //虚拟
                    welfareVoucherDialog = new WelfareVoucherDialog(this, entity1.getData());
                    welfareVoucherDialog.show();
                } else {  //实物
                    winningAddressDialog = new WinningAddressDialog(this, entity1.getData().getImage());
                    winningAddressDialog.show();
                    winningAddressDialog.setOnAddressClickCallback((name, phone, address) -> {
                        RequestUtil.create().addexpress(entity1.getData().getSafe_box_open_record_id(), name, phone, address, entity2 -> {
                            if (entity2 != null && entity2.getCode() == 200) {
                                winningAddressDialog.dismiss();
                                publicDialog = new PublicDialog(this);
                                publicDialog.show();
                                publicDialog.setCancelable(false);
                                publicDialog.setTitleText("恭喜");
                                publicDialog.setContentText("您的奖品已发放");
                                publicDialog.setButtonText("确定");
                            } else {
                                showMessage(entity2.getMsg());
                            }
                        });
                    });
                }
            } else {
                showMessage(entity1.getMsg());
            }
        });

    }


    @Override
    public void OnBoxSuccess(List<BoxEntity> list) {
        if (list.size() > 0) {
            mUnityPlayer.UnitySendMessage("Main Camera", "OpenMangHe", GsonUtil.toJson(list.subList(0, 10)));
        }
    }

    @Override
    public void OnCreateOrderOpenBoxDailySuccess(CreateOrderOpenBoxEntity entity) {
        if (entity != null) {
            PaymentInfoActivity.start(this, 3, entity.getSn(), entity.getTotal_amount(), false);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PaymentEvent(PaymentEvent event) {  //购买成功回调
        if (event != null) {
            openBox(keys, types, "", is_kongtous);
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
