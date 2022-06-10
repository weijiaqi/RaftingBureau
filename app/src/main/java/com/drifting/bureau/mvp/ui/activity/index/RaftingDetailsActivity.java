package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerRaftingDetailsComponent;
import com.drifting.bureau.mvp.model.entity.BarrageEntity;
import com.drifting.bureau.mvp.ui.adapter.RaftingDetailsAdapter;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.SpannableUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.view.AutoPollRecyclerView;
import com.drifting.bureau.view.VoiceWave;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.RaftingDetailsContract;
import com.drifting.bureau.mvp.presenter.RaftingDetailsPresenter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/19 13:21
 *
 * @author 查看漂流
 * module name is RaftingDetailsActivity
 */
public class RaftingDetailsActivity extends BaseActivity<RaftingDetailsPresenter> implements RaftingDetailsContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_barrage)
    AutoPollRecyclerView mRcyBarrage;
    @BindView(R.id.tv_passer)
    TextView mTvPasser;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_passer_num)
    TextView mTvPasserNum;
    @BindView(R.id.tv_word)
    TextView mTvWord;
    @BindView(R.id.rl_voice_play)
    RelativeLayout mRlVoicePlay;
    @BindView(R.id.rl_video_play)
    RelativeLayout mRlVideoPlay;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.videoView)
    VoiceWave mVideoView;
    @BindView(R.id.iv_pic)
    ImageView mIvpic;
    @BindView(R.id.iv_play)
    ImageView mIvPlay;
    @BindView(R.id.tv_talking)
    TextView mTvTalking;
    private int id, totaltime;
    private static String EXTRA_MESSAGE_ID = "extra_message_id";
    private RaftingDetailsAdapter raftingDetailsAdapter;
    private BarrageEntity barrageEntity;
    private List<BarrageEntity.CommentsBean> commentsBeanList;
    private BarrageEntity.CommentsBean commentsBean;
    private BarrageEntity.CommentsBean bean;
    private int size;

    public static void start(Context context, int id, boolean closePage) {
        Intent intent = new Intent(context, RaftingDetailsActivity.class);
        intent.putExtra(EXTRA_MESSAGE_ID, id);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRaftingDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_rafting_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("漂流详情");
        if (getIntent() != null) {
            id = getIntent().getIntExtra(EXTRA_MESSAGE_ID, 0);
        }
        initListener();
    }

    public void initListener() {
        if (mPresenter != null) {
            mPresenter.messagedetails(id);
        }
    }


    @Override
    public void onMessageDetails(BarrageEntity entity) {
        if (entity != null) {
            barrageEntity = entity;
            SpannableStringBuilder passer = SpannableUtil.getBuilder(this, "我是第 ").append(barrageEntity.getMy_attend() + "").setBold().setForegroundColor(R.color.color_6d).setTextSize(23).append(" 传递人").build();
            mTvPasser.setText(passer);
            mTvPasserNum.setText(getString(R.string.transmit, barrageEntity.getTotal_attend() + ""));
            commentsBeanList = new ArrayList<>();
            //拼接到一个数组
            commentsBean = new BarrageEntity.CommentsBean();
            commentsBean.setUser_id(barrageEntity.getMessage().getUser_id());
            commentsBean.setUser_name(barrageEntity.getMessage().getUser_name());
            commentsBean.setContent(barrageEntity.getMessage().getContent());
            commentsBean.setType_id(barrageEntity.getMessage().getType_id());
            commentsBean.setImage(barrageEntity.getMessage().getImage());
            commentsBeanList.add(commentsBean);
            bean = commentsBean;
            setMessageStatus(bean);


            if (barrageEntity.getComments().size() > 0) {
                for (int i = 0; i < barrageEntity.getComments().size(); i++) {
                    commentsBeanList.add(barrageEntity.getComments().get(i));
                }
            }

            if (commentsBeanList.size() > 7) {
                size = 7;
            } else {
                size = commentsBeanList.size();
            }
            for (int i = 0; i < size; i++) {
                commentsBean = new BarrageEntity.CommentsBean();
                commentsBean.setType_id(1);
                commentsBean.setContent("\u3000");
                commentsBeanList.add(0, commentsBean);
            }
            mRcyBarrage.setLayoutManager(new StaggeredGridLayoutManager(size, StaggeredGridLayoutManager.HORIZONTAL));
            raftingDetailsAdapter = new RaftingDetailsAdapter(new ArrayList<>(), entity1 -> {
                if (bean.getType_id()==2) {//点击先停止语音播放
                    VideoUtil.stop(mVideoView, mIvPlay, mTvTime,totaltime);
                }
                bean = entity1;
                setMessageStatus(bean);
            });
            mRcyBarrage.setAdapter(raftingDetailsAdapter);
            raftingDetailsAdapter.setData(commentsBeanList);
            mRcyBarrage.start();


        }
    }


    public void setMessageStatus(BarrageEntity.CommentsBean bean) {

        mTvName.setText("昵称：" + bean.getUser_name());

        mTvWord.setVisibility(bean.getType_id() == 1 ? View.VISIBLE : View.GONE);
        mRlVoicePlay.setVisibility(bean.getType_id() == 1 ? View.GONE : bean.getType_id() == 2 ? View.VISIBLE : View.GONE);
        mRlVideoPlay.setVisibility(bean.getType_id() == 1 ? View.GONE : bean.getType_id() == 2 ? View.GONE : View.VISIBLE);
        if (bean.getType_id() == 1) {
            mTvWord.setText(bean.getContent());
        } else if (bean.getType_id() == 2) {
            totaltime = VideoUtil.getLocalVideoDuration(bean.getContent())+1;
            mTvTime.setText(totaltime + "S");
            mVideoView.setDecibel(0);
        } else if (bean.getType_id() == 3) {
            GlideUtil.create().loadLongImage(this, barrageEntity.getMessage().getImage(), mIvpic);
        }
    }

    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }


    @OnClick({R.id.toolbar_back, R.id.tv_passer_num, R.id.iv_play, R.id.rl_video_play})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_passer_num:   //传递详情
                    DeliveryDetailsActivity.start(this, id, false);
                    break;
                case R.id.iv_play: //语音播放
                    PermissionDialog.startVoicePlay(this, bean.getContent(), totaltime, mIvPlay, mVideoView, mTvTime);
                    break;
                case R.id.rl_video_play://播放视频
                    VideoActivity.start(this, bean.getContent(), false);
                    break;
            }
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
       ToastUtil.showToast(message);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        VideoUtil.close();
        VideoUtil.cleanCountDown();
    }
}