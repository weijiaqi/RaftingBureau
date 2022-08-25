package com.drifting.bureau.mvp.ui.activity.user;

import static com.drifting.bureau.app.FilePathConstant.STAR_PATH;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.billy.android.swipe.SmartSwipe;
import com.billy.android.swipe.SmartSwipeWrapper;
import com.billy.android.swipe.SwipeConsumer;
import com.billy.android.swipe.consumer.DrawerConsumer;
import com.billy.android.swipe.consumer.SlidingConsumer;
import com.billy.android.swipe.listener.SimpleSwipeListener;
import com.drifting.bureau.R;
import com.drifting.bureau.data.event.AnswerCompletedEvent;
import com.drifting.bureau.di.component.DaggerAboutMeComponent;
import com.drifting.bureau.mvp.contract.AboutMeContract;
import com.drifting.bureau.mvp.model.entity.AoubtMeEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.presenter.AboutMePresenter;
import com.drifting.bureau.mvp.ui.activity.index.MoveAwayPlanetaryActivity;
import com.drifting.bureau.mvp.ui.activity.index.PlanetarySelectActivity;
import com.drifting.bureau.mvp.ui.activity.index.ar.ARActivity;
import com.drifting.bureau.mvp.ui.adapter.AboutMeAdapter;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.mvp.ui.fragment.PlanetaryDisFragment;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ARCoreUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.FileUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.NotificationUtil;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.animator.AnimatorUtil;
import com.drifting.bureau.util.downloadutil.DownloadRequest;
import com.drifting.bureau.util.manager.NotificationManager;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/12 12:48
 *
 * @author 关于我
 * module name is AboutMeActivity
 */
