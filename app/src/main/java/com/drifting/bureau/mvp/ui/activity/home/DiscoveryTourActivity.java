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
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.drifting.bureau.R;
import com.drifting.bureau.data.entity.Point;
import com.drifting.bureau.data.event.BackSpaceEvent;
import com.drifting.bureau.data.event.MyBlindBoxRefreshEvent;
import com.drifting.bureau.di.component.DaggerDiscoveryTourComponent;
import com.drifting.bureau.mvp.model.entity.CustomerEntity;

import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
import com.drifting.bureau.mvp.model.entity.PlanetEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.ui.activity.index.DriftingBottleActivity;
import com.drifting.bureau.mvp.ui.activity.index.SpaceCapsuleActivity;
import com.drifting.bureau.mvp.ui.activity.index.ViewRaftingActivity;
import com.drifting.bureau.mvp.ui.activity.user.AboutMeActivity;
import com.drifting.bureau.mvp.ui.dialog.RaftingInforDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.MapsUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.animator.AnimatorUtil;
import com.drifting.bureau.util.callback.BaseDataCallBack;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.DiscoveryTourContract;
import com.drifting.bureau.mvp.presenter.DiscoveryTourPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


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
    @BindView(R.id.iv_planet)
    ImageView mIvPlanet;
    @BindView(R.id.iv_planet1)
    ImageView mIvPlanet1;
    @BindView(R.id.iv_planet2)
    ImageView mIvPlanet2;
    @BindView(R.id.iv_planet3)
    ImageView mIvPlanet3;
    @BindView(R.id.iv_planet4)
    ImageView mIvPlanet4;
    @BindView(R.id.tv_planet1)
    TextView mTvPlanet1;
    @BindView(R.id.tv_planet2)
    TextView mTvPlanet2;
    @BindView(R.id.tv_planet3)
    TextView mTvPlanet3;
    @BindView(R.id.rl_plant1)
    RelativeLayout mRlPlant1;
    @BindView(R.id.rl_plant2)
    RelativeLayout mRlPlant2;
    @BindView(R.id.rl_planet3)
    RelativeLayout mRlPlant3;
    @BindView(R.id.iv_rocket)
    ImageView mIvRocket;
    @BindView(R.id.rl_message)
    RelativeLayout mRlMessage;

    private List<PlanetEntity> list;
    private int index = -1;

    //定义滑动的最小距离
    private static final int MIN_DISTANCE = 100;
    private GestureDetector gestureDetector;
    private UserGestureDetector userGestureDetector;
    private RaftingInforDialog raftingInforDialog;
    private AnimatorSet animatorSet;
    private static String EXTRA_TYPE = "extra_type";
    private Handler handler;
    private boolean isAnmiation = true;
    private int id, user_id, explore_id;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, DiscoveryTourActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    public static void start(Context context, int type, boolean closePage) {
        Intent intent = new Intent(context, DiscoveryTourActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
        if (intent.getIntExtra(EXTRA_TYPE, 0) == 2) {
            IntoSpace();
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getXy(mIvPlanet1);
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
        //实例化滑动监听
        userGestureDetector = new UserGestureDetector();
        //实例化GestureDetector并将UserGestureDetector实例传入
        gestureDetector = new GestureDetector(this, userGestureDetector);
        if (mPresenter != null) {
            mPresenter.getExploreList();
        }
    }

    public void getXy(ImageView imageView) {
        int[] location = new int[2];
        imageView.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
    }


    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }


    @OnClick({R.id.rl_plant2, R.id.rl_plant1, R.id.rl_planet3, R.id.rl_message, R.id.tv_about_me, R.id.tv_space_capsule})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_plant1:
                startDriftingBottle(mTvPlanet1);
                break;
            case R.id.rl_plant2:  //星球点击
                startDriftingBottle(mTvPlanet2);
                break;
            case R.id.rl_planet3:
                startDriftingBottle(mTvPlanet3);
                break;
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
                RequestUtil.create().userplayer(Preferences.getUserId(), entity -> {
                    if (entity != null && entity.getCode() == 200) {
                        AboutMeActivity.start(this, entity.getData(), false);
                    }
                });
                break;
            case R.id.tv_space_capsule: //太空舱
                SpaceCapsuleActivity.start(this, false);
                break;
        }
    }


    public void startDriftingBottle(TextView textView) {
        if (!textView.getText().toString().equals("传递漂")) {
            showMessage("暂未开放");
        } else {
            DriftingBottleActivity.start(this, textView.getText().toString(), false);
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
                mIvRocket.setVisibility(View.GONE);
                mRlMessage.setVisibility(View.INVISIBLE);
                handler.postDelayed(mAdRunnable, 1000 * 10);
            }
        }
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
        animatorSet.play(translationY).with(translationX);
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
                    handler.postDelayed(mAdRunnable, 60);
                }
            }
        });
    }


    @Override
    public void onExploretypeSuccess(List<PlanetEntity> entityList) {
        if (entityList != null && entityList.size() > 0) {
            list = entityList;
            setFrame();
            mRlPlant1.setAlpha(0.5f);
            mRlPlant3.setAlpha(0.5f);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler= new Handler();
        handler.postDelayed(mAdRunnable, 60);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler!=null){
            handler.removeCallbacks(mAdRunnable);
        }
    }

    @Override
    public void finishSuccess() {
        finish();
    }

    public void setFrame() {
        if (index < 0) {
            index = list.size() - 1;
        }
        if (index >= list.size()) {
            index = 0;
        }
        for (int i = 0; i < 5; i++) {
            int row = 0;
            if (i == 0) {
                row = index - 1;
                if (row < 0) {
                    row = list.size() - 1;
                }
            } else {
                row = index + i - 1;
                if (row >= list.size()) {
                    row = row - list.size();
                }
            }
            switch (i) {
                case 0:
                    GlideUtil.create().loadLongImage(this, list.get(row).getImageUrl(), mIvPlanet);
                    break;
                case 1:
                    GlideUtil.create().loadLongImage(this, list.get(row).getImageUrl(), mIvPlanet1);
                    mTvPlanet1.setText(list.get(row).getName());
                    break;
                case 2:
                    GlideUtil.create().loadLongImage(this, list.get(row).getImageUrl(), mIvPlanet2);
                    mTvPlanet2.setText(list.get(row).getName());
                    break;
                case 3:
                    GlideUtil.create().loadLongImage(this, list.get(row).getImageUrl(), mIvPlanet3);
                    mTvPlanet3.setText(list.get(row).getName());
                    break;
                case 4:
                    GlideUtil.create().loadLongImage(this, list.get(row).getImageUrl(), mIvPlanet4);
                    break;
            }
        }
    }


    /**
     * 重写onTouchEvent返回一个gestureDetector的屏幕触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * 自定义MyGestureDetector类继承SimpleOnGestureListener
     */
    class UserGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > MIN_DISTANCE || e2.getY() - e1.getY() > MIN_DISTANCE) { //下滑或者左滑动
                index--;
                setFrame();
            } else { //上滑或者右滑
                index++;
                setFrame();
            }
            return true;
        }
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
        handler.removeCallbacks(mAdRunnable);
        RequestUtil.create().disDispose();
    }
}