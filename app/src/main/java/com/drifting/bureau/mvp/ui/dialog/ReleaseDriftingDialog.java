package com.drifting.bureau.mvp.ui.dialog;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsEntity;
import com.drifting.bureau.mvp.ui.activity.index.ImagePreviewActivity;
import com.drifting.bureau.mvp.ui.activity.index.SelectImageActivity;
import com.drifting.bureau.mvp.ui.activity.index.VideoActivity;
import com.drifting.bureau.mvp.ui.activity.index.VideoRecordingActivity;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.BitmapUtil;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.util.ViewUtil;
import com.drifting.bureau.view.VoiceWave;
import com.hjq.shape.layout.ShapeLinearLayout;
import com.jess.arms.base.BaseDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * @Description: 发布漂流
 * @Author : WeiJiaQI
 * @Time : 2022/7/11 10:54
 */
public class ReleaseDriftingDialog extends BaseDialog implements View.OnClickListener {
    private LinearLayout mLlStarrySky, mLlParticipate, mLlJoin;
    private RelativeLayout mRlVoicePlay;
    private ShapeLinearLayout mLlImprint;
    private ImageView mIvReleaseCamera, mIvRelaseRecording, mIvPlay, mIvVideoPlay, mIvPic, mIvReport;
    private RecordingDialog recordingDialog;
    private ReportDialog reportDialog;
    private RelativeLayout mRlVideoPlay, mRlStartVoice;
    private TextView mTvTime, mTvNums, mTvParticipate, mTvIntoSpace, mTvNum, mTvTitle, mTvByTime, mTvImprint;
    private EditText mEtWord;
    private VoiceWave mVideoView;
    private Context context;
    private List<Object> objectList;
    private String path;
    private Bitmap cover;
    private int type = -1;
    private boolean isFirst;
    private CoverDialog coverDialog;
    private List<String> strings;
    private int status, total, second;
    private int totaltime;
    private CommentDetailsEntity commentDetailsEntity;
    private MoreDetailsEntity.RelevanceBean relevanceBean;

    public static final int SELECT_FINISH = 0x01;


    public ReleaseDriftingDialog(@NonNull Context context, int status, int total) {
        super(context);
        this.context = context;
        this.status = status;
        this.total = total;
    }

    public ReleaseDriftingDialog(@NonNull Context context, int status, CommentDetailsEntity entity, MoreDetailsEntity.RelevanceBean relevanceBean, int total) {
        super(context);
        this.context = context;
        this.status = status;
        this.commentDetailsEntity = entity;
        this.relevanceBean = relevanceBean;
        this.total = total;
    }