public class AboutMeActivity extends BaseActivity<AboutMePresenter> implements AboutMeContract.View {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.rcy_mylist)
    RecyclerView mRcyList;
    @BindView(R.id.pr_upload_value)
    ProgressBar mPrUpload;
    @BindView(R.id.tv_place_esidence)
    TextView mTvPlace;
    @BindView(R.id.tv_place_esidence2)
    TextView mTvPlace2;
    @BindView(R.id.tv_identity)
    TextView mTvIdentity;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_identity2)
    TextView mTvIdentity2;
    @BindView(R.id.tv_schedule)
    TextView mTvSchedule;
    @BindView(R.id.iv_drifting)
    ImageView mDrifting;
    private AboutMeAdapter aboutMeAdapter;
    private UserInfoEntity userInfoEntity;
    private int assess_after, assess_status;
    private static final String EXTRA_USERINFOENTITY = "userinfo_entity";

    public static void start(Context context, UserInfoEntity entity, boolean closePage) {
        Intent intent = new Intent(context, AboutMeActivity.class);
        intent.putExtra(EXTRA_USERINFOENTITY, entity);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAboutMeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_about_me; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        TextUtil.setRightImage(mIvRight, R.drawable.setting);
        if (getIntent() != null) {
            userInfoEntity = (UserInfoEntity) getIntent().getSerializableExtra(EXTRA_USERINFOENTITY);
        }
        initListener();
    }

    public void initListener() {
        GlideUtil.create().loadLongImage(this, Preferences.getMascot(), mDrifting);
        mRcyList.setLayoutManager(new GridLayoutManager(this, 3));
        aboutMeAdapter = new AboutMeAdapter(new ArrayList<>());
        mRcyList.setAdapter(aboutMeAdapter);
        aboutMeAdapter.setData(getData());
        if (userInfoEntity != null && userInfoEntity.getPlanet() != null) {
            setUserInfo();
        }
    }


    public void setUserInfo() {
        mTvPlace.setText(userInfoEntity.getPlanet().getName());
        mTvPlace2.setText(userInfoEntity.getPlanet().getName());
        mTvIdentity.setText(userInfoEntity.getUser().getLevel_name());
        mTvIdentity2.setText(userInfoEntity.getUser().getLevel_name());
        mTvName.setText(userInfoEntity.getUser().getName());
        mTvSchedule.setText(userInfoEntity.getPlanet().getSchedule() + "%");
        mPrUpload.setProgress(userInfoEntity.getPlanet().getSchedule());
//        setTopSwipe();
    }


    public List<AoubtMeEntity> getData() {
        List<AoubtMeEntity> list = new ArrayList<>();
        list.add(new AoubtMeEntity("漂流轨迹", "我的漂流"));
        list.add(new AoubtMeEntity("订单记录", "我的订单"));
        list.add(new AoubtMeEntity("星际战队", "战队成员"));
        list.add(new AoubtMeEntity("实体门店", "漂流局茶饮店"));
        return list;
    }


    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }


    @OnClick({R.id.toolbar_back, R.id.iv_right, R.id.tv_select, R.id.tv_explore, R.id.ar_selected})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_right:
                    AccountSettingsActivity.start(this, false);
                    break;
                case R.id.tv_select: //查看
                    PlanetarySelectActivity.start(this, userInfoEntity.getPlanet().getLevel(), false);
                    break;
                case R.id.tv_explore: //探索
                    finish();
                    break;
                case R.id.ar_selected:
                    if (ARCoreUtil.checkArCoreAvailability(getActivity())) {
                        startCamrea(userInfoEntity.getPlanet().getAr_url()); //开启AR
                    }
                    break;
            }
        }
    }


    public void startCamrea(String url) {
        PermissionDialog.requestCodePermissions(this, new PermissionDialog.PermissionCallBack() {
            @Override
            public void onSuccess() {
                String file = STAR_PATH + FileUtil.getUrlFileName(url);
                if (FileUtil.fileIsExists(file)) {
                    FileUtil.getNetworkFileSize(url, new Handler(Looper.myLooper()) {
                        @Override
                        public void handleMessage(Message msg) {
                            int fileSize = msg.getData().getInt("fileSize");
                            if (fileSize == new File(file).length()) {
                                ARActivity.start(AboutMeActivity.this, file, false);
                            } else {
                                showNotificationDialog(url);
                            }
                        }
                    });
                } else {
                    showNotificationDialog(url);
                }

            }

            @Override
            public void onFailure() {
            }

            @Override
            public void onAlwaysFailure() {
                PermissionDialog.showDialog(AboutMeActivity.this, "android.permission.CAMERA");
            }
        });
    }


    public void showNotificationDialog(String url) {
        if (NotificationManager.isOpenNotification(AboutMeActivity.this)) {
            DownloadRequest.whith().downloadWithNotification(AboutMeActivity.this, url);
        } else {
            NotificationUtil.showNotificationDialog(AboutMeActivity.this);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }


    public void setTopSwipe() {
        int type = userInfoEntity.getPlanet().getLevel();
        View topMenu = LayoutInflater.from(this).inflate(R.layout.activity_planetary_select, null);
        Fragment fragment = PlanetaryDisFragment.newInstance(type);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fame, fragment).commitAllowingStateLoss();
        RelativeLayout toolbar_back = topMenu.findViewById(R.id.toolbar_back);
        TextView toolbar_title = topMenu.findViewById(R.id.toolbar_title);
        TextView mTvBar = topMenu.findViewById(R.id.tv_bar);
        LinearLayout ll_move_away = topMenu.findViewById(R.id.ll_move_away);
        ImageView mIvRocket = topMenu.findViewById(R.id.iv_rocket);
        ImageView mIvOpenSearch = topMenu.findViewById(R.id.iv_open_search);
        TextView mTvThreeDay = topMenu.findViewById(R.id.tv_three_day);
        TextView mTvSeek = topMenu.findViewById(R.id.tv_seek);
        ScrollView scrollView = topMenu.findViewById(R.id.scroll_view);
        setStatusBarHeight(mTvBar);
        toolbar_back.setOnClickListener(v -> {
            finish();
        });
        toolbar_title.setText("星球分布");
        RequestUtil.create().planetlocation(entity -> {
            if (entity != null && entity.getCode() == 200) {
                if (entity.getData().getShow() == 0) {  //不显示
                    ll_move_away.setVisibility(View.GONE);
                } else { //显示
                    ll_move_away.setVisibility(View.VISIBLE);
                    assess_after = entity.getData().getAssess_after();
                    assess_status = entity.getData().getAssess_status();
                    if (assess_status == 1) {//可以答题
                        mTvSeek.setText("可探寻星球");
                        mIvOpenSearch.setVisibility(View.VISIBLE);
                        mTvThreeDay.setVisibility(View.GONE);
                    } else {
                        mTvSeek.setText("探寻星球中...");
                        mTvThreeDay.setVisibility(View.VISIBLE);
                        mTvThreeDay.setText(getString(R.string.three_day, assess_after + ""));
                        mIvOpenSearch.setVisibility(View.GONE);
                        statScaleAnim(mTvSeek);
                        statFloatAnim(mIvRocket);
                    }
                }
            }
        });

        ll_move_away.setOnClickListener(v -> {
            if (assess_status == 1) {
                MoveAwayPlanetaryActivity.start(AboutMeActivity.this, 1, false);
            }
        });
        topMenu.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        SmartSwipeWrapper topMenuWrapper = SmartSwipe.wrap(topMenu);
        DrawerConsumer slidingConsumer = new SlidingConsumer()
                .setTopDrawerView(topMenuWrapper)
                .showScrimAndShadowOutsideContentView()
                //设置遮罩层背景颜色，默认透明
                .setScrimColor(R.color.color_00_7f)
                //设置边框阴影颜色，默认透明
//                .setShadowColor(R.color.transparent)
                //设置边框阴影大小
//                .setShadowSize(SmartSwipe.dp2px(20, this))
                //设置监听
                .addListener(new SimpleSwipeListener() {
                    @Override
                    public void onSwipeOpened(SmartSwipeWrapper wrapper, SwipeConsumer consumer, int direction) {
                        //设置默认滚动到顶部
                        scrollView.post(() -> {
                            // TODO Auto-generated method stub
                            if (type == 1 || type == 9 || type == 3 || type == 8 || type == 2 || type == 6) {
                                scrollView.fullScroll(ScrollView.FOCUS_UP);
                            } else if (type == 11 || type == 17 || type == 10 || type == 12 || type == 7) {
                                scrollView.scrollTo(0, scrollView.getBottom() / 3);
                            } else {
                                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
                    }
                })
                //将SwipeConsumer类型转换为DrawerConsumer类型
                .as(DrawerConsumer.class);


        SmartSwipe.wrap(this).addConsumer(slidingConsumer);


    }

    public void statScaleAnim(View view) {
        AnimatorUtil.AlphaAnim(view, 3000);
    }

    public void statFloatAnim(View view) {
        AnimatorUtil.floatAnim(view, 1000, 3);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void AnswerCompletedEvent(AnswerCompletedEvent answerCompletedEvent) {
        if (answerCompletedEvent != null) {
            RequestUtil.create().userplayer(Preferences.getUserId(), entity -> {
                if (entity != null && entity.getCode() == 200) {
                    userInfoEntity = entity.getData();
                    setUserInfo();
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.create().disDispose();
    }
}