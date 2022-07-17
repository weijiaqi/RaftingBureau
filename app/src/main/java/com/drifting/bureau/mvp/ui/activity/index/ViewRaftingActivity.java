package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.BackSpaceEvent;
import com.drifting.bureau.data.event.PaymentEvent;
import com.drifting.bureau.di.component.DaggerViewRaftingComponent;
import com.drifting.bureau.mvp.contract.ViewRaftingContract;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.MessageContentEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.presenter.ViewRaftingPresenter;
import com.drifting.bureau.mvp.ui.activity.pay.PaymentInfoActivity;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.drifting.bureau.mvp.ui.dialog.RaftingOrderDialog;
import com.drifting.bureau.mvp.ui.dialog.ReportDialog;
import com.drifting.bureau.mvp.ui.fragment.PostDriftingFragment;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.view.VoiceWave;
import com.hjq.shape.layout.ShapeLinearLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/18 14:44
 *
 * @author 查看漂流
 * module name is ViewRaftingActivity
 */
public class ViewRaftingActivity extends BaseActivity<ViewRaftingPresenter> implements ViewRaftingContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToobarTitle;
    @BindView(R.id.tv_word)
    TextView mTvWord;
    @BindView(R.id.rl_voice_play)
    RelativeLayout mRlVoicePlay;
    @BindView(R.id.rl_video_play)
    RelativeLayout mRlVideoPlay;
    @BindView(R.id.iv_play)
    ImageView mIvPlay;
    @BindView(R.id.videoView)
    VoiceWave mVideoView;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.iv_pic)
    ImageView mIvpic;
    @BindView(R.id.rl_participate)
    RelativeLayout mRlParticipate;
    @BindView(R.id.ll_imprint)
    ShapeLinearLayout mLlImprint;
    @BindView(R.id.rl_explore)
    RelativeLayout mRlExplore;
    @BindView(R.id.iv_explore_lock)
    ImageView mIvExploreLock;
    @BindView(R.id.tv_rafting_type)
    TextView mTvRaftingType;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_planet)
    TextView mTvPlanet;
    @BindView(R.id.tv_identity)
    TextView mTvIdentity;
    @BindView(R.id.iv_mask)
    ImageView mIvMask;
    @BindView(R.id.rl_add_friends)
    RelativeLayout mRlAddFriends;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.tv_by_time)
    TextView mTvByTime;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.tv_imprint)
    TextView mTvImprint;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.iv_mastor)
    ImageView mIvMascot;
    private RaftingOrderDialog raftingOrderDialog;
    private PublicDialog publicDialog;
    private ReportDialog reportDialog;
    private int type, id, user_id, explore_id, totaltime, second, total;

    private static String EXTRA_USER_ID = "extra_user_id";
    private static String EXTRA_ID = "extra_id";
    private static String EXTRA_EXPLORE_ID = "extra_explore_id";
    private static final String EXTRA_USERINFOENTITY = "userinfo_entity";
    private UserInfoEntity userInfoEntity;
    private String content;

    Handler handler = new Handler();

    public static void start(Context context, int user_id, int id, int explore_id, UserInfoEntity entity, boolean closePage) {
        Intent intent = new Intent(context, ViewRaftingActivity.class);
        intent.putExtra(EXTRA_USER_ID, user_id);
        intent.putExtra(EXTRA_ID, id);
        intent.putExtra(EXTRA_EXPLORE_ID, explore_id);
        intent.putExtra(EXTRA_USERINFOENTITY, entity);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerViewRaftingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_view_rafting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setImageResource(R.drawable.report);
        mToobarTitle.setText("查看漂流");
        if (getIntent() != null) {
            user_id = getIntent().getIntExtra(EXTRA_USER_ID, 0);
            id = getIntent().getIntExtra(EXTRA_ID, 0);
            explore_id = getIntent().getIntExtra(EXTRA_EXPLORE_ID, 0);
            userInfoEntity = (UserInfoEntity) getIntent().getSerializableExtra(EXTRA_USERINFOENTITY);
        }
        initListener();
    }

    public void initListener() {
        switch (explore_id) {
            case 1:
                mTvRaftingType.setText("传递漂");
                break;
        }
        mTvName.setText("昵称：" + userInfoEntity.getUser().getName());
        mTvPlanet.setText(userInfoEntity.getPlanet().getName());
        mTvIdentity.setText(userInfoEntity.getUser().getLevel_name());
        GlideUtil.create().loadLongImage(this,userInfoEntity.getUser().getMascot(),mIvMascot);
        setIsLock(false);
        getMessageContent();

        RequestUtil.create().platformtimes(explore_id, entity -> {
            if (entity != null && entity.getCode() == 200) {
                total = entity.getData().getAttend_times() + entity.getData().getCommon_times();
                if (total > 0) {
                    mTvStatus.setText(getString(R.string.free_times, total + ""));
                } else {
                    mTvStatus.setText("可添加好友");
                }
            }
        });
    }


    public void getMessageContent() {
        if (mPresenter != null) {
            mPresenter.messageContent(id);
        }
    }


    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.iv_right, R.id.toolbar_back, R.id.iv_play, R.id.rl_video_play, R.id.ll_join, R.id.ll_imprint, R.id.rl_explore, R.id.tv_into_space, R.id.rl_add_friends})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.iv_right:   //举报
                    reportDialog = new ReportDialog(this, id);
                    reportDialog.show();
                    break;
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_play: //语音播放
                    PermissionDialog.startVoicePlay(this, content, totaltime, mIvPlay, mVideoView, mTvTime);
                    break;
                case R.id.rl_video_play://播放视频
                    VideoActivity.start(this, content, false);
                    break;
                case R.id.ll_join: //参与传递
                    if (total > 0) {   //有免费次数
                        if (mPresenter != null) {
                            mPresenter.messageattending(id);
                        }
                    } else {
                        if (mPresenter != null) {
                            mPresenter.skulist(explore_id, id);
                        }
                    }
                    break;
                case R.id.ll_imprint:  //留下我的传递印记
                    mLlImprint.setVisibility(View.GONE);
                    Fragment fragment = PostDriftingFragment.newInstance(2, explore_id, id);
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fame, fragment).commitAllowingStateLoss();
                    break;
                case R.id.rl_explore:  //探寻过程
                    RaftingDetailsActivity.start(this, id, false);
                    break;
                case R.id.tv_into_space: //丢回太空
                    RequestUtil.create().messagethrow(id, entity -> {
                        if (entity.getCode() == 200) {
                            publicDialog = new PublicDialog(ViewRaftingActivity.this);
                            publicDialog.show();
                            publicDialog.setCancelable(false);
                            publicDialog.setTitleText("已丢回太空");
                            publicDialog.setContentText("丢回太空的漂流信息\n将不会再次收到");
                            publicDialog.setButtonText("返回星际");
                            publicDialog.setOnClickCallback(type -> {
                                if (type == PublicDialog.SELECT_FINISH) {
                                    finish();
                                    EventBus.getDefault().post(new BackSpaceEvent());
                                }
                            });
                        } else {
                            showMessage(entity.getMsg());
                        }
                    });
                    break;
                case R.id.rl_add_friends:  //添加好友
                    RequestUtil.create().friendapply(user_id + "", entity -> {
                        if (entity != null) {
                            if (entity.getCode() == 200) {
                                ToastUtil.showAddFriendDialog(this);
                            } else {
                                showMessage(entity.getMsg());
                            }
                        }
                    });
                    break;
            }
        }
    }


    @Override
    public void onSkuListSuccess(SkuListEntity skuListEntity) {
        if (skuListEntity != null) {
            raftingOrderDialog = new RaftingOrderDialog(this, skuListEntity);
            raftingOrderDialog.show();
            raftingOrderDialog.setOnClickCallback(type -> {
                if (type == RaftingOrderDialog.SELECT_FINISH) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < skuListEntity.getGoods_sku().size(); i++) {
                        if (sb.length() > 0) {
                            sb.append(",");
                        }
                        sb.append(skuListEntity.getGoods_sku().get(i).getSku_code());
                    }
                    if (mPresenter != null) {
                        mPresenter.createOrder(id, sb.toString());
                    }
                }
            });
        }
    }

    @Override
    public void onCreateOrderSuccess(CreateOrderEntity entity) {
        if (entity != null) {
            PaymentInfoActivity.start(this, 3, entity.getSn(), entity.getTotal_amount(), false);
        }
    }

    @Override
    public void onMessageContent(MessageContentEntity entity) {
        if (entity != null) {
            setIsLock(entity.getUnlock() == 1 ? true : false);
            mTvNum.setText(entity.getSummary().getPlayer() + "");
            type = entity.getMessage().getType_id();
            setVisibility(type, entity);
            if (entity.getAttend_info().getAttend() == 1) { // 是否参与了  1是
                mRlParticipate.setVisibility(View.GONE);
                mLlImprint.setVisibility(View.VISIBLE);
                second = entity.getAttend_info().getFootprint_rest();
                if (second > 0) {
                    mTvByTime.setText(DateUtil.TimeRemaining(second) + "后将会自动发送");
                } else {
                    mLlImprint.getShapeDrawableBuilder().setSolidColor(getColor(R.color.color_99)).intoBackground();
                    mTvImprint.setText("不能留下印记");
                    mTvByTime.setText("订单已失效");
                    mLlImprint.setClickable(false);
                }
            }
        }
    }

    @Override
    public void onMessageAttendingSuccess() {   //免费参与话题成功
        showParticipateDialog();
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            second--;
            if (second == 0) {
                handler.removeCallbacks(runnable);
                publicDialog = new PublicDialog(ViewRaftingActivity.this);
                publicDialog.show();
                publicDialog.setCancelable(false);
                publicDialog.setTitleText("温馨提示");
                publicDialog.setContentText("该信息已经飞走啦!");
                publicDialog.setOnClickCallback(type -> {
                    finish();
                });
            } else {
                mTvByTime.setText(DateUtil.getTime(second) + "后将会自动发送");
                handler.postDelayed(runnable, 1000);
            }
        }
    };


    public void setIsLock(boolean type) {
        mRlExplore.setClickable(type);
        mRlAddFriends.setClickable(type);
        mIvMask.setVisibility(type ? View.GONE : View.VISIBLE);
        mIvExploreLock.setVisibility(type ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onNetError() {

    }


    public void setVisibility(int type, MessageContentEntity entity) {
        mTvWord.setVisibility(type == 1 ? View.VISIBLE : View.GONE);
        mRlVoicePlay.setVisibility(type == 1 ? View.GONE : type == 2 ? View.VISIBLE : View.GONE);
        mRlVideoPlay.setVisibility(type == 1 ? View.GONE : type == 2 ? View.GONE : View.VISIBLE);
        content = entity.getMessage().getContent();
        if (type == 1) {
            mTvWord.setText(content);
        } else if (type == 2) {
            totaltime = VideoUtil.getLocalVideoDuration(content);
            mTvTime.setText(totaltime + "S");
            mVideoView.setDecibel(0);
        } else if (type == 3) {
            GlideUtil.create().loadLongImage(this, entity.getMessage().getImage(), mIvpic);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PaymentEvent(PaymentEvent event) {
        if (event != null) {
            showParticipateDialog();
        }
    }

    public void showParticipateDialog() {
        publicDialog = new PublicDialog(this);
        publicDialog.show();
        publicDialog.setCancelable(false);
        publicDialog.setTitleText("参与成功");
        publicDialog.setContentText("快写下你的传递漂信息吧！");
        publicDialog.setOnClickCallback(type -> {
            if (type == PublicDialog.SELECT_FINISH) {
                getMessageContent();
            }
        });
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        if (type == 2) {
            VideoUtil.close();
        }
        RequestUtil.create().disDispose();
    }
}