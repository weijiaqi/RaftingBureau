package com.drifting.bureau.mvp.ui.activity.home;


import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.di.component.DaggerArCenterConsoleComponent;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
import com.drifting.bureau.mvp.model.entity.MakingRecordEntity;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsEntity;
import com.drifting.bureau.mvp.model.entity.OrderOneEntity;
import com.drifting.bureau.mvp.model.entity.SpaceCheckEntity;
import com.drifting.bureau.mvp.model.entity.SpaceInfoEntity;
import com.drifting.bureau.mvp.model.entity.TeamStatisticEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.ui.activity.index.DriftTrackMapActivity;
import com.drifting.bureau.mvp.ui.activity.index.MoveAwayPlanetaryActivity;
import com.drifting.bureau.mvp.ui.activity.index.PlanetarySelectActivity;
import com.drifting.bureau.mvp.ui.activity.index.TeaShopActivity;
import com.drifting.bureau.mvp.ui.activity.user.AboutMeActivity;
import com.drifting.bureau.mvp.ui.activity.user.MessageCenterActivity;
import com.drifting.bureau.mvp.ui.activity.user.WithdrawalActivity;
import com.drifting.bureau.mvp.ui.adapter.ARWithRecordAdapter;
import com.drifting.bureau.mvp.ui.adapter.IncomeRecordAdapter;
import com.drifting.bureau.mvp.ui.adapter.MakingRecordAdapter;
import com.drifting.bureau.mvp.ui.dialog.ArAnnouncementDisplayDialog;
import com.drifting.bureau.mvp.ui.dialog.DriftingPlayDialog;
import com.drifting.bureau.mvp.ui.dialog.ExclusivePlanetDialog;
import com.drifting.bureau.mvp.ui.dialog.MakeScheduleDialog;
import com.drifting.bureau.mvp.ui.dialog.MakingTeaDialog;

import com.drifting.bureau.mvp.ui.dialog.MyTreasuryDialog;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.drifting.bureau.mvp.ui.dialog.ShareDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.TakePhoto;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;

import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.view.CleanArFragment;
import com.drifting.bureau.view.chart.LineChartView;
import com.drifting.bureau.view.guide.ArGuideView;


import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ExternalTexture;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.ModelRenderable;

import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.FootprintSelectionVisualizer;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

import com.hjq.shape.view.ShapeTextView;

import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.ArCenterConsoleContract;
import com.drifting.bureau.mvp.presenter.ArCenterConsolePresenter;
import com.rb.core.xrecycleview.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.UnReadMessageManager;
import io.rong.imlib.model.Conversation;


/**
 * Created on 2022/08/20 10:53
 *
 * @author 主页AR
 * module name is ArCenterConsoleActivity
 */
