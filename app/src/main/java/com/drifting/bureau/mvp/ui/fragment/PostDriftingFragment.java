package com.drifting.bureau.mvp.ui.fragment;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.drifting.bureau.R;
import com.drifting.bureau.data.event.VideoEvent;
import com.drifting.bureau.mvp.ui.dialog.RaftingOrderDialog;
import com.drifting.bureau.mvp.ui.dialog.RecordingDialog;
import com.drifting.bureau.util.BitmapUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.util.ViewGroupUtil;
import com.drifting.bureau.view.VoiceWave;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.component.DaggerPostDriftingComponent;
import com.drifting.bureau.di.module.PostDriftingModule;
import com.drifting.bureau.mvp.contract.PostDriftingContract;
import com.drifting.bureau.mvp.presenter.PostDriftingPresenter;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/19 09:51
 *
 * @author 发布漂流
 * module name is PostDriftingFragment
 */
public class PostDriftingFragment extends BaseFragment<PostDriftingPresenter> implements PostDriftingContract.View {
    @BindView(R.id.tv_word)
    TextView mTvWord;
    @BindView(R.id.tv_voice)
    TextView mTvVoice;
    @BindView(R.id.tv_video)
    TextView mTvVideo;
    @BindView(R.id.et_word)
    EditText mEtWord;
    @BindView(R.id.rl_voice)
    RelativeLayout mRlVoice;
    @BindView(R.id.rl_video)
    RelativeLayout mRlVideo;
    @BindView(R.id.iv_voice)
    ImageView mIvVoice;
    @BindView(R.id.iv_video)
    ImageView mIvVideo;
    @BindView(R.id.rl_voice_play)
    RelativeLayout mRlVoicePlay;
    @BindView(R.id.iv_delete)
    ImageView mIvDelete;
    @BindView(R.id.iv_play)
    ImageView mIvPlay;
    @BindView(R.id.videoView)
    VoiceWave mVideoView;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.rl_video_delete)
    RelativeLayout mRlVideoDelete;
    @BindView(R.id.rl_video_play)
    RelativeLayout mRlVideoPlay;
    @BindView(R.id.iv_pic)
    ImageView mIvpic;
    @BindView(R.id.ll_info)
    LinearLayout mLlInfo;
    @BindView(R.id.tv_my_info)
    TextView mTvMyInfo;
    private RecordingDialog recordingDialog;
    private List<Object> objectList;
    private String path;
    private RaftingOrderDialog raftingOrderDialog;
    private static final String BUNDLE_TYPE = "bundle_type";
    private int type;

    public static PostDriftingFragment newInstance(int type) {
        PostDriftingFragment fragment = new PostDriftingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPostDriftingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_drifting, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        type = (args != null) ? args.getInt(BUNDLE_TYPE) : 1;
    }

    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //setToolBarNoBack(toolbar, "PostDrifting");
        initListener();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void initListener() {
        if (type == 1) {
            mRlVideoDelete.setVisibility(View.VISIBLE);
        }else {
            mTvMyInfo.setVisibility(View.VISIBLE);
            mLlInfo.setLayoutParams(ViewGroupUtil.setMargin(ArmsUtils.dip2px(mContext,17),0,0,0));
        }
        setSelected(1);
    }


    @OnClick({R.id.tv_word, R.id.tv_voice, R.id.tv_video, R.id.iv_voice, R.id.iv_video, R.id.iv_play, R.id.iv_delete, R.id.rl_video_delete, R.id.rl_video_play, R.id.tv_starry_sky})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_word:  //文字
                    setSelected(1);
                    break;
                case R.id.tv_voice: //语音
                    setSelected(2);
                    break;
                case R.id.tv_video://视频
                    setSelected(3);
                    break;
                case R.id.iv_voice:  //语音
                    if (mPresenter != null) {
                        mPresenter.showDialog(getActivity());
                    }
                    break;
                case R.id.iv_video: //视频
                    if (mPresenter != null) {
                        mPresenter.startVideo(getActivity());
                    }
                    break;
                case R.id.iv_play: //语音播放
                    if (objectList != null) {
                        VideoUtil.startVoicePlay(objectList.get(0).toString(), objectList.get(1), mIvPlay, mVideoView, mTvTime);
                    }
                    break;
                case R.id.iv_delete: //删除语音播放
                    if (objectList != null) {
                        VideoUtil.stop(mVideoView, mIvPlay, mTvTime, objectList.get(1));
                        setVoiceStatus(1);
                    }
                    break;
                case R.id.rl_video_delete:  //删除视频
                    setVideoStatus(1);
                    break;
                case R.id.rl_video_play: //播放视频
                    if (mPresenter != null) {
                        mPresenter.PlayVideo(getActivity(), path);
                    }
                    break;
                case R.id.tv_starry_sky:  //丢入星空
                    raftingOrderDialog = new RaftingOrderDialog(mContext);
                    raftingOrderDialog.show();
                    break;
            }
        }
    }


    /**
     * @description 按钮选中
     */
    public void setSelected(int type) {
        mTvWord.setBackgroundResource(type == 1 ? R.drawable.drifting_selected : R.drawable.drifting_unselected);
        mTvVoice.setBackgroundResource(type == 2 ? R.drawable.drifting_selected : R.drawable.drifting_unselected);
        mTvVideo.setBackgroundResource(type == 3 ? R.drawable.drifting_selected : R.drawable.drifting_unselected);
        mEtWord.setVisibility(type == 1 ? View.VISIBLE : View.GONE);
        mRlVoice.setVisibility(type == 2 ? View.VISIBLE : View.GONE);
        mRlVideo.setVisibility(type == 3 ? View.VISIBLE : View.GONE);
    }


    public Fragment getFragment() {
        return this;
    }

    @Override
    public void PermissionVoiceSuccess() {
        //语音
        recordingDialog = new RecordingDialog((Activity)mContext);
        recordingDialog.setCanceledOnTouchOutside(false);
        recordingDialog.show();
        recordingDialog.setOnMoreClickCallback((type, list) -> {
            objectList = list;
            if (objectList.size() > 0) {
                setVoiceStatus(2);
                mTvTime.setText(objectList.get(1).toString() + "S");
                mVideoView.setDecibel(0);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void VideoEvent(VideoEvent event) {
        if (event != null) {
            setVideoStatus(2);
            path = event.getPath();
            mIvpic.setImageBitmap(BitmapUtil.getVideoThumb(path));
            VideoUtil.compressVideo(mContext, path);
        }
    }


    /**
     * @description 语音控件展示状态
     */
    public void setVoiceStatus(int status) {
        mIvVoice.setVisibility(status == 1 ? View.VISIBLE : View.GONE);
        mRlVoicePlay.setVisibility(status == 1 ? View.GONE : View.VISIBLE);
        mIvDelete.setVisibility(status == 1 ? View.GONE : View.VISIBLE);
    }

    /**
     * @description 语音控件展示状态
     */
    public void setVideoStatus(int status) {
        mIvVideo.setVisibility(status == 1 ? View.VISIBLE : View.GONE);
        mRlVideoPlay.setVisibility(status == 1 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (objectList!=null){
            VideoUtil.stop(mVideoView, mIvPlay, mTvTime, objectList.get(1));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        VideoUtil.close();
        VideoUtil.cleanCountDown();
    }
}