package com.drifting.bureau.mvp.ui.activity.home;

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
import android.util.Log;
import android.view.GestureDetector;
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
import com.drifting.bureau.di.component.DaggerDiscoveryTourComponent;
import com.drifting.bureau.mvp.model.entity.CustomerEntity;

import com.drifting.bureau.mvp.model.entity.PlanetEntity;
import com.drifting.bureau.mvp.ui.activity.index.DriftingBottleActivity;
import com.drifting.bureau.mvp.ui.activity.index.SpaceCapsuleActivity;
import com.drifting.bureau.mvp.ui.activity.index.ViewRaftingActivity;
import com.drifting.bureau.mvp.ui.activity.user.AboutMeActivity;
import com.drifting.bureau.mvp.ui.dialog.RaftingInforDialog;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.DiscoveryTourContract;
import com.drifting.bureau.mvp.presenter.DiscoveryTourPresenter;

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
    private int index = 0;
    private List<Point> pointList;
    private int rows;
    private Animation animation;
    private ScaleAnimation scaleAnimation;
    private AlphaAnimation alphaAnimation;
    private AnimationSet animationSet;
    //定义滑动的最小距离
    private static final int MIN_DISTANCE = 100;
    private GestureDetector gestureDetector;
    private UserGestureDetector userGestureDetector;
    private RaftingInforDialog raftingInforDialog;

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
        //setToolBar(toolbar, "DiscoveryTour");
        setStatusBar(true);
        mToobarBack.setVisibility(View.GONE);
        mRlInfo.setVisibility(View.VISIBLE);
        //实例化滑动监听
        userGestureDetector = new UserGestureDetector();
        //实例化GestureDetector并将UserGestureDetector实例传入
        gestureDetector = new GestureDetector(this, userGestureDetector);


        list = new ArrayList<>();
        list.add(new PlanetEntity("传递漂1", R.drawable.plant3));
        list.add(new PlanetEntity("传递漂2", R.drawable.plant1));
        list.add(new PlanetEntity("传递漂3", R.drawable.plant2));
        list.add(new PlanetEntity("传递漂4", R.drawable.plant2));
        list.add(new PlanetEntity("传递漂5", R.drawable.guide_planet));
        setFrame();
        mRlPlant1.setAlpha(0.5f);
        mRlPlant3.setAlpha(0.5f);

        //   pointList = new ArrayList<>();
//        pointList.add(new Point(getXy(mIvPlanet)[0], getXy(mIvPlanet)[1],mIvPlanet.getWidth(),mIvPlanet.getHeight()));
//        pointList.add(new Point(getXy(mIvPlanet1)[0], getXy(mIvPlanet1)[1],mIvPlanet1.getWidth(),mIvPlanet1.getHeight()));
//        pointList.add(new Point(getXy(mIvPlanet2)[0], getXy(mIvPlanet2)[1],mIvPlanet2.getWidth(),mIvPlanet2.getHeight()));
//        pointList.add(new Point(getXy(mIvPlanet3)[0], getXy(mIvPlanet3)[1],mIvPlanet3.getWidth(),mIvPlanet3.getHeight()));
//        pointList.add(new Point(getXy(mIvPlanet4)[0], getXy(mIvPlanet4)[1],mIvPlanet4.getWidth(),mIvPlanet4.getHeight()));

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

    }


    @OnClick({R.id.rl_plant2, R.id.iv_planet1, R.id.iv_planet3, R.id.tv_planet2, R.id.tv_open, R.id.tv_about_me,R.id.tv_space_capsule})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_plant2:  //星球点击
                DriftingBottleActivity.start(this, false);
                break;
            case R.id.iv_planet1:
                index--;
                setFrame();
                break;
            case R.id.iv_planet3:
                index++;
                setFrame();
                break;
            case R.id.tv_planet2:
                mIvRocket.setVisibility(View.VISIBLE);
                objectAnimation(1, mIvRocket, mRlMessage, -500, 200, 0, 0, 1000);
                break;
            case R.id.tv_open:
                raftingInforDialog = new RaftingInforDialog(this);
                raftingInforDialog.show();
                raftingInforDialog.setOnClickCallback(type -> {
                    if (type == RaftingInforDialog.CLICK_FINISH) {
                        mRlMessage.setVisibility(View.INVISIBLE);
                        objectAnimation(2, mIvRocket, mRlMessage, 0, 0, 1000, -500, 250);
                    }else if (type == RaftingInforDialog.CLICK_SELECT){
                        ViewRaftingActivity.start(this,false);
                    }
                });
                break;
            case R.id.tv_about_me: //关于我
                AboutMeActivity.start(this, false);
                break;
            case R.id.tv_space_capsule: //太空舱
                SpaceCapsuleActivity.start(this,false);
                break;
        }
    }


    /**
     * @description 属性组合动画
     */
    public void objectAnimation(int type, View view, View tagetview, int values1, int values2, int x, int y, long duration) {
        Log.e("1--------", "x---" + view.getTranslationX() + "y----" + view.getTranslationY());
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", values1, x);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", values2, y);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translationY).with(translationX);
        animatorSet.setDuration(duration);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (type == 1) {
                    tagetview.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    /**
     * @description 从另一个view移动到当前view位置的动画
     */
    public void startTranslate(View view, View targetView) {
        animation = new TranslateAnimation(targetView.getX() - view.getX(), 0, targetView.getY() - view.getY(), 0);
        animation.setDuration(500);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }


    private void moveViewToTargetView(View view, View targetView) {
        final float x = view.getX();
        final float y = view.getY();
        final float targetX = targetView.getX();
        final float targetY = targetView.getY();
        view.animate()
                .translationX(-(x - targetX))
                .translationY(-(y - targetY))
                .setDuration(800)
                .setInterpolator(new DecelerateInterpolator())
                .withLayer()
                .start();
    }


    @Override
    public void customerSuccess(List<CustomerEntity> customerEntity) {

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
                    mIvPlanet.setImageResource(list.get(row).getDrawable());
                    break;
                case 1:
                    mIvPlanet1.setImageResource(list.get(row).getDrawable());
                    mTvPlanet1.setText(list.get(row).getTitle());
                    break;
                case 2:
                    mIvPlanet2.setImageResource(list.get(row).getDrawable());
                    mTvPlanet2.setText(list.get(row).getTitle());
                    break;
                case 3:
                    mIvPlanet3.setImageResource(list.get(row).getDrawable());
                    mTvPlanet3.setText(list.get(row).getTitle());
                    break;
                case 4:
                    mIvPlanet4.setImageResource(list.get(row).getDrawable());
                    break;
            }
        }
    }