public class ArCenterConsoleActivity extends BaseManagerActivity<ArCenterConsolePresenter> implements FragmentOnAttachListener, BaseArFragment.OnSessionConfigurationListener,
        CleanArFragment.OnViewCreatedListener, View.OnClickListener, ArCenterConsoleContract.View, XRecyclerView.LoadingListener {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.tv_change_mode)
    ShapeTextView mTvChangeMode;
    @BindView(R.id.tv_energy)
    TextView mTvEnergy;
    @BindView(R.id.tv_about_me)
    TextView mTvAboutMe;
    @BindView(R.id.iv_hot)
    ImageView mIvHot;
    @BindView(R.id.guide_view)
    ArGuideView mGuideView;

    @BindView(R.id.ll_explore_planet)
    LinearLayout mLlExplorePlanet;
    @BindView(R.id.main_content)
    RelativeLayout maincontent;

    @BindView(R.id.rl_close)
    RelativeLayout mRlClose;

    private ImageView mIvBorder1, mIvBorder2, mIvBorder3, mIvBorder4, mIvBorder5, mIvOperation, mIvDriftTrack, mIvStarTroopers, mIvPhysicalstore, mIvRadioNews, mIvMastor, mIvLock, mIvDrink, mIvFindPlanet, mIvExplorePlay, mIvHaveNewOrder, mIvNoneNewOrder, mIvQueryInventory;
    private XRecyclerView mRcyWithdrawalsRecord, mRcyMakeRecord, mRcyIncomeRecord;
    private LinearLayout mLlContent, mLlReceiveTea, mLlTop, mLlNewSpaceOrder;
    private FrameLayout mFlState, mFlMakeState, mFlIncomeState;
    private TextView mTvNebulaName, mTvTimeLeft, mTvNikeName, mTvIdentity, mTvPlanet, mTvShare, mTvApplyWithdrawal, mTvIncome, mTvPersons, mTvSureWithdrawal, mTvUnderReview, mTvWithdrawnCash, mTvRankingNum, mTvOrderNum, mTvFromName, mTvToName, mTvWithdrawNow, mTvTodayMake, mTvWholeMake, mTvWholeIncome, mTvWholeMake2, mTvWholeIncome2, mTvTodayMake2, mTvWithdrawal, mTvSelect, mTvTimeLine;
    private CleanArFragment arFragment;
    private HorizontalScrollView scrollView;
    private LineChartView lineChartView;
    private ArSceneView arSceneView;
    private CompletableFuture<ModelRenderable> model, model2, model3, model5, model6, model7,  model9, model10, model11;
    private CompletableFuture<ViewRenderable> viewRenderable, viewRenderable2, viewRenderable3, viewRenderable4, viewRenderable5;
    private int status = 1;
    private int id, explore_id, rcyid;
    private DriftingPlayDialog driftingPlayDialog;
    private ExclusivePlanetDialog exclusivePlanetDialog;

    private AnchorNode anchorNode;
    private Node titleNode, spacenode, recordnode, recordnode2, inventorynode, inventorynode2, expenditurenode, expenditurenode2;
    private TransformationSystem transformationSystem;
    private TransformableNode andy, andy2, andy3, andy5, andy7,andy9, andy10, andy11, andy13;
    private View view;
    private Handler handlerReciver, handlerSpace;
    private ARWithRecordAdapter arWithRecordAdapter;
    private MakingRecordAdapter makingRecordAdapter;
    private IncomeRecordAdapter incomeRecordAdapter;
    private int mPage = 1;
    private int makepage = 1;
    private int incomepage = 1;

    private UserInfoEntity userInfoEntity;
    private ShareDialog shareDialog;
    private List<LineChartView.Data> datas;
    private MoreDetailsEntity.MessageBean messageBean;
    private OrderOneEntity orderOneEntity;
    private int[] newData;
    private MakingTeaDialog makingTeaDialog;
    private MakeScheduleDialog makeScheduleDialog;
    private PublicDialog publicDialog;
    private MyTreasuryDialog myTreasuryDialog;
    private List<MoreDetailsEntity.MessagePathBean> messagePathBeanList;
    private ArAnnouncementDisplayDialog arAnnouncementDisplayDialog;
    private int[] dataArr = new int[]{152, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146};

    private TakePhoto takePhoto;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, ArCenterConsoleActivity.class);
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
        return R.layout.activity_ar_starry_sky; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        getSupportFragmentManager().addFragmentOnAttachListener(this);
        if (savedInstanceState == null) {
            if (Sceneform.isSupported(this)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.arFragment, CleanArFragment.class, null)
                        .commit();
            }
        }


        loadModels();

        //是否展示引导
        mGuideView.setVisibility(!Preferences.isArGuide() ? View.VISIBLE : View.GONE);
        mGuideView.setOnClickCallback(() -> {
            Preferences.setArGuide(true);
            mGuideView.setVisibility(View.GONE);
        });

    }

    Runnable mAdRunnable = () -> getMessage();

    //获取新消息
    public void getMessage() {
        if (mPresenter != null) {
            mPresenter.getMessage();
        }
    }


    @Override
    public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        if (fragment.getId() == R.id.arFragment) {
            arFragment = (CleanArFragment) fragment;
            arFragment.setOnSessionConfigurationListener(this);
            arFragment.setOnViewCreatedListener(this);
        }
    }

    @Override
    public void onViewCreated(ArSceneView arSceneView) {
        this.arSceneView = arSceneView;
        this.arSceneView.setMaxFramesPerSeconds(30);
        this.arSceneView.getPlaneRenderer().setEnabled(false);
        this.arSceneView.getPlaneRenderer().setVisible(false); //隐藏小白点
        this.arSceneView.setFrameRateFactor(SceneView.FrameRate.FULL);

//        ArSceneViewKt.setLightEstimationConfig(this.arSceneView, LightEstimationConfig.DISABLED);
//        HDREnvironmentKt.loadEnvironmentAsync(
//                HDRLoader.INSTANCE,
//                this,
//                "environments/winter_lake_01_2k.hdr",
//                false,
//                LifecycleOwnerKt.getLifecycleScope(this),
//                hdrEnvironment -> {
//                    float indirectLightIntensity = SceneViewKt.getEnvironment(this.arSceneView).getIndirectLight().getIntensity();
//                    SceneViewKt.setEnvironment(this.arSceneView, new Environment(hdrEnvironment.getSphericalHarmonics(), hdrEnvironment.getIndirectLight(), null));
//                    SceneViewKt.getEnvironment(this.arSceneView).getIndirectLight().setIntensity(indirectLightIntensity);
//                    return Unit.INSTANCE;
//                }
//        );
    }

    @Override
    public void onSessionConfiguration(Session session, Config config) {
        if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
            config.setDepthMode(Config.DepthMode.AUTOMATIC);
        }
    }

    public void loadModels() {
        model = ModelRenderable
                .builder()
                .setSource(this
                        , Uri.parse("models/shijieqiu.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();


        model2 = ModelRenderable.builder()
                .setSource(this, Uri.parse("models/xiaoxiong.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();
        model3 = ModelRenderable.builder()
                .setSource(this, Uri.parse("models/logo.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();
        model5 = ModelRenderable.builder()
                .setSource(this, Uri.parse("models/feichuan.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();
        model11 = ModelRenderable.builder()
                .setSource(this, Uri.parse("models/xingqiu.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();
        model7 = ModelRenderable.builder()
                .setSource(this, Uri.parse("models/shijie2.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();

        model9 = ModelRenderable.builder()
                .setSource(this, Uri.parse("models/leida.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();

        model10 = ModelRenderable.builder()
                .setSource(this, Uri.parse("models/kongjianzhan.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();


        model6 = ModelRenderable.builder()
                .setSource(this, Uri.parse("models/liaisonOfficer.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();

        viewRenderable = ViewRenderable.builder()
                .setView(this, R.layout.view_center_console)
                .build();
        viewRenderable2 = ViewRenderable.builder()
                .setView(this, R.layout.view_space_station)
                .build();
        viewRenderable3 = ViewRenderable.builder()
                .setView(this, R.layout.view_make_record)
                .build();
        viewRenderable4 = ViewRenderable.builder()
                .setView(this, R.layout.view_query_inventory)
                .build();
        viewRenderable5 = ViewRenderable.builder()
                .setView(this, R.layout.view_expenditure_record)
                .build();
        CompletableFuture.allOf(model)
                .handle((ok, ex) -> {
                    try {
                        FootprintSelectionVisualizer selectionVisualizer = new FootprintSelectionVisualizer();
                        transformationSystem = new TransformationSystem(getResources().getDisplayMetrics(), selectionVisualizer);
                        arFragment.getArSceneView().getScene().addOnPeekTouchListener((hitTestResult, motionEvent) -> {
                            transformationSystem.onTouch(hitTestResult, motionEvent);
                        });
                        anchorNode = new AnchorNode();
                        anchorNode.setParent(arFragment.getArSceneView().getScene());
                        //星球世界
                        andy = new TransformableNode(transformationSystem);
                        andy.setParent(anchorNode);
                        andy.setRenderable(model.get()).animate(true).start();
                        model.get().setShadowCaster(false);
                        andy.setWorldScale(new Vector3(0.3f, 0.3f, 0.3f));
                        andy.setLocalPosition(new Vector3(0f, 3f, -10.5f));
                        andy.getRenderableInstance().setCulling(false);
                        // 禁止缩放
                        andy.getScaleController().setEnabled(false);
                        andy.getRotationController().setEnabled(false);
                        andy.getTranslationController().setEnabled(false);
                        andy.select();
                        //中控台
                        addTitleNode();

                        //小熊
                        andy2 = new TransformableNode(transformationSystem);
                        andy2.setParent(anchorNode);
                        andy2.setRenderable(model2.get()).animate(true).start();
                        andy2.setWorldScale(new Vector3(0.5f, 0.5f, 0.5f));
                        andy2.setLocalPosition(new Vector3(0.2f, -3f, -2.5f));
                        andy2.getRenderableInstance().setCulling(false);
                        // 禁止缩放，没禁止缩放，设置的倍数会失效，自动加载默认的大小
                        andy2.getScaleController().setEnabled(false);
                        andy2.getRotationController().setEnabled(false);
                        andy2.getTranslationController().setEnabled(false);
                        andy2.select();


                        andy2.setOnTapListener(new Node.OnTapListener() {
                            @Override
                            public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                                if (!ClickUtil.isFastClick(motionEvent.getDeviceId())) {
                                    driftingPlayDialog = new DriftingPlayDialog(ArCenterConsoleActivity.this);
                                    driftingPlayDialog.show();
                                    driftingPlayDialog.setOnClickCallback(type -> {
                                        if (type == DriftingPlayDialog.OPEN_PLAY) {//开启玩法
                                            DriftTrackMapActivity.start(ArCenterConsoleActivity.this, 1, 1, 0, false);
                                        } else if (type == DriftingPlayDialog.START_SPACE) {//空间站
                                            if (mPresenter != null) {  //检测是否有空间站
                                                mPresenter.spacecheck();
                                            }
                                        }
                                    });
                                }
                            }
                        });

                        if (Preferences.isDidAttend()) {
                            showAndy9();
                        } else {
                            RequestUtil.create().didAttend(entity -> {
                                if (entity != null && entity.getCode() == 200) {
                                    if (entity.getData().getAttend() == 1) {
                                        Preferences.setDidAttend(true);
                                        showAndy9();
                                    }
                                }
                            });
                        }

                        //飞船
                        andy5 = new TransformableNode(transformationSystem);
                        andy5.setParent(anchorNode);
                        andy5.setRenderable(model5.get()).animate(true).start();
                        andy5.setWorldScale(new Vector3(0.3f, 0.3f, 0.3f));
                        andy5.setLocalPosition(new Vector3(0f, -1.5f, 0f));
                        andy5.getRenderableInstance().setCulling(false);
                        // 禁止缩放，没禁止缩放，设置的倍数会失效，自动加载默认的大小
                        andy5.getScaleController().setEnabled(false);
                        andy5.getRotationController().setEnabled(false);
                        andy5.getTranslationController().setEnabled(false);
                        andy5.select();
                        andy5.setOnTapListener((hitTestResult, motionEvent) -> {
                            if (!ClickUtil.isFastClick(motionEvent.getDeviceId())) {
                                try {
                                    andy.setEnabled(false);
                                    mRlClose.setVisibility(View.GONE);

                                    andy2.setEnabled(false);
                                    andy3.setEnabled(false);
                                    andy5.setEnabled(false);
                                    andy11.setEnabled(false);
                                    if (andy9 != null) {
                                        andy9.setEnabled(false);
                                    }
                                    status = 2;
                                    mTvChangeMode.setText("返回星环");

                                    //世界2
                                    andy7 = new TransformableNode(transformationSystem);
                                    andy7.setParent(anchorNode);
                                    andy7.setRenderable(model7.get()).animate(true).start();
                                    andy7.setLocalPosition(new Vector3(0f, -2f, 0f));
                                    andy7.getRenderableInstance().setCulling(false);
                                    // 禁止缩放，没禁止缩放，设置的倍数会失效，自动加载默认的大小
                                    andy7.getScaleController().setEnabled(false);
                                    andy7.getRotationController().setEnabled(false);
                                    andy7.getTranslationController().setEnabled(false);
                                    andy7.select();
                                    //世界联络官
                                    andy13 = new TransformableNode(transformationSystem);
                                    andy13.setParent(anchorNode);
                                    andy13.setRenderable(model6.get()).animate(true).start();
                                    andy13.setLocalPosition(new Vector3(0f, 0f, -1.226f));
                                    andy13.getRenderableInstance().setCulling(false);
                                    // 禁止缩放，没禁止缩放，设置的倍数会失效，自动加载默认的大小
                                    andy13.getScaleController().setEnabled(false);
                                    andy13.getRotationController().setEnabled(false);
                                    andy13.getTranslationController().setEnabled(false);
                                    andy13.select();
                                    andy13.setOnTapListener((hitTestResult1, motionEvent1) -> {
                                        if (!ClickUtil.isFastClick(motionEvent1.getDeviceId())) {
                                            exclusivePlanetDialog = new ExclusivePlanetDialog(ArCenterConsoleActivity.this);
                                            exclusivePlanetDialog.show();
                                            exclusivePlanetDialog.setOnClickCallback(type -> {
                                                if (type == ExclusivePlanetDialog.OPEN_PLAY) {
                                                    ArAnswerActivity.start(this, false);
                                                }
                                            });
                                        }
                                    });
                                    //移除中控台
                                    addDeleteNode();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                        //logo
                        andy3 = new TransformableNode(transformationSystem);
                        andy3.setParent(anchorNode);
                        andy3.setRenderable(model3.get()).animate(true).start();
                        andy3.setWorldScale(new Vector3(0.3f, 0.3f, 0.3f));
                        andy3.setLocalPosition(new Vector3(0f, 1f, -15f));
                        andy3.getRenderableInstance().setCulling(false);
                        // 禁止缩放，没禁止缩放，设置的倍数会失效，自动加载默认的大小
                        andy3.getScaleController().setEnabled(false);
                        andy3.getRotationController().setEnabled(false);
                        andy3.getTranslationController().setEnabled(false);
                        andy3.select();
                        andy3.setOnTapListener(new Node.OnTapListener() {
                            @Override
                            public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                                if (!ClickUtil.isFastClick(motionEvent.getDeviceId())) {
                                    arAnnouncementDisplayDialog = new ArAnnouncementDisplayDialog(ArCenterConsoleActivity.this);
                                    arAnnouncementDisplayDialog.show();
                                }
                            }
                        });

                        andy11 = new TransformableNode(transformationSystem);
                        andy11.setParent(anchorNode);
                        andy11.setRenderable(model11.get()).animate(true).start();
                        andy11.setWorldScale(new Vector3(0.1f, 0.1f, 0.1f));
                        andy11.getRenderableInstance().setCulling(false);
                        // 禁止缩放，没禁止缩放，设置的倍数会失效，自动加载默认的大小
                        andy11.getScaleController().setEnabled(false);
                        andy11.getRotationController().setEnabled(false);
                        andy11.getTranslationController().setEnabled(false);
                        andy11.select();
                        andy11.setOnTapListener((hitTestResult, motionEvent) -> {
                            if (!ClickUtil.isFastClick(motionEvent.getDeviceId())) {
                                PlanetarySelectActivity.start(this, userInfoEntity.getPlanet().getLevel(), false);
                            }
                        });

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                });

    }

    public void addDeleteNode() {
        //移除中控台
        anchorNode.removeChild(titleNode);
        addTitleNode();
    }


    public void addTitleNode() {
        try {
            titleNode = new Node();
            titleNode.setParent(anchorNode);
            titleNode.setRenderable(viewRenderable.get());
            titleNode.setWorldScale(new Vector3(0.1f, 0.1f, 0.1f));
            Quaternion rotation1 = Quaternion.axisAngle(new Vector3(0.0f, 1.0f, 0.0f), 90); // rotate y axis 90 degrees
            Quaternion rotation2 = Quaternion.axisAngle(new Vector3(1.0f, 0.0f, 0.0f), -45); // rotate x axis 90 degrees
            titleNode.setLocalRotation(Quaternion.multiply(rotation1, rotation2));
            titleNode.setWorldPosition(new Vector3(-0.3f, -0.2f, 0.1f));
            mLlContent = viewRenderable.get().getView().findViewById(R.id.ll_content);
            mIvBorder1 = viewRenderable.get().getView().findViewById(R.id.border1);
            mIvBorder2 = viewRenderable.get().getView().findViewById(R.id.border2);
            mIvBorder3 = viewRenderable.get().getView().findViewById(R.id.border3);
            mIvBorder4 = viewRenderable.get().getView().findViewById(R.id.border4);
            mIvBorder5 = viewRenderable.get().getView().findViewById(R.id.border5);
            mIvOperation = viewRenderable.get().getView().findViewById(R.id.iv_operation);
            mIvDriftTrack = viewRenderable.get().getView().findViewById(R.id.iv_drift_track);
            mIvStarTroopers = viewRenderable.get().getView().findViewById(R.id.iv_star_troopers);
            mIvPhysicalstore = viewRenderable.get().getView().findViewById(R.id.iv_physical_store);
            mIvRadioNews = viewRenderable.get().getView().findViewById(R.id.iv_radio_news);
            mIvOperation.setOnClickListener(this);
            mIvDriftTrack.setOnClickListener(this);
            mIvStarTroopers.setOnClickListener(this);
            mIvPhysicalstore.setOnClickListener(this);
            mIvRadioNews.setOnClickListener(this);
            //默认展示第一个
            setBorderVisible(1);
            arSceneView.getScene().addOnUpdateListener(new Scene.OnUpdateListener() {
                @Override
                public void onUpdate(FrameTime frameTime) {
                    titleNode.setWorldPosition(new Vector3(arSceneView.getScene().getCamera().getWorldPosition().x - 0.3f, arSceneView.getScene().getCamera().getWorldPosition().y - 0.2f, arSceneView.getScene().getCamera().getWorldPosition().z + 0.1f));
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void setBorderVisible(int type) {
        mIvBorder1.setVisibility(type == 1 ? View.VISIBLE : View.INVISIBLE);
        mIvBorder2.setVisibility(type == 2 ? View.VISIBLE : View.INVISIBLE);
        mIvBorder3.setVisibility(type == 3 ? View.VISIBLE : View.INVISIBLE);
        mIvBorder4.setVisibility(type == 4 ? View.VISIBLE : View.INVISIBLE);
        mIvBorder5.setVisibility(type == 5 ? View.VISIBLE : View.INVISIBLE);
        if (view != null) {
            mLlContent.removeView(view);
        }
        switch (type) {
            case 1:
                view = LayoutInflater.from(this).inflate(R.layout.layout_home_operation, null);
                mLlReceiveTea = view.findViewById(R.id.ll_receive_tea);
                mTvNebulaName = view.findViewById(R.id.tv_nebula_name);
                mTvTimeLeft = view.findViewById(R.id.tv_time_left);
                mTvNikeName = view.findViewById(R.id.tv_nikename);
                mTvIdentity = view.findViewById(R.id.tv_identity);
                mTvPlanet = view.findViewById(R.id.tv_planet);
                mIvMastor = view.findViewById(R.id.iv_mastor);
                mIvLock = view.findViewById(R.id.iv_lock);
                mIvDrink = view.findViewById(R.id.iv_drink);
                mIvFindPlanet = view.findViewById(R.id.iv_find_planet);
                mIvExplorePlay = view.findViewById(R.id.iv_explore_play);

                mIvLock.setOnClickListener(this);
                mIvExplorePlay.setOnClickListener(this);
                mIvFindPlanet.setOnClickListener(this);
                handlerReciver = new Handler();
                handlerReciver.postDelayed(mAdRunnable, 1000);
                getUserInfo(1);
                break;
            case 2:
                view = LayoutInflater.from(this).inflate(R.layout.layout_drift_track, null);
                scrollView = view.findViewById(R.id.scroll_view);
                mLlTop = view.findViewById(R.id.ll_top);
                lineChartView = view.findViewById(R.id.line_chart_view);
                mTvFromName = view.findViewById(R.id.tv_from_name);
                mTvToName = view.findViewById(R.id.tv_to_name);
                initListener();
                break;
            case 3:
                view = LayoutInflater.from(this).inflate(R.layout.layout_star_troopers, null);
                mTvNikeName = view.findViewById(R.id.tv_nikename);
                mIvMastor = view.findViewById(R.id.iv_mastor);
                mTvShare = view.findViewById(R.id.tv_share);
                mTvApplyWithdrawal = view.findViewById(R.id.tv_apply_withdrawal);
                mTvIncome = view.findViewById(R.id.tv_income);
                mTvPersons = view.findViewById(R.id.tv_persons);
                mTvSureWithdrawal = view.findViewById(R.id.tv_sure_withdrawal);
                mTvUnderReview = view.findViewById(R.id.tv_under_review);
                mTvWithdrawnCash = view.findViewById(R.id.withdrawn_cash);
                mRcyWithdrawalsRecord = view.findViewById(R.id.rcy_withdrawals_record);
                mFlState = view.findViewById(R.id.fl_container);
                mTvRankingNum = view.findViewById(R.id.tv_ranking_num);
                mTvOrderNum = view.findViewById(R.id.tv_order_num);

                mTvShare.setOnClickListener(this);
                mTvApplyWithdrawal.setOnClickListener(this);
                mRcyWithdrawalsRecord.setLayoutManager(new LinearLayoutManager(this));
                mRcyWithdrawalsRecord.setLoadingListener(this);
                arWithRecordAdapter = new ARWithRecordAdapter(new ArrayList<>());
                mRcyWithdrawalsRecord.setAdapter(arWithRecordAdapter);

                getUserInfo(2);
                break;
        }
        mLlContent.addView(view);
    }

    /**
     * @description 漂流轨迹
     */
    public void initListener() {
        datas = new ArrayList<>();
        getDetail(1);
    }

    public void getDetail(int type) {
        if (mPresenter != null) {
            mPresenter.moreDetails(0, type);
        }
    }


    public void getUserInfo(int type) {
        RequestUtil.create().userplayer(Preferences.getUserId(), entity -> {
            if (entity != null && entity.getCode() == 200) {
                userInfoEntity = entity.getData();
                if (type == 1) {
                    mTvNikeName.setText(entity.getData().getUser().getName());
                    mTvPlanet.setText(entity.getData().getPlanet().getName());
                    mTvIdentity.setText(entity.getData().getUser().getLevel_name());
                    GlideUtil.create().loadLongImage(ArCenterConsoleActivity.this, entity.getData().getUser().getMascot(), mIvMastor);
                    mTvEnergy.setText(userInfoEntity.getUser().getMeta_power());
                    mTvAboutMe.setText(userInfoEntity.getPlanet().getName());
                } else {
                    mTvNikeName.setText(entity.getData().getUser().getName());
                    GlideUtil.create().loadLongImage(ArCenterConsoleActivity.this, entity.getData().getUser().getMascot(), mIvMastor);
                    getTeam();
                }
            }
        });

    }


    @Override
    public void onMessageReceiveSuccess(MessageReceiveEntity entity) {
        if (entity != null && entity.getId() != null) {
            mLlReceiveTea.setVisibility(View.VISIBLE);
            if (entity.getId() != 0) {
                id = entity.getId();
                explore_id = entity.getExplore_id();
                mTvTimeLeft.setVisibility(View.VISIBLE);
                mIvLock.setVisibility(View.VISIBLE);
                mIvDrink.setImageResource(R.drawable.unlock_tea);
                mTvNebulaName.setText(getString(R.string.nebula_name, entity.getNebula_name()));
                mTvTimeLeft.setText(getString(R.string.time_left, entity.getDrift_rest() / 60 + ""));
                handlerReciver.postDelayed(mAdRunnable, 1000 * 60);
            } else {
                mTvNebulaName.setText("暂时没收到奶茶哦");
                mIvDrink.setImageResource(R.drawable.lock_tea);
                mTvTimeLeft.setVisibility(View.INVISIBLE);
                mIvLock.setVisibility(View.GONE);
                handlerReciver.postDelayed(mAdRunnable, 1000 * 10);
            }
        }
    }


    @Override
    public void loadFinish(boolean loadType, boolean isNotData) {
        if (mRcyWithdrawalsRecord == null) {
            return;
        }
        if (!loadType && isNotData) {
            mRcyWithdrawalsRecord.loadEndLine();
        } else {
            mRcyWithdrawalsRecord.refreshEndComplete();
        }
    }

    @Override
    public void loadState(int dataState) {
        if (arWithRecordAdapter.getDatas() == null || arWithRecordAdapter.getDatas().size() == 0) {
            if (dataState == ViewUtil.NOT_DATA) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_DATA);
            } else if (dataState == ViewUtil.NOT_SERVER) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_SERVER);
            } else if (dataState == ViewUtil.NOT_NETWORK) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_NETWORK);
            } else {
                ViewUtil.create().setView(mFlState);
            }
        } else {
            ViewUtil.create().setView(mFlState);
        }
    }


    @Override
    public void onRefresh() {
        if (status == 1) {
            mPage = 1;
            getData(mPage, true);
        } else {
            if (rcyid == 2) {
                makepage = 1;
                getMakeData(makepage, true);
            } else if (rcyid == 3) {
                incomepage = 1;
                getIncomeData(incomepage, true);
            }
        }
    }

    @Override
    public void onLoadMore() {
        if (status == 1) {
            getData(mPage, false);
        } else {
            if (rcyid == 2) {
                getMakeData(makepage, false);
            } else if (rcyid == 3) {
                getIncomeData(incomepage, false);
            }
        }
    }

    @Override
    public void myBillLogsSuccess(IncomeRecordEntity entity, boolean isNotData) {
        if (mPage == 1 && arWithRecordAdapter.getItemCount() != 0) {
            arWithRecordAdapter.clearData();
        }
        List<IncomeRecordEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            if (isNotData) {
                mPage = 2;
                arWithRecordAdapter.setData(list);
            } else {
                mPage++;
                arWithRecordAdapter.addData(list);
            }
        }
    }

    @Override
    public void onMoreDetailsSuccess(MoreDetailsEntity entity, int type) {
        if (entity != null) {
            messageBean = entity.getMessage();
            messagePathBeanList = entity.getMessage_path();
            if (!TextUtils.isEmpty(entity.getRelevance().getLast_attend())) {
                mLlTop.setVisibility(View.VISIBLE);
                mTvFromName.setText(messageBean.getUser_name());
                mTvToName.setText(entity.getRelevance().getLast_attend());
            } else {
                mLlTop.setVisibility(View.GONE);
            }

            if (messageBean.getId() == 0) {  //发起话题
                InitiateTopic();
            } else {
                MoreDetailsEntity.MessagePathBean messagePathBean = new MoreDetailsEntity.MessagePathBean();
                messagePathBean.setUser_id(messageBean.getUser_id());
                messagePathBean.setComment_id(-1);
                messagePathBeanList.add(0, messagePathBean);

                newData = Arrays.copyOfRange(dataArr, 0, messagePathBeanList.size() + 1);
                for (int i = 0; i < newData.length; i++) {
                    LineChartView.Data data = new LineChartView.Data(newData[i]);
                    datas.add(data);
                }
                lineChartView.setData(datas, 2);
            }

            lineChartView.setClockListener(new LineChartView.OpenMessageListener() {
                @Override
                public void onClick(int index) { //开启
                    DriftTrackMapActivity.start(ArCenterConsoleActivity.this, 1, 0, false);
                }

                @Override
                public void onOPenClick(int index) {   //点击飘来宇宙电波
                    DriftTrackMapActivity.start(ArCenterConsoleActivity.this, 1, 0, false);
                }
            });


            scrollView.post(() -> {
                //滚动到右边
                scrollView.fullScroll(ScrollView.FOCUS_RIGHT);
            });


        }
    }


    /**
     * @description 发起话题
     */

    public void InitiateTopic() {

        newData = Arrays.copyOfRange(dataArr, 0, 2);
        for (int i = 0; i < newData.length; i++) {
            LineChartView.Data data = new LineChartView.Data(newData[i]);
            datas.add(data);
        }
        lineChartView.setData(datas, 1);
    }

    @Override
    public void onNetError() {

    }

    @OnClick({R.id.tv_change_mode, R.id.rl_info, R.id.tv_about_me, R.id.ll_explore_planet, R.id.iv_photo, R.id.iv_close})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.rl_info:
                case R.id.iv_radio_news:  //电波消息
                    MessageCenterActivity.start(this, false);
                    break;
                case R.id.tv_about_me:
                    if (userInfoEntity != null) {
                        AboutMeActivity.start(this, userInfoEntity, false);
                    }
                    break;
                case R.id.iv_find_planet:  //寻找星球
                    if (userInfoEntity != null) {
                        PlanetarySelectActivity.start(this, userInfoEntity.getPlanet().getLevel(), false);
                    }
                    break;
                case R.id.ll_explore_planet:   //开启探寻
                    MoveAwayPlanetaryActivity.start(ArCenterConsoleActivity.this, 1, false);
                    break;
                case R.id.iv_explore_play:  //探索玩法
                    if (status == 1) {
                        ToastUtil.showToast("当前正在探索...");
                    } else {
                        nextMainNode();
                    }
                    break;
                case R.id.tv_change_mode:  //返回星环
                    if (status == 1) {
                        Preferences.setARModel(false);
                        DiscoveryTourActivity.start(this, true);
                    } else {
                        nextMainNode();
                    }
                    break;
                case R.id.iv_operation:  //主屏操作
                    setBorderVisible(1);
                    break;
                case R.id.iv_drift_track:  //漂流轨迹
                    setBorderVisible(2);
                    break;
                case R.id.iv_star_troopers:  //星际战队
                    setBorderVisible(3);
                    break;
                case R.id.iv_physical_store:  //实体门店
                    TeaShopActivity.start(this, false);
                    break;
                case R.id.iv_lock: //解锁奶茶
                    DriftTrackMapActivity.start(this, explore_id, id, false);
                    break;
                case R.id.tv_share: //分享
                    if (userInfoEntity != null) {
                        shareDialog = new ShareDialog(this, userInfoEntity);
                        shareDialog.show();
                    }
                    break;
                case R.id.tv_apply_withdrawal: //申请提现
                    if (!TextUtils.isEmpty(mTvSureWithdrawal.getText().toString())) {
                        WithdrawalActivity.start(this, 2, mTvSureWithdrawal.getText().toString(), false);
                    }
                    break;
                case R.id.tv_withdraw_now:  //立即提现
                    if (!TextUtils.isEmpty(mTvWithdrawal.getText().toString())) {
                        WithdrawalActivity.start(this, 1, mTvWithdrawal.getText().toString(), false);
                    }
                    break;
                case R.id.tv_select:   //查看空间站消息
                    if (mPresenter != null & orderOneEntity != null) {
                        mPresenter.details(orderOneEntity.getLog_id(), orderOneEntity.getLevel(), orderOneEntity.getUser_id());
                    }
                    break;
                case R.id.iv_query_inventory:  //查询库藏
                    myTreasuryDialog = new MyTreasuryDialog(this);
                    myTreasuryDialog.show();
                    break;
                case R.id.iv_photo://拍照
                    takePhoto = new TakePhoto();
                    takePhoto.takePhoto(arFragment.getArSceneView(), ArCenterConsoleActivity.this, maincontent);
                    break;
                case R.id.iv_close: //关闭拍照
                    mRlClose.setVisibility(View.GONE);
                    break;

            }
        }
    }

    /**
     * @description跳回到主场景
     */
    public void nextMainNode() {
        mTvChangeMode.setText("转换普通模式");
        andy.setEnabled(true);
        andy5.setEnabled(true);
        andy2.setEnabled(true);
        andy3.setEnabled(true);
        andy11.setEnabled(true);
        if (andy9 != null) {
            andy9.setEnabled(true);
        }
        if (status == 2) {
            mLlExplorePlanet.setVisibility(View.GONE);
            anchorNode.removeChild(andy7);
            anchorNode.removeChild(andy13);
        } else if (status == 3) {  //空间站
            anchorNode.removeChild(spacenode);
            anchorNode.removeChild(recordnode);
            anchorNode.removeChild(inventorynode);
            anchorNode.removeChild(expenditurenode);
            anchorNode.removeChild(recordnode2);
            anchorNode.removeChild(inventorynode2);
            anchorNode.removeChild(expenditurenode2);
            anchorNode.removeChild(andy10);
            if (handlerSpace != null) {
                handlerSpace.removeCallbacks(mSpceRunnable);
            }
        }
        status = 1;
        addDeleteNode();
    }


    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    public void getTeam() {
        if (mPresenter != null) {
            mPresenter.team();
        }
    }

    @Override
    public void OnTeamSuccess(TeamStatisticEntity entity) {
        if (entity != null) {
            mTvIncome.setText(entity.getTotal_income());
            mTvPersons.setText(entity.getPeople() + "");
            mTvSureWithdrawal.setText(entity.getWithdrawable());
            mTvUnderReview.setText(entity.getAuditing());
            mTvWithdrawnCash.setText(entity.getWithdrawn());
            mTvRankingNum.setText(entity.getRanking() + "");
            mTvOrderNum.setText(entity.getOrder_num() + "");
            mPage = 1;
            getData(mPage, true);
        }
    }

    /**
     * @description 提现记录
     */
    public void getData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.withdrawnLogs(mPage,  loadType);
        }
    }

    /**
     * @description 制作记录
     */
    public void getMakeData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.ordermadelog(mPage, loadType);
        }
    }


    /**
     * @description 收支记录
     */
    public void getIncomeData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.spacebillogs(mPage, loadType);
        }
    }

    /**
     * @description 我的空间站信息
     */

    public void getInfo() {
        if (mPresenter != null) {
            mPresenter.spaceinfo(Preferences.getUserId());
        }
    }

    Runnable mSpceRunnable = () -> getOrderOne();

    /**
     * @description 空间站新消息查看
     */
    public void getOrderOne() {
        if (mPresenter != null) {
            mPresenter.orderone();
        }
    }

    @Override
    public void onSpcaeInfoSuccess(SpaceInfoEntity entity) {
        if (entity != null) {
            mTvTodayMake.setText(entity.getToday_make() + "");
            mTvTodayMake2.setText(entity.getToday_make() + "");
            mTvWholeMake.setText(entity.getTotal_make() + "");
            mTvWholeMake2.setText(entity.getTotal_make() + "");
            mTvWholeIncome.setText(StringUtil.frontValue(entity.getTotal_income()));
            mTvWholeIncome2.setText(StringUtil.frontValue(entity.getTotal_income()));
            mTvWithdrawal.setText(StringUtil.frontValue(entity.getWithdrawable()));
        }
    }

    @Override
    public void onOrderOneSuccess(OrderOneEntity entity) {
        if (entity != null) {
            orderOneEntity = entity;
            if (orderOneEntity.getTimeout() == 0) {
                setOrderGone(true);
                handlerSpace.postDelayed(mSpceRunnable, 1000 * 10);
            } else {
                setOrderGone(false);
                mTvTimeLine.setText(DateUtil.TimeRemaining(orderOneEntity.getTimeout()) + "后将消失");
                handlerSpace.postDelayed(mSpceRunnable, orderOneEntity.getTimeout() * 1000);
            }
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
    public void onOrderThrowSuccess() { //丢回太空
        makingTeaDialog.dismiss();
        showDialog(2, "订单已丢回太空", "已经把该订单丢回太空\n该订单的收益将无法拥有");
    }

    @Override
    public void onOrderMakingSuccess() { //制作成功
        showDialog(1, "制作完成", "已经制作完成并发往太空了\n拥有该订单带来的收益");
    }

    @Override
    public void myOrderMadeSuccess(MakingRecordEntity entity, boolean isNotData) {
        if (makepage == 1 && makingRecordAdapter.getItemCount() != 0) {
            makingRecordAdapter.clearData();
        }
        List<MakingRecordEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            if (isNotData) {
                makepage = 2;
                makingRecordAdapter.setData(list);
            } else {
                makepage++;
                makingRecordAdapter.addData(list);
            }
        }
    }

    @Override
    public void loadMakeFinish(boolean loadType, boolean isNotData) {
        if (mRcyMakeRecord == null) {
            return;
        }
        if (!loadType && isNotData) {
            mRcyMakeRecord.loadEndLine();
        } else {
            mRcyMakeRecord.refreshEndComplete();
        }
    }

    @Override
    public void loadMakeState(int dataState) {
        if (makingRecordAdapter.getDatas() == null || makingRecordAdapter.getDatas().size() == 0) {
            if (dataState == ViewUtil.NOT_DATA) {
                ViewUtil.create().setView(this, mFlMakeState, ViewUtil.NOT_DATA);
            } else if (dataState == ViewUtil.NOT_SERVER) {
                ViewUtil.create().setView(this, mFlMakeState, ViewUtil.NOT_SERVER);
            } else if (dataState == ViewUtil.NOT_NETWORK) {
                ViewUtil.create().setView(this, mFlMakeState, ViewUtil.NOT_NETWORK);
            } else {
                ViewUtil.create().setView(mFlMakeState);
            }
        } else {
            ViewUtil.create().setView(mFlMakeState);
        }
    }

    @Override
    public void myIncomeSuccess(IncomeRecordEntity entity, boolean isNotData) {
        if (incomepage == 1 && incomeRecordAdapter.getItemCount() != 0) {
            incomeRecordAdapter.clearData();
        }
        List<IncomeRecordEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            if (isNotData) {
                incomepage = 2;
                incomeRecordAdapter.setData(list);
            } else {
                incomepage++;
                incomeRecordAdapter.addData(list);
            }
        }
    }

    @Override
    public void loadIncomeFinish(boolean loadType, boolean isNotData) {
        if (mRcyIncomeRecord == null) {
            return;
        }
        if (!loadType && isNotData) {
            mRcyIncomeRecord.loadEndLine();
        } else {
            mRcyIncomeRecord.refreshEndComplete();
        }
    }

    @Override
    public void loadIncomeState(int dataState) {
        if (incomeRecordAdapter.getDatas() == null || incomeRecordAdapter.getDatas().size() == 0) {
            if (dataState == ViewUtil.NOT_DATA) {
                ViewUtil.create().setView(this, mFlIncomeState, ViewUtil.NOT_DATA);
            } else if (dataState == ViewUtil.NOT_SERVER) {
                ViewUtil.create().setView(this, mFlIncomeState, ViewUtil.NOT_SERVER);
            } else if (dataState == ViewUtil.NOT_NETWORK) {
                ViewUtil.create().setView(this, mFlIncomeState, ViewUtil.NOT_NETWORK);
            } else {
                ViewUtil.create().setView(mFlState);
            }
        } else {
            ViewUtil.create().setView(mFlState);
        }
    }

    @Override
    public void onSpaceCheck(SpaceCheckEntity entity) {
        if (entity != null) {
            if (entity.getStatus() == 0) {
                showMessage("检测到您还没拥有空间站,请去获取!");
            } else {
                status = 3;
                mTvChangeMode.setText("返回星环");
                //隐藏节点
                andy.setEnabled(false);
                mRlClose.setVisibility(View.GONE);
                andy2.setEnabled(false);
                andy3.setEnabled(false);
                andy5.setEnabled(false);
                andy11.setEnabled(false);
                if (andy9 != null) {
                    andy9.setEnabled(false);
                }
                //空间站
                andy10 = new TransformableNode(transformationSystem);
                andy10.setParent(anchorNode);
                try {
                    andy10.setRenderable(model10.get()).animate(true).start();
                    // 禁止缩放，没禁止缩放，设置的倍数会失效，自动加载默认的大小
                    andy10.getScaleController().setEnabled(false);
                    andy10.getRotationController().setEnabled(false);
                    andy10.getTranslationController().setEnabled(false);
                    andy10.select();
                    //移除中控台
                    addDeleteNode();

                    spacenode = new Node();
                    spacenode.setParent(anchorNode);
                    spacenode.setRenderable(viewRenderable2.get());
                    spacenode.setWorldScale(new Vector3(1.42f, 1.42f, 3f));
                    spacenode.setWorldPosition(new Vector3(0.05f, -1.66f, -4f));
                    mTvWithdrawNow = viewRenderable2.get().getView().findViewById(R.id.tv_withdraw_now);
                    mTvTodayMake = viewRenderable2.get().getView().findViewById(R.id.tv_today_make);
                    mTvTodayMake2 = viewRenderable2.get().getView().findViewById(R.id.tv_today_make2);
                    mTvWholeMake = viewRenderable2.get().getView().findViewById(R.id.tv_whole_make);
                    mTvWholeMake2 = viewRenderable2.get().getView().findViewById(R.id.tv_whole_make2);
                    mTvWholeIncome = viewRenderable2.get().getView().findViewById(R.id.tv_whole_income);
                    mTvWholeIncome2 = viewRenderable2.get().getView().findViewById(R.id.tv_whole_income2);
                    mTvWithdrawal = viewRenderable2.get().getView().findViewById(R.id.tv_withdrawal);
                    mIvNoneNewOrder = viewRenderable2.get().getView().findViewById(R.id.iv_none_new_order);
                    mIvHaveNewOrder = viewRenderable2.get().getView().findViewById(R.id.iv_have_new_order);
                    mLlNewSpaceOrder = viewRenderable2.get().getView().findViewById(R.id.ll_new_space_order);
                    mTvSelect = viewRenderable2.get().getView().findViewById(R.id.tv_select);
                    mTvTimeLine = viewRenderable2.get().getView().findViewById(R.id.tv_timeliness);
                    mTvWithdrawNow.setOnClickListener(ArCenterConsoleActivity.this);
                    mTvSelect.setOnClickListener(ArCenterConsoleActivity.this);

                    recordnode = new Node();
                    recordnode.setParent(anchorNode);
                    recordnode.setRenderable(viewRenderable3.get());
                    recordnode.setWorldScale(new Vector3(0.8f, 0.8f, 0.8f));
                    Quaternion rotation1 = Quaternion.axisAngle(new Vector3(0.0f, 1f, 0f), 54);
                    Quaternion rotation2 = Quaternion.axisAngle(new Vector3(1f, 0f, 0f), -30);
                    recordnode.setLocalRotation(Quaternion.multiply(rotation1, rotation2));
                    recordnode.setWorldPosition(new Vector3(-3.5f, -1.1f, -1.45f));
                    mFlMakeState = viewRenderable3.get().getView().findViewById(R.id.fl_container);
                    mRcyMakeRecord = viewRenderable3.get().getView().findViewById(R.id.rcy_make_record);
                    recordnode.setOnTouchListener((hitTestResult1, motionEvent1) -> {
                        rcyid = 2;
                        return false;
                    });


                    inventorynode = new Node();
                    inventorynode.setParent(anchorNode);
                    inventorynode.setRenderable(viewRenderable4.get());
                    inventorynode.setWorldScale(new Vector3(0.8f, 0.8f, 0.8f));
                    Quaternion rotation3 = Quaternion.axisAngle(new Vector3(0.0f, 1f, 0f), 100);
                    Quaternion rotation4 = Quaternion.axisAngle(new Vector3(1f, 0f, 0f), -35);
                    inventorynode.setLocalRotation(Quaternion.multiply(rotation3, rotation4));
                    inventorynode.setLocalPosition(new Vector3(-4.2f, -1.05f, 1f));
                    mIvQueryInventory = viewRenderable4.get().getView().findViewById(R.id.iv_query_inventory);
                    mIvQueryInventory.setOnClickListener(ArCenterConsoleActivity.this);


                    expenditurenode = new Node();
                    expenditurenode.setParent(anchorNode);
                    expenditurenode.setRenderable(viewRenderable5.get());
                    expenditurenode.setWorldScale(new Vector3(0.8f, 0.8f, 0.8f));
                    Quaternion rotation5 = Quaternion.axisAngle(new Vector3(0.0f, 1f, 0f), 120);
                    Quaternion rotation6 = Quaternion.axisAngle(new Vector3(1f, 0f, 0f), -35);
                    expenditurenode.setLocalRotation(Quaternion.multiply(rotation5, rotation6));
                    expenditurenode.setLocalPosition(new Vector3(-3.3f, -1.05f, 3.4f));

                    mFlIncomeState = viewRenderable5.get().getView().findViewById(R.id.fl_container);
                    mRcyIncomeRecord = viewRenderable5.get().getView().findViewById(R.id.rcy_income_record);
                    expenditurenode.setOnTouchListener((hitTestResult1, motionEvent1) -> {
                        rcyid = 3;
                        return false;
                    });


                    recordnode2 = new Node();
                    recordnode2.setParent(anchorNode);
                    recordnode2.setRenderable(viewRenderable3.get());
                    recordnode2.setWorldScale(new Vector3(0.8f, 0.8f, 0.8f));
                    Quaternion rotation7 = Quaternion.axisAngle(new Vector3(0.0f, 1f, 0f), -54);
                    Quaternion rotation8 = Quaternion.axisAngle(new Vector3(1f, 0f, 0f), -30);
                    recordnode2.setLocalRotation(Quaternion.multiply(rotation7, rotation8));
                    recordnode2.setWorldPosition(new Vector3(3.5f, -1.1f, -1.45f));

                    inventorynode2 = new Node();
                    inventorynode2.setParent(anchorNode);
                    inventorynode2.setRenderable(viewRenderable4.get());
                    inventorynode2.setWorldScale(new Vector3(0.8f, 0.8f, 0.8f));
                    Quaternion rotation9 = Quaternion.axisAngle(new Vector3(0.0f, 1f, 0f), -100);
                    Quaternion rotation10 = Quaternion.axisAngle(new Vector3(1f, 0f, 0f), -35);
                    inventorynode2.setLocalRotation(Quaternion.multiply(rotation9, rotation10));
                    inventorynode2.setLocalPosition(new Vector3(4.2f, -1.05f, 1f));


                    expenditurenode2 = new Node();
                    expenditurenode2.setParent(anchorNode);
                    expenditurenode2.setRenderable(viewRenderable5.get());
                    expenditurenode2.setWorldScale(new Vector3(0.8f, 0.8f, 0.8f));
                    Quaternion rotation11 = Quaternion.axisAngle(new Vector3(0.0f, 1f, 0f), -120);
                    Quaternion rotation12 = Quaternion.axisAngle(new Vector3(1f, 0f, 0f), -35);
                    expenditurenode2.setLocalRotation(Quaternion.multiply(rotation11, rotation12));
                    expenditurenode2.setLocalPosition(new Vector3(3.3f, -1.05f, 3.4f));

                    handlerSpace = new Handler();
                    handlerSpace.postDelayed(mSpceRunnable, 60);
                    getInfo();

                    mRcyMakeRecord.setLayoutManager(new GridLayoutManager(ArCenterConsoleActivity.this, 2));
                    mRcyMakeRecord.setLoadingListener(ArCenterConsoleActivity.this);
                    makingRecordAdapter = new MakingRecordAdapter(new ArrayList<>(), 2);
                    mRcyMakeRecord.setAdapter(makingRecordAdapter);
                    getMakeData(makepage, true);

                    mRcyIncomeRecord.setLayoutManager(new LinearLayoutManager(ArCenterConsoleActivity.this));
                    mRcyIncomeRecord.setLoadingListener(ArCenterConsoleActivity.this);
                    incomeRecordAdapter = new IncomeRecordAdapter(new ArrayList<>(), 2);
                    mRcyIncomeRecord.setAdapter(incomeRecordAdapter);
                    getIncomeData(incomepage, true);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @description 展示雷达
     */

    public void showAndy9() {
        //雷达
        try {
            andy9 = new TransformableNode(transformationSystem);
            andy9.setParent(anchorNode);
            andy9.setRenderable(model9.get()).animate(true).start();
            andy9.setWorldScale(new Vector3(0.2f, 0.2f, 0.2f));
            andy9.setLocalPosition(new Vector3(5f, -1f, -10f));
            // 禁止缩放，没禁止缩放，设置的倍数会失效，自动加载默认的大小
            andy9.getScaleController().setEnabled(false);
            andy9.getRotationController().setEnabled(false);
            andy9.getTranslationController().setEnabled(false);
            andy9.select();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
                    getInfo();
                }
                handlerSpace.postDelayed(mSpceRunnable, 60);
            }
        });
    }

    public void setOrderGone(boolean type) {
        mIvHaveNewOrder.setVisibility(type ? View.INVISIBLE : View.VISIBLE);
        mLlNewSpaceOrder.setVisibility(type ? View.INVISIBLE : View.VISIBLE);
        mIvNoneNewOrder.setVisibility(type ? View.VISIBLE : View.INVISIBLE);
    }


    @Override
    public void onResume() {
        super.onResume();
        RongIM.getInstance().addUnReadMessageCountChangedObserver(observer, Conversation.ConversationType.PRIVATE);
        if (handlerReciver != null) {
            handlerReciver.postDelayed(mAdRunnable, 1000);
        }
        if (handlerSpace != null) {
            handlerSpace.postDelayed(mSpceRunnable, 60);
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
                if (entity1.getData().getIndex_msg() == 0) {
                    mIvHot.setVisibility(View.GONE);
                } else {
                    mIvHot.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        removeHandler();
    }


    @Override
    public void finishSuccess() {
        // 友盟统计退出
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
        removeHandler();
        RequestUtil.create().disDispose();
    }

    public void removeHandler() {
        //移除监听，防止内存泄漏
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(observer);
        if (handlerReciver != null) {
            handlerReciver.removeCallbacks(mAdRunnable);
        }
        if (handlerSpace != null) {
            handlerSpace.removeCallbacks(mSpceRunnable);
        }
    }

}