package com.drifting.bureau.mvp.ui.dialog;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsForMapEntity;
import com.drifting.bureau.mvp.ui.activity.index.AddTopicActivity;
import com.drifting.bureau.mvp.ui.activity.index.ImagePreviewActivity;
import com.drifting.bureau.mvp.ui.activity.index.VideoActivity;
import com.drifting.bureau.mvp.ui.activity.index.VideoRecordingActivity;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.BitmapUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.view.VoiceWave;
import com.drifting.bureau.view.guide.MapGuideView;
import com.hjq.shape.layout.ShapeLinearLayout;
import com.jess.arms.base.BaseDialog;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author 卫佳琪1
 * @description 发布漂流
 * @time 19:24 19:24
 */

public class MapSendDriftDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private ImageView mIvReleaseCamera, mIvReleaseRecording, mIvReleaseWord, mIvRecording, mIvPlay, mIvVideo, mIvVideoPlay, mIvPic, mIvReport;
    private TextView mTvTip, mTvTime, mTvAddTopic, mTvTitle, mTvParticipate, mTvIntoSpace, mTvNums, mTvByTime, mTvImprint;
    private EditText mEtWord;
    private View mViewTop;
    private RelativeLayout mRlVoicePlay, mRlViedeoPlay, mRlStartVoice;
    private LinearLayout mLlStarrySky, mLlBottom, mLlParticipate, mLlJoin;
    private ShapeLinearLayout mLlImprint;
    private VoiceWave mVideoView;
    private RecordingDialog recordingDialog;
    private ReportDialog reportDialog;
    private MapGuideView mapGuideView;
    public static final int SELECT_FINISH = 0x01;

    private List<Object> objectList;
    private List<String> strings;
    private int status, total, totaltime, second,free;

    private int cameratype = -1;
    private String path, tag;
    private boolean isCameraFirst;
    private CoverDialog coverDialog;
    private Bitmap cover;

    private CommentDetailsEntity commentDetailsEntity;
    private MoreDetailsForMapEntity.RelevanceBean relevanceBean;

    public MapSendDriftDialog(@NonNull Context context, int status, int total) {
        super(context);
        this.context = context;
        this.status = status;
        this.total = total;
    }

    public MapSendDriftDialog(@NonNull Context context, int status, CommentDetailsEntity entity, MoreDetailsForMapEntity.RelevanceBean relevanceBean, int total,int free) {
        super(context);
        this.context = context;
        this.status = status;
        this.commentDetailsEntity = entity;
        this.relevanceBean = relevanceBean;
        this.total = total;
        this.free=free;
    }

    @Override
    protected void initView() {
        super.initView();
        mRlViedeoPlay = findViewById(R.id.rl_video_play);
        mRlVoicePlay = findViewById(R.id.rl_voice_play);
        mIvReleaseCamera = findViewById(R.id.iv_release_camrea);
        mIvReleaseRecording = findViewById(R.id.iv_release_recording);
        mIvReleaseWord = findViewById(R.id.iv_release_word);
        mIvRecording = findViewById(R.id.iv_recording);
        mViewTop=findViewById(R.id.view_top);
        mTvTip = findViewById(R.id.tv_tip);
        mTvTime = findViewById(R.id.tv_time);
        mVideoView = findViewById(R.id.videoView);
        mIvPlay = findViewById(R.id.iv_play);
        mEtWord = findViewById(R.id.et_word);
        mIvVideo = findViewById(R.id.iv_video);
        mIvVideoPlay = findViewById(R.id.iv_video_play);
        mIvPic = findViewById(R.id.iv_pic);
        mLlStarrySky = findViewById(R.id.ll_starry_sky);
        mTvAddTopic = findViewById(R.id.tv_add_topic);
        mTvTitle = findViewById(R.id.tv_title);
        mLlBottom = findViewById(R.id.ll_bottom);
        mIvReport = findViewById(R.id.iv_report);
        mLlParticipate = findViewById(R.id.rl_participate);
        mTvParticipate = findViewById(R.id.tv_participate);
        mTvIntoSpace = findViewById(R.id.tv_into_space);
        mLlJoin = findViewById(R.id.ll_join);
        mTvNums = findViewById(R.id.tv_nums);
        mTvByTime = findViewById(R.id.tv_by_time);
        mTvImprint = findViewById(R.id.tv_imprint);
        mLlImprint = findViewById(R.id.ll_imprint);
        mRlStartVoice = findViewById(R.id.rl_start_voice);
        mapGuideView = findViewById(R.id.guide_view);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mIvReleaseCamera.setOnClickListener(this);
        mIvReleaseRecording.setOnClickListener(this);
        mIvReleaseWord.setOnClickListener(this);
        mIvRecording.setOnClickListener(this);
        mIvVideo.setOnClickListener(this);
        mLlStarrySky.setOnClickListener(this);
        mIvPic.setOnClickListener(this);
        mTvAddTopic.setOnClickListener(this);
        mLlJoin.setOnClickListener(this);
        mLlParticipate.setOnClickListener(this);
        mIvReport.setOnClickListener(this);
        mLlImprint.setOnClickListener(this);
        mTvIntoSpace.setOnClickListener(this);
        mRlStartVoice.setOnClickListener(this);

        //是否展示引导页
        mapGuideView.setVisibility(!Preferences.isPostGuide()?View.VISIBLE:View.GONE);
        mapGuideView.setOnClickCallback(() -> mapGuideView.setVisibility(View.INVISIBLE));


        if (status == 1) {  //编辑
            mIvReport.setVisibility(View.GONE);
            mLlStarrySky.setVisibility(View.VISIBLE);
            if (free==1){
                mTvNums.setText("可添加好友");
            }else {
                mTvNums.setText(context.getString(R.string.free_times, total + ""));
            }
            toggleIcon(2);
        } else { //查看
            mTvTitle.setText("查看Ta的故事~~");
            if (!TextUtils.isEmpty(commentDetailsEntity.getTag_name())) {
                mTvAddTopic.setVisibility(View.VISIBLE);
                tag = commentDetailsEntity.getTag();
                mTvAddTopic.setText("#" + commentDetailsEntity.getTag_name());
                mTvAddTopic.setClickable(false);
            } else {
                mTvAddTopic.setVisibility(View.GONE);
            }
            if (TextUtils.equals(Preferences.getUserId(), commentDetailsEntity.getUser_id() + "")) {
                mIvReport.setVisibility(View.GONE);
            } else {
                mIvReport.setVisibility(View.VISIBLE);
            }
            mLlBottom.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(commentDetailsEntity.getVedio())) {  //视频
                mRlViedeoPlay.setVisibility(View.VISIBLE);
                mIvVideoPlay.setVisibility(View.VISIBLE);
                mViewTop.setVisibility(View.VISIBLE);
                mIvVideo.setVisibility(View.INVISIBLE);
                GlideUtil.create().loadLongImage(context, commentDetailsEntity.getImage(), mIvPic);
            } else if (!TextUtils.isEmpty(commentDetailsEntity.getAudio())) {//语音
                mRlVoicePlay.setVisibility(View.VISIBLE);
                mIvPlay.setVisibility(View.VISIBLE);
                mIvRecording.setVisibility(View.INVISIBLE);
                mTvTip.setVisibility(View.GONE);
                mVideoView.setVisibility(View.VISIBLE);
                totaltime = VideoUtil.getLocalVideoDuration(context,commentDetailsEntity.getAudio());
                mTvTime.setText(totaltime + "S");
                mVideoView.setDecibel(0);
            } else if (!TextUtils.isEmpty(commentDetailsEntity.getAlbum())) {  //图片
                mRlViedeoPlay.setVisibility(View.VISIBLE);
                mViewTop.setVisibility(View.VISIBLE);
                mIvVideoPlay.setVisibility(View.GONE);
                mIvVideo.setVisibility(View.INVISIBLE);
                GlideUtil.create().loadLongImage(context, commentDetailsEntity.getAlbum(), mIvPic);
            } else if (!TextUtils.isEmpty(commentDetailsEntity.getContent())) {  //判断文字不为空
                mEtWord.setVisibility(View.VISIBLE);
                mEtWord.setFocusable(false);
                mEtWord.setCursorVisible(false);
                mEtWord.setText(commentDetailsEntity.getContent());
            }


            if (relevanceBean.getAttend() == 1) { //是否参与了该话题，0否、1是
                if (relevanceBean.getIs_comment() == 1) {
                    mLlParticipate.setVisibility(View.VISIBLE);
                    mTvParticipate.setText("已参与");
                    mTvIntoSpace.setVisibility(View.GONE);
                    mTvNums.setVisibility(View.GONE);
                    mLlJoin.setClickable(false);
                } else {
                    mLlImprint.setVisibility(View.VISIBLE);
                    second = relevanceBean.getFootprint_rest();
                    if (second > 0) {
                        mTvByTime.setText(DateUtil.TimeRemaining(second) + "后将会自动发送");
                    } else {
                        mLlImprint.getShapeDrawableBuilder().setSolidColor(context.getColor(R.color.color_99)).intoBackground();
                        mTvImprint.setText("不能留下印记");
                        mTvByTime.setText("订单已失效");
                        mLlImprint.setClickable(false);
                    }
                }
            } else {
                mLlParticipate.setVisibility(View.VISIBLE);
                if (total == 0 || free==1) {
                    mTvNums.setText("可添加好友");
                } else {
                    mTvNums.setText(context.getString(R.string.free_times, total + ""));
                }
            }
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_send_drift;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }

    @Override
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.iv_release_camrea:  //拍照
                    toggleIcon(1);
                    break;
                case R.id.iv_release_recording: //语音
                    toggleIcon(2);
                    break;
                case R.id.iv_release_word://文字
                    toggleIcon(3);
                    mEtWord.requestFocus();
                    break;
                case R.id.iv_recording:  //录音
                    startVocie();
                    break;
                case R.id.rl_start_voice: //语音播放
                    if (status == 1 || status == 3) {
                        if (objectList != null) {
                            VideoUtil.startVoicePlay(objectList.get(0).toString(), objectList.get(1), mIvPlay, mVideoView, mTvTime);
                        }
                    } else {  //查看播放
                        PermissionDialog.startVoicePlay((Activity) context, commentDetailsEntity.getAudio(), totaltime, mIvPlay, mVideoView, mTvTime);
                    }
                    break;
                case R.id.iv_video:  //拍摄视频
                    startCamera();
                    break;
                case R.id.iv_pic:  //添加图片/视频
                    if (status == 1 || status == 3) {
                        if (path != null) {
                            if (cameratype == 1) { //视频
                                VideoActivity.start(context, path, false);
                            } else {  //查看图片
                                ImagePreviewActivity.start(context,path);
                            }
                        } else {
                            startCamera();
                        }
                    } else {
                        if (!TextUtils.isEmpty(commentDetailsEntity.getVedio())) {
                            VideoActivity.start(context, commentDetailsEntity.getVedio(), false);
                        } else {
                            ImagePreviewActivity.start(context,commentDetailsEntity.getAlbum());
                        }
                    }
                    break;
                case R.id.tv_add_topic:  //添加话题
                    AddTopicActivity.start(context, false);
                    break;
                case R.id.ll_starry_sky:  //丢入星空
                    if (TextUtils.isEmpty(mEtWord.getText().toString()) && objectList == null && path == null) {
                        ShowMessage("内容不能为空!");
                        return;
                    }
                    if (status == 1) { //发起漂流
                        if (tag == null && TextUtils.isEmpty(tag)) {
                            ShowMessage("请添加话题标签!");
                            return;
                        }
                    }
                    if (onStarrySkyClickCallback != null) {
                        onStarrySkyClickCallback.onStarrySkyClick(cameratype, mEtWord.getText().toString(), path, objectList, cover, tag);
                    }
                    break;
                case R.id.ll_join:
                    if (onContentClickCallback != null) {
                        onContentClickCallback.onContetClick(commentDetailsEntity.getMessage_id() + "");
                    }
                    break;
                case R.id.ll_imprint:  //留下你的飘来印记
                    status = 3;
                    mTvTitle.setText("留下我的故事~~");
                    mLlImprint.setVisibility(View.GONE);
                    mLlStarrySky.setVisibility(View.VISIBLE);
                    mIvReport.setVisibility(View.GONE);


                    if (!TextUtils.isEmpty(mEtWord.getText().toString())) {
                        mEtWord.setText("");
                        mEtWord.setFocusableInTouchMode(true);
                        mEtWord.setCursorVisible(true);
                        mEtWord.setFocusable(true);
                    }

                    mLlBottom.setVisibility(View.VISIBLE);
                    mEtWord.setVisibility(View.GONE);


                    //删除语音
                    deleteVoice();
                    mRlVoicePlay.setVisibility(View.VISIBLE);

                    mViewTop.setVisibility(View.GONE);
                    break;
                case R.id.tv_into_space:  //不感兴趣
                    if (onClickCallback != null) {
                        onClickCallback.onClickType(SELECT_FINISH);
                    }
                    break;
                case R.id.iv_report://举报
                    reportDialog = new ReportDialog(context, commentDetailsEntity.getMessage_id(), commentDetailsEntity.getComment_id());
                    reportDialog.show();
                    break;
            }
        }
    }

    /**
     * @description 拍摄视频
     */
    public void startCamera() {
        strings = new ArrayList<>();
        strings.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        strings.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        strings.add(Manifest.permission.CAMERA);
        if (PermissionDialog.requestPermissions((Activity) context, strings, 1)) {
            if (cameratype != -1 && !isCameraFirst) {
                coverDialog = new CoverDialog(context);
                coverDialog.show();
                coverDialog.setOnClickCallback(type -> {
                    if (type == CoverDialog.SELECT_FINISH) {
                        isCameraFirst = true;
                        VideoRecordingActivity.start(context, false);
                    }
                });
            } else {
                VideoRecordingActivity.start(context, false);
            }
        } else {
            ShowMessage("请开启拍照/存储/录音相关权限");
        }
    }

    /**
     * @description 录音
     */
    public void startVocie() {
        PermissionDialog.requestAudioPermissions((Activity) context, new PermissionDialog.PermissionCallBack() {
            @Override
            public void onSuccess() {
                //语音
                recordingDialog = new RecordingDialog((Activity) context);
                recordingDialog.setCanceledOnTouchOutside(false);
                recordingDialog.show();
                recordingDialog.setOnMoreClickCallback((type, list) -> {
                    objectList = list;
                    if (objectList.size() > 0) {
                        mIvPlay.setVisibility(View.VISIBLE);
                        mVideoView.setVisibility(View.VISIBLE);
                        mTvTime.setVisibility(View.VISIBLE);
                        mTvTip.setVisibility(View.GONE);

                        mTvTime.setText(objectList.get(1).toString() + "S");
                        mVideoView.setDecibel(0);
                    }
                });
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onAlwaysFailure() {
                PermissionDialog.showDialog((Activity) context, "android.permission.RECORD_AUDIO");
            }
        });
    }


    public void setTag(String tag, String tagname) {
        this.tag = tag;
        mTvAddTopic.setText("#" + tagname);
    }


    public void setData(int index, String route) {
        cameratype = index;
        path = route;
        if (cameratype == 1) {//视频
            mIvVideoPlay.setVisibility(View.VISIBLE);
            cover = BitmapUtil.createVideoThumbnail(context, path);
        } else {
            mIvVideoPlay.setVisibility(View.GONE);
            cover = BitmapUtil.openImage(path);
            Luban.with(context).load(path).ignoreBy(100)
                    .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            path = file.getAbsolutePath();
                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                        }
                    }).launch();
        }
        mIvPic.setImageBitmap(cover);
    }


    /**
     * @description 切换底部图标
     */
    public void toggleIcon(int isType) {
        //删除语音
        deleteVoice();
        //删除视频
        if (!TextUtils.isEmpty(path)) {
            cameratype = -1;
            isCameraFirst = false;
            mIvPic.setImageResource(R.drawable.add_video_thumb);
            mIvVideoPlay.setVisibility(View.GONE);
            path = null;
        }

        if (!TextUtils.isEmpty(mEtWord.getText().toString())) {
            mEtWord.setText("");
        }

        mIvReleaseCamera.setVisibility(isType == 1 ? View.GONE : View.VISIBLE);
        mRlViedeoPlay.setVisibility(isType == 1 ? View.VISIBLE : View.GONE);

        mIvReleaseRecording.setVisibility(isType == 2 ? View.GONE : View.VISIBLE);
        mRlVoicePlay.setVisibility(isType == 2 ? View.VISIBLE : View.GONE);


        mIvReleaseWord.setVisibility(isType == 3 ? View.GONE : View.VISIBLE);
        mEtWord.setVisibility(isType == 3 ? View.VISIBLE : View.GONE);
    }

    /**
     * @description 删除语音
     */
    public void deleteVoice() {
        if (objectList != null) {
            mIvPlay.setVisibility(View.GONE);
            mVideoView.setVisibility(View.GONE);
            mTvTime.setVisibility(View.GONE);
            mTvTip.setVisibility(View.VISIBLE);
            VideoUtil.stop(mVideoView, mIvPlay, mTvTime, objectList.get(1));
            objectList = null;
        }
    }

    public void ShowMessage(String content) {
        ToastUtil.showToast(content);
    }
}