    @Override
    protected void initView() {
        super.initView();
        mRlVoicePlay = findViewById(R.id.rl_voice_play);
        mIvReleaseCamera = findViewById(R.id.iv_release_camrea);
        mIvRelaseRecording = findViewById(R.id.iv_release_recording);
        mTvTime = findViewById(R.id.tv_time);
        mVideoView = findViewById(R.id.videoView);

        mRlStartVoice = findViewById(R.id.rl_start_voice);
        mIvPlay = findViewById(R.id.iv_play);
        mRlVideoPlay = findViewById(R.id.rl_video_play);
        mIvVideoPlay = findViewById(R.id.iv_video_play);
        mIvPic = findViewById(R.id.iv_pic);
        mLlStarrySky = findViewById(R.id.ll_starry_sky);
        mEtWord = findViewById(R.id.et_word);
        mLlParticipate = findViewById(R.id.rl_participate);
        mTvNums = findViewById(R.id.tv_nums);
        mTvParticipate = findViewById(R.id.tv_participate);
        mTvIntoSpace = findViewById(R.id.tv_into_space);
        mLlImprint = findViewById(R.id.ll_imprint);
        mTvNum = findViewById(R.id.tv_num);
        mTvTitle = findViewById(R.id.tv_title);
        mTvByTime = findViewById(R.id.tv_by_time);
        mTvImprint = findViewById(R.id.tv_imprint);
        mLlJoin = findViewById(R.id.ll_join);
        mIvReport = findViewById(R.id.iv_report);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mRlStartVoice.setOnClickListener(this);
        mIvReleaseCamera.setOnClickListener(this);
        mIvRelaseRecording.setOnClickListener(this);

        mRlVideoPlay.setOnClickListener(this);
        mLlStarrySky.setOnClickListener(this);
        mLlParticipate.setOnClickListener(this);
        mLlImprint.setOnClickListener(this);
        mLlJoin.setOnClickListener(this);
        mTvIntoSpace.setOnClickListener(this);
        mIvReport.setOnClickListener(this);
        if (status == 1) {  //编辑
            mTvTitle.setText("发送漂流~~");
            mIvReport.setVisibility(View.GONE);
            mLlStarrySky.setVisibility(View.VISIBLE);
            mTvNums.setText(context.getString(R.string.free_times, total + ""));
        } else {//查看
            mTvTitle.setText("查看漂流~~");
            if (TextUtils.equals(Preferences.getUserId(), commentDetailsEntity.getUser_id() + "")) {
                mIvReport.setVisibility(View.GONE);
            } else {
                mIvReport.setVisibility(View.VISIBLE);
            }
            mIvReleaseCamera.setVisibility(View.GONE);
            mIvRelaseRecording.setVisibility(View.GONE);
            if (TextUtils.isEmpty(commentDetailsEntity.getContent())) {
                mEtWord.setText("收到一份来自元宇宙的电波传递");
            } else {
                mEtWord.setText(commentDetailsEntity.getContent());
            }
            mEtWord.setFocusable(false);
            mEtWord.setCursorVisible(false);
            if (!TextUtils.isEmpty(commentDetailsEntity.getAlbum())) {  //图片
                mRlVideoPlay.setVisibility(View.VISIBLE);
                mIvVideoPlay.setVisibility(View.GONE);
                GlideUtil.create().loadLongImage(context, commentDetailsEntity.getAlbum(), mIvPic);
            }
            if (!TextUtils.isEmpty(commentDetailsEntity.getVedio())) {  //视频
                mRlVideoPlay.setVisibility(View.VISIBLE);
                mIvVideoPlay.setVisibility(View.VISIBLE);
                GlideUtil.create().loadLongImage(context, commentDetailsEntity.getImage(), mIvPic);
            }
            if (!TextUtils.isEmpty(commentDetailsEntity.getAudio())) {//语音
                mRlVoicePlay.setVisibility(View.VISIBLE);

                totaltime = VideoUtil.getLocalVideoDuration(commentDetailsEntity.getAudio());
                mTvTime.setText(totaltime + "S");
                mVideoView.setDecibel(0);
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
                if (total == 0) {
                    mTvNums.setText("可添加好友");
                } else {
                    mTvNums.setText(context.getString(R.string.free_times, total + ""));
                }
            }
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_release_drifting;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_release_camrea:  //视频
                strings = new ArrayList<>();
                strings.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                strings.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                strings.add(Manifest.permission.CAMERA);
                strings.add(Manifest.permission.RECORD_AUDIO);
                if (PermissionDialog.requestPermissions((Activity) context, strings, 1)) {
                    if (type != -1 && !isFirst) {
                        coverDialog = new CoverDialog(context);
                        coverDialog.show();
                        coverDialog.setOnClickCallback(type -> {
                            if (type == CoverDialog.SELECT_FINISH) {
                                isFirst = true;
                                VideoRecordingActivity.start(context, false);
                            }
                        });
                    } else {
                        VideoRecordingActivity.start(context, false);
                    }
                } else {
                    ShowMessage("请开启拍照/存储/录音相关权限");
                }
                break;
            case R.id.iv_release_recording: //录音
                if (type != -1 && !isFirst) {
                    coverDialog = new CoverDialog(context);
                    coverDialog.show();
                    coverDialog.setOnClickCallback(type -> {
                        if (type == CoverDialog.SELECT_FINISH) {
                            isFirst = true;
                            startVocie();
                        }
                    });
                } else {
                    startVocie();
                }
                break;
            case R.id.rl_start_voice:  //语音播放
                if (status == 1) {
                    if (objectList != null) {
                        VideoUtil.startVoicePlay(objectList.get(0).toString(), objectList.get(1), mIvPlay, mVideoView, mTvTime);
                    }
                } else {  //查看播放
                    PermissionDialog.startVoicePlay((Activity) context, commentDetailsEntity.getAudio(), totaltime, mIvPlay, mVideoView, mTvTime);
                }
                break;

            case R.id.rl_video_play:  //视频播放
                if (status == 2) {  //查看
                    if (!TextUtils.isEmpty(commentDetailsEntity.getVedio())) {
                        VideoActivity.start(context, commentDetailsEntity.getVedio(), false);
                    } else {
                        ImagePreviewActivity.start(context,commentDetailsEntity.getAlbum());
                    }
                } else {
                    if (type == 1) { //视频
                        VideoActivity.start(context, path, false);
                    } else {  //查看图片
                        ImagePreviewActivity.start(context,path);
                    }
                }
                break;
            case R.id.ll_starry_sky:  //丢入星空
                if (TextUtils.isEmpty(mEtWord.getText().toString()) && objectList == null && path == null) {
                    ShowMessage("内容不能为空!");
                    return;
                }
                if (onStarrySkyClickCallback != null) {
                    onStarrySkyClickCallback.onStarrySkyClick(type, mEtWord.getText().toString(), path, objectList, cover,"");
                }
                break;
            case R.id.ll_join:
                if (onContentClickCallback != null) {
                    onContentClickCallback.onContetClick(commentDetailsEntity.getMessage_id() + "");
                }
                break;
            case R.id.ll_imprint:  //留下你的飘来印记
                status = 1;
                mTvTitle.setText("发送漂流~~");
                mLlImprint.setVisibility(View.GONE);
                mLlStarrySky.setVisibility(View.VISIBLE);
                mIvReleaseCamera.setVisibility(View.VISIBLE);
                mIvRelaseRecording.setVisibility(View.VISIBLE);
                mIvReport.setVisibility(View.GONE);
                mEtWord.setText("");
                mEtWord.setFocusableInTouchMode(true);
                mEtWord.setCursorVisible(true);
                mEtWord.setFocusable(true);
                mEtWord.requestFocus();
                mRlVoicePlay.setVisibility(View.GONE);
                mRlVideoPlay.setVisibility(View.GONE);
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
                        mRlVoicePlay.setVisibility(View.VISIBLE);

                        mTvTime.setText(objectList.get(1).toString() + "S");
                        mVideoView.setDecibel(0);
                        if (!TextUtils.isEmpty(path)) {
                            mRlVideoPlay.setVisibility(View.GONE);
                            path = null;
                        }
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

    public void setData(int index, String route) {
        type = index;
        path = route;
        deleteVoice();  //删除语音
        mRlVideoPlay.setVisibility(View.VISIBLE);
        if (type == 1) {//视频
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


    public void deleteVoice() {
        if (objectList != null) {
            mRlVoicePlay.setVisibility(View.GONE);
            VideoUtil.stop(mVideoView, mIvPlay, mTvTime, objectList.get(1));
            objectList = null;
        }
    }


    public void ShowMessage(String content) {
        ToastUtil.showToast(content);
    }
}
