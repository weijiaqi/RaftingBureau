package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.BackSpaceEvent;
import com.drifting.bureau.data.event.PaymentEvent;
import com.drifting.bureau.data.event.VideoEvent;
import com.drifting.bureau.di.component.DaggerViewRaftingComponent;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.ui.activity.pay.PaymentInfoActivity;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.drifting.bureau.mvp.ui.dialog.RaftingOrderDialog;
import com.drifting.bureau.mvp.ui.fragment.PostDriftingFragment;
import com.drifting.bureau.util.BitmapUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.view.VoiceWave;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.ViewRaftingContract;
import com.drifting.bureau.mvp.presenter.ViewRaftingPresenter;

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
    LinearLayout mLlImprint;
    @BindView(R.id.rl_explore)
    RelativeLayout mRlExplore;
    @BindView(R.id.iv_explore_lock)
    ImageView mIvExploreLock;
    private RaftingOrderDialog raftingOrderDialog;
    private PublicDialog publicDialog;
    private int type;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, ViewRaftingActivity.class);
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
        mToobarTitle.setText("查看漂流");
        initListener();
    }

    public void initListener() {
        mRlExplore.setClickable(false);

        type = 1;
        setVisibility(type);


    }


    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.toolbar_back, R.id.iv_play, R.id.rl_video_play, R.id.ll_join, R.id.ll_imprint, R.id.rl_explore, R.id.tv_into_space})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_play: //语音播放
                    PermissionDialog.startVoicePlay(this, "/data/user/0/com.drifting.bureau/files/voice_20220520_093022.amr", mIvPlay, mVideoView, mTvTime);
                    break;
                case R.id.rl_video_play://播放视频
                    VideoActivity.start(this, "http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4", false);
                    break;
                case R.id.ll_join: //参与传递
                    if (mPresenter != null) {
                        mPresenter.skulist(type, 1, 3);
                    }
                    break;
                case R.id.ll_imprint:  //留下我的传递印记
                    mLlImprint.setVisibility(View.GONE);
                    Fragment fragment = PostDriftingFragment.newInstance(2);
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fame, fragment).commitAllowingStateLoss();
                    break;
                case R.id.rl_explore:  //探寻过程
                    RaftingDetailsActivity.start(this, false);
                    break;
                case R.id.tv_into_space:
                    publicDialog=new PublicDialog(this);
                    publicDialog.show();
                    publicDialog.setCancelable(false);
                    publicDialog.setTitleText("已丢回太空");
                    publicDialog.setContentText("丢回太空的漂流信息\n将不会再次收到");
                    publicDialog.setButtonText("返回首页");
                    publicDialog.setOnClickCallback(type -> {
                        if (type==PublicDialog.SELECT_FINISH){
                            finish();
                            EventBus.getDefault().post(new BackSpaceEvent());
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
                        //3是假数据
                        //      mPresenter.createOrder(5, sb.toString());
                        PaymentInfoActivity.start(this, 3, "1111", "0.12", false);
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
    public void onNetError() {

    }


    public void setVisibility(int type) {
        mTvWord.setVisibility(type == 1 ? View.VISIBLE : View.GONE);
        mRlVoicePlay.setVisibility(type == 1 ? View.GONE : type == 2 ? View.VISIBLE : View.GONE);
        mRlVideoPlay.setVisibility(type == 1 ? View.GONE : type == 2 ? View.GONE : View.VISIBLE);
        if (type == 1) {
            mTvWord.setText("一起来玩成语接龙的游戏吧");
        } else if (type == 2) {
            mTvTime.setText("4S");
            mVideoView.setDecibel(0);
        } else if (type == 3) {
            mIvpic.setImageBitmap(BitmapUtil.getVideoThumb("http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4"));
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PaymentEvent(PaymentEvent event) {
        if (event != null) {
            publicDialog = new PublicDialog(this);
            publicDialog.show();
            publicDialog.setCancelable(false);
            publicDialog.setTitleText("参与成功");
            publicDialog.setContentText("快写下你的传递漂信息吧！");
            publicDialog.setOnClickCallback(type -> {
                if (type == PublicDialog.SELECT_FINISH) {
                    mRlParticipate.setVisibility(View.GONE);
                    mLlImprint.setVisibility(View.VISIBLE);
                    mRlExplore.setClickable(true);
                    mIvExploreLock.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (type == 2) {
            VideoUtil.close();
            VideoUtil.cleanCountDown();
        }
    }
}