//    public void setPointFrame() {
//        if (index < 0) {
//            index = pointList.size() - 1;
//        }
//        if (index >= pointList.size()) {
//            index = 0;
//        }
//        for (int i = 0; i < 5; i++) {
//            int row = 0;
//            if (i == 0) {
//                row = index - 1;
//                if (row < 0) {
//                    row = pointList.size() - 1;
//                }
//            } else {
//                row = index + i - 1;
//                if (row >= pointList.size()) {
//                    row = row - pointList.size();
//                }
//            }
//            switch (i) {
//                case 0:
//
//                    mIvPlanet.setImageResource(list.get(row));
//                    break;
//                case 1:
//                    mIvPlanet1.setImageResource(list.get(row));
//                    break;
//                case 2:
//                    mIvPlanet2.setImageResource(list.get(row));
//                    break;
//                case 3:
//                    mIvPlanet3.setImageResource(list.get(row));
//                    break;
//                case 4:
//                    mIvPlanet4.setImageResource(list.get(row));
//                    break;
//            }
//        }
//    }

    public void StartAnimation() {
        for (int i = 0; i < 2; i++) {
            switch (i) {
                case 0:
                    animation = new TranslateAnimation(0, 228, 0, -400);
                    animation.setDuration(250);
                    animation.setFillAfter(true);
                    scaleAnimation = new ScaleAnimation(1, 1.5f, 1, 1.5f, Animation.RELATIVE_TO_SELF, 1.5f, Animation.RELATIVE_TO_SELF, 1.5f);
                    scaleAnimation.setDuration(250);
                    scaleAnimation.setFillAfter(true);
                    //透明度动画
                    alphaAnimation = new AlphaAnimation(1, 0.1f);
                    alphaAnimation.setDuration(250);
                    alphaAnimation.setFillAfter(true);
                    AnimationSet set = new AnimationSet(true);
                    set.addAnimation(animation);
                    set.addAnimation(scaleAnimation);
                    set.addAnimation(alphaAnimation);
                    mIvPlanet3.startAnimation(animation);
                    break;
                case 1:
                    animation = new TranslateAnimation(0, 318 * 2, 0, -318 * 2);
                    animation.setDuration(250);
                    animation.setFillAfter(true);
                    scaleAnimation = new ScaleAnimation(1, 0.5f, 1, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(250);
                    scaleAnimation.setFillAfter(true);
                    //透明度动画
                    alphaAnimation = new AlphaAnimation(1, 0.1f);
                    alphaAnimation.setDuration(250);
                    alphaAnimation.setFillAfter(true);
                    animationSet = new AnimationSet(true);
                    animationSet.addAnimation(animation);
                    animationSet.addAnimation(scaleAnimation);
                    animationSet.addAnimation(alphaAnimation);
                    mIvPlanet2.startAnimation(animationSet);
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

}