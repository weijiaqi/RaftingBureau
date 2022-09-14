package com.drifting.bureau.mvp.ui.activity.home;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;


import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerArAnswerComponent;
import com.drifting.bureau.mvp.contract.MoveAwayPlanetaryContract;
import com.drifting.bureau.mvp.model.entity.AnswerEntity;
import com.drifting.bureau.mvp.model.entity.QuestionAssessEntity;
import com.drifting.bureau.mvp.model.entity.QuestionEntity;
import com.drifting.bureau.mvp.presenter.MoveAwayPlanetaryPresenter;
import com.drifting.bureau.mvp.ui.adapter.ArAnswerAdapter;
import com.drifting.bureau.mvp.ui.adapter.manager.ArCardLayoutManager;
import com.drifting.bureau.mvp.ui.dialog.AttributeResultsDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.view.CleanArFragment;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;

import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.FootprintSelectionVisualizer;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;
import kotlin.Unit;

/**
 * Created on 2022/09/07 10:37
 *
 * @author AR 答题
 * module name is ArAnswerActivity
 */
public class ArAnswerActivity extends BaseActivity<MoveAwayPlanetaryPresenter> implements MoveAwayPlanetaryContract.View, FragmentOnAttachListener, BaseArFragment.OnSessionConfigurationListener, CleanArFragment.OnViewCreatedListener {

    @BindView(R.id.rl_right)
    RelativeLayout mRlRight;
    @BindView(R.id.tv_change_mode)
    TextView mTvChangeMode;
    private RecyclerView mRcyAnswer;
    private ImageView mIvCofim;
    private ProgressBar mPrUploadValue;
    private ArAnswerAdapter arAnswerAdapter;
    private CleanArFragment arFragment;
    private TransformationSystem transformationSystem;
    private TransformableNode andy, andy2;
    private ArSceneView arSceneView;
    private CompletableFuture<ModelRenderable> model, model2, model3, model4, model5, model6, model7, model8, model9;
    private AnchorNode anchorNode;
    private Node answerNode;

    private List<QuestionEntity> questionEntityList;

    private List<AnswerEntity> infos;
    private CompletableFuture<ViewRenderable> viewRenderable;

    private Map<String, String> map;
    private AttributeResultsDialog attributeResultsDialog;

