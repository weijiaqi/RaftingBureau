package com.drifting.bureau.mvp.ui.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.BackSpaceEvent;
import com.drifting.bureau.data.event.PaymentEvent;
import com.drifting.bureau.data.event.VideoEvent;
import com.drifting.bureau.di.component.DaggerPostDriftingComponent;
import com.drifting.bureau.mvp.contract.PostDriftingContract;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.presenter.PostDriftingPresenter;
import com.drifting.bureau.mvp.ui.activity.pay.PaymentInfoActivity;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.drifting.bureau.mvp.ui.dialog.RaftingOrderDialog;
import com.drifting.bureau.mvp.ui.dialog.RecordingDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.BitmapUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.util.ViewGroupUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.view.VoiceWave;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
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
    LinearLayout mRlVoicePlay;
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
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_identity)
    TextView mTvIdentity;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.tv_starry_sky)
    TextView mTvStarrySky;

    private RecordingDialog recordingDialog;
    private List<Object> objectList;
    private String path;
    private RaftingOrderDialog raftingOrderDialog;
    private static final String BUNDLE_TYPE = "bundle_type";
    private static final String BUNDLE_EXPLORE_ID = "bundle_explore_id";
    private static final String BUNDLE_MESSAGE_ID = "bundle_message_id";
    private int type, explore_id, message_id;
    private int selectPostion = 1;
    private Bitmap cover;
    private PublicDialog publicDialog;
    private CreatewithfileEntity createwithfileEntity;

    public static PostDriftingFragment newInstance(int type, int explore_id, int message_id) {
        PostDriftingFragment fragment = new PostDriftingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_TYPE, type);
        bundle.putInt(BUNDLE_MESSAGE_ID, message_id);
        bundle.putInt(BUNDLE_EXPLORE_ID, explore_id);
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
        explore_id = args.getInt(BUNDLE_EXPLORE_ID);
        message_id = args.getInt(BUNDLE_MESSAGE_ID);
    }

    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        initListener();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void initListener() {
        if (type == 1) {
            mRlVideoDelete.setVisibility(View.VISIBLE);
        } else {
            mTvStarrySky.setText("传递到下个星云");
            mTvMyInfo.setVisibility(View.VISIBLE);
            mLlInfo.setLayoutParams(ViewGroupUtil.setMargin(ArmsUtils.dip2px(mContext, 17), 0, 0, 0));
        }
        setSelected(selectPostion);

        getUserInfo();
    }

    public void getUserInfo() {
        RequestUtil.create().userplayer(Preferences.getUserId(), entity -> {
            if (entity != null && entity.getCode() == 200) {
                mTvName.setText(entity.getData().getUser().getName());
                mTvIdentity.setText("(" + entity.getData().getUser().getLevel_name() + ")");
            }
        });
        RequestUtil.create().platformtimes(explore_id, entity -> {
            if (entity != null && entity.getCode() == 200) {
                int total = entity.getData().getCreate_times() + entity.getData().getCommon_times();
                if (PostDriftingFragment.this.isAdded()) {
                    mTvNum.setText(getString(R.string.free_times, total + ""));
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.tv_word, R.id.tv_voice, R.id.tv_video, R.id.iv_voice, R.id.iv_video, R.id.iv_play, R.id.iv_delete, R.id.rl_video_delete, R.id.rl_video_play, R.id.ll_starry_sky})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_word:  //文字
                    selectPostion = 1;
                    setSelected(selectPostion);
                    break;
                case R.id.tv_voice: //语音
                    selectPostion = 2;
                    setSelected(selectPostion);
                    break;
                case R.id.tv_video://视频
                    selectPostion = 3;
                    setSelected(selectPostion);
                    break;
                case R.id.iv_voice:  //语音录制
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
                        objectList = null;
                    }
                    break;
                case R.id.rl_video_delete:  //删除视频
                    setVideoStatus(1);
                    path = null;
                    break;
                case R.id.rl_video_play: //播放视频
                    if (mPresenter != null) {
                        mPresenter.PlayVideo(getActivity(), path);
                    }
                    break;
                case R.id.ll_starry_sky:  //丢入星空

                    if (selectPostion == 1) {  //文字漂流
                        if (StringUtil.isEmpty(mEtWord.getText().toString())) {
                            showMessage("请输入文字内容!");
                            return;
                        }
                        mEtWord.clearFocus();
                        if (mPresenter != null) {
                            if (type == 1) {
                                mPresenter.createwithword(1, 1, mEtWord.getText().toString(), 0);
                            } else {
                                mPresenter.createwithword(1, explore_id, mEtWord.getText().toString(), message_id);
                            }
                        }
                    } else if (selectPostion == 2) { //语音漂流
                        if (objectList == null) {
                            showMessage("请进行语音录制!");
                            return;
                        }
                        if (mPresenter != null) {
                            if (type == 1) {
                                mPresenter.createwithVoice(2, 1, new File(objectList.get(0).toString()), 0);
                            } else {
                                mPresenter.createwithVoice(2, explore_id, new File(objectList.get(0).toString()), message_id);
                            }
                        }
                    } else if (selectPostion == 3) { //视频漂流
                        if (path == null || TextUtils.isEmpty(path)) {
                            showMessage("请进行视频录制!");
                            return;
                        }
                        if (mPresenter != null) {
                            if (type == 1) {
                                mPresenter.createwithVideo(3, 1, new File(VideoUtil.getRunLog()), BitmapUtil.saveBitmapFile(mContext, cover), 0);
                            } else {
                                mPresenter.createwithVideo(3, explore_id, new File(VideoUtil.getRunLog()), BitmapUtil.saveBitmapFile(mContext, cover), message_id);
                            }
                        }
                    }
                    break;
            }
        }
    }


    /**
     * @description 按钮选中
     */
    public void setSelected(int postion) {
        mTvWord.setBackgroundResource(postion == 1 ? R.drawable.drifting_selected : R.drawable.drifting_unselected);
        mTvVoice.setBackgroundResource(postion == 2 ? R.drawable.drifting_selected : R.drawable.drifting_unselected);
        mTvVideo.setBackgroundResource(postion == 3 ? R.drawable.drifting_selected : R.drawable.drifting_unselected);
        mEtWord.setVisibility(postion == 1 ? View.VISIBLE : View.GONE);
        mRlVoice.setVisibility(postion == 2 ? View.VISIBLE : View.GONE);
        mRlVideo.setVisibility(postion == 3 ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onCreatewithwordSuccess(CreatewithfileEntity entity) {
        if (entity != null) {
            if (type == 1) {
                if (entity.getNeed_pay() == 1) {
                    createwithfileEntity = entity;
                    if (mPresenter != null) {
                        mPresenter.skulist(1, 0);
                    }
                } else {
                    sendSuccess();
                }
            } else {
                sendSuccess();
            }
        }
    }

    @Override
    public void onSkuListSuccess(SkuListEntity skuListEntity) {
        if (skuListEntity != null) {
            raftingOrderDialog = new RaftingOrderDialog(mContext, skuListEntity);
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
                        mPresenter.createOrder(createwithfileEntity.getMessage_id(), sb.toString());
                    }
                }
            });
        }
    }

    @Override
    public void onCreateOrderSuccess(CreateOrderEntity entity) {
        if (entity != null) {
            PaymentInfoActivity.start(mContext, 1, entity.getSn(), entity.getTotal_amount(), false);
        }
    }

    @Override
    public void onNetError() {

    }

    public Fragment getFragment() {
        return this;
    }

    @Override
    public void PermissionVoiceSuccess() {
        //语音
        recordingDialog = new RecordingDialog((Activity) mContext);
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
          //  cover = BitmapUtil.createVideoThumbnail(path,MediaStore.Images.Thumbnails.MINI_KIND) ;
            mIvpic.setImageBitmap(cover);
//            VideoUtil.compressVideo(mContext, path);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PaymentEvent(PaymentEvent event) {  //购买成功回调
        if (event != null) {
            sendSuccess();
        }
    }


    /**
     * 消息发送成功
     */
    public void sendSuccess() {
        publicDialog = new PublicDialog(mContext);
        publicDialog.show();
        publicDialog.setCancelable(false);
        publicDialog.setTitleText("已成功发送");
        publicDialog.setContentText("可在“关于我-漂流轨迹”中 查看漂流记录");

        publicDialog.setButtonText("返回星际");
        publicDialog.setOnClickCallback(status -> {
            if (status == PublicDialog.SELECT_FINISH) {
                getActivity().finish();
                EventBus.getDefault().post(new BackSpaceEvent());
            }
        });
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
        if (objectList != null) {
            VideoUtil.stop(mVideoView, mIvPlay, mTvTime, objectList.get(1));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        VideoUtil.close();
        RequestUtil.create().disDispose();
    }
}