    private int total;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, ArAnswerActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerArAnswerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ar_answer; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mTvChangeMode.setText("返回星环");
        mRlRight.setVisibility(View.GONE);
        getSupportFragmentManager().addFragmentOnAttachListener(this);
        if (savedInstanceState == null) {
            if (Sceneform.isSupported(this)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.arFragment, CleanArFragment.class, null)
                        .commit();
            }
        }

        map = Preferences.getHashMapData();
        initListener();
    }

    public void initListener() {


        model = ModelRenderable
                .builder()
                .setSource(this
                        , Uri.parse("models/quiworld.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();

//        model2 = ModelRenderable
//                .builder()
//                .setSource(this
//                        , Uri.parse("models/dongwu.glb"))
//                .setIsFilamentGltf(true)
//                .setAsyncLoadEnabled(true)
//                .build();
//
        model3 = ModelRenderable
                .builder()
                .setSource(this
                        , Uri.parse("models/huoshan.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();
//
//        model4 = ModelRenderable
//                .builder()
//                .setSource(this
//                        , Uri.parse("models/feng.glb"))
//                .setIsFilamentGltf(true)
//                .setAsyncLoadEnabled(true)
//                .build();

//        model5 = ModelRenderable
//                .builder()
//                .setSource(this
//                        , Uri.parse("models/haiyang.glb"))
//                .setIsFilamentGltf(true)
//                .setAsyncLoadEnabled(true)
//                .build();
//
//        model6 = ModelRenderable
//                .builder()
//                .setSource(this
//                        , Uri.parse("models/wendu.glb"))
//                .setIsFilamentGltf(true)
//                .setAsyncLoadEnabled(true)
//                .build();
//
//        model7 = ModelRenderable
//                .builder()
//                .setSource(this
//                        , Uri.parse("models/turang.glb"))
//                .setIsFilamentGltf(true)
//                .setAsyncLoadEnabled(true)
//                .build();
//
//        model8 = ModelRenderable
//                .builder()
//                .setSource(this
//                        , Uri.parse("models/tiankong.glb"))
//                .setIsFilamentGltf(true)
//                .setAsyncLoadEnabled(true)
//                .build();
//
//
        model9 = ModelRenderable
                .builder()
                .setSource(this
                        , Uri.parse("models/nengliang.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();


        viewRenderable = ViewRenderable.builder()
                .setView(this, R.layout.view_ar_answer)
                .build();

        CompletableFuture.allOf(model,model3,viewRenderable)
                .handle((ok, ex) -> {
                    try {
                        FootprintSelectionVisualizer selectionVisualizer = new FootprintSelectionVisualizer();
                        transformationSystem = new TransformationSystem(getResources().getDisplayMetrics(), selectionVisualizer);
                        arFragment.getArSceneView().getScene().addOnPeekTouchListener((hitTestResult, motionEvent) -> {
                            transformationSystem.onTouch(hitTestResult, motionEvent);
                        });
                        anchorNode = new AnchorNode();
                        anchorNode.setParent(arFragment.getArSceneView().getScene());

                        andy = new TransformableNode(transformationSystem);
                        andy.setParent(anchorNode);
                        andy.setRenderable(model.get()).animate(true).start();
                        model.get().setShadowCaster(false);
                        andy.setWorldScale(new Vector3(0.3f, 0.3f, 0.3f));
                        andy.setWorldPosition(new Vector3(0f, 0f, 0.5f));
                        andy.getRenderableInstance().setCulling(false);
                        // 禁止缩放
                        andy.getScaleController().setEnabled(false);
                        andy.getRotationController().setEnabled(false);
                        andy.getTranslationController().setEnabled(false);
                        andy.select();

                       setModel();

                        answerNode = new Node();
                        answerNode.setParent(anchorNode);
                        answerNode.setRenderable(viewRenderable.get());
                        viewRenderable.get().setShadowCaster(false);
                        answerNode.setWorldScale(new Vector3(0.368f, 0.328f, 0.3f));
                        answerNode.setWorldPosition(new Vector3(0.009f, -0.14f, -1.980f));
                        mRcyAnswer = viewRenderable.get().getView().findViewById(R.id.rcy_answer);
                        mPrUploadValue = viewRenderable.get().getView().findViewById(R.id.pr_upload_value);
                        mIvCofim = viewRenderable.get().getView().findViewById(R.id.iv_cofim);
                        ArCardLayoutManager arCardLayoutManager = new ArCardLayoutManager();
                        arCardLayoutManager.initCardConfig(getApplicationContext());
                        mRcyAnswer.setLayoutManager(arCardLayoutManager);
                        arAnswerAdapter = new ArAnswerAdapter(new ArrayList<>(), value -> {
                            infos = value;
                        });
                        mRcyAnswer.setAdapter(arAnswerAdapter);
                        //接口
                        if (mPresenter != null) {
                            mPresenter.questionlist();
                        }
                        mIvCofim.setOnClickListener(view -> {
                            if (mRcyAnswer != null && mRcyAnswer.getChildCount() > 0) {
                                if (infos != null) {
                                    map.put(infos.get(infos.size() - 1).getQuestionid() + "", infos.get(infos.size() - 1).getValue());
                                    Preferences.putHashMapData(map);
                                }
                                mPrUploadValue.setProgress(map.size());
                                if (arAnswerAdapter.getItemCount() != 1) {
                                    if (infos.size() == total - arAnswerAdapter.getItemCount() + 1) {
                                        arAnswerAdapter.remove(0);
//                                        //动物
//                                        try {
//                                            if (new Random().nextInt(2) == 1) {
//                                                andy2.setRenderable(getModel().get()).animate(true).start();
//                                            }
//                                        } catch (ExecutionException e) {
//                                            e.printStackTrace();
//                                        } catch (InterruptedException e) {
//                                            e.printStackTrace();
//                                        }
                                    } else {
                                        showMessage("请进行选择!");
                                    }
                                } else {
                                    if (mPresenter != null) {
                                        mPresenter.questionassess(map);
                                    }
                                }
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

    public void setModel() {
        try {
            andy2 = new TransformableNode(transformationSystem);
            andy2.setParent(anchorNode);
            andy2.setRenderable(model3.get()).animate(true).start();
            model3.get().setShadowCaster(false);
            andy2.setWorldScale(new Vector3(0.3f, 0.3f, 0.3f));
            andy2.getRenderableInstance().setCulling(false);
            // 禁止缩放
            andy2.getScaleController().setEnabled(false);
            andy2.getRotationController().setEnabled(false);
            andy2.getTranslationController().setEnabled(false);
            andy2.select();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<ModelRenderable> getModel() {
        int random = new Random().nextInt(8);
        CompletableFuture<ModelRenderable>[] model = new CompletableFuture[]{model2, model3, model4, model5, model6, model7, model8, model9};
        return model[random];
    }

    @Override
    public void onQuestionListSuccess(List<QuestionEntity> list) {
        if (list != null && list.size() > 0) {
            questionEntityList = new ArrayList<>();
            if (map != null) {
                mPrUploadValue.setProgress(map.size());
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
            total = questionEntityList.size();
            arAnswerAdapter.setData(questionEntityList);
        }
    }

    @Override
    public void onQuestionAssessSuccess(QuestionAssessEntity entity) {
        if (entity != null) {
            Preferences.putHashMapData(null);
            attributeResultsDialog = new AttributeResultsDialog(this, entity);
            attributeResultsDialog.setCancelable(false);
            attributeResultsDialog.show();
            attributeResultsDialog.setOnClickCallback(status -> {
                if (status == AttributeResultsDialog.SELECT_FINISH) {
                    finish();
                }
            });
        }
    }

    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
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
        this.arSceneView.getPlaneRenderer().setEnabled(false);
        this.arSceneView.getPlaneRenderer().setVisible(false); //隐藏小白点
        this.arSceneView.setFrameRateFactor(SceneView.FrameRate.FULL);

       // ArSceneViewKt.setLightEstimationConfig(this.arSceneView, LightEstimationConfig.DISABLED);
//        HDREnvironmentKt.loadEnvironmentAsync(
//                HDRLoader.INSTANCE,
//                this,
//                "environments/e2.hdr",
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

    @OnClick({R.id.tv_change_mode})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_change_mode:
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        arSceneView.destroySession();
    }
}