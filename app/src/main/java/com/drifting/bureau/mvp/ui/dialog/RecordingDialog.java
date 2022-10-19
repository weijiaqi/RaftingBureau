package com.drifting.bureau.mvp.ui.dialog;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.buihha.audiorecorder.Mp3Recorder;
import com.drifting.bureau.R;
import com.drifting.bureau.util.FileUtil;

import com.drifting.bureau.util.StorageUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.view.CircleProgressView;
import com.drifting.bureau.view.VoiceWave;
import com.jess.arms.base.BaseDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 语音录制
 */
public class RecordingDialog extends BaseDialog implements View.OnClickListener {

    private ImageView mIvNext;

    private TextView mTvTime, mTvPlayTime, mTvPlay, mTvdelete, mTvEnd;
    private LinearLayout mLlPrepare, mLlStartRecord, mLlplay;
    private CircleProgressView mProgress, cPlay;
    private RelativeLayout mRlplay;
    private View mStyle;
    private VoiceWave voiceWave;
    private int time;
    private int isRecording = 0;
    private int isPlaying = 0;
    private String mMp3Path = null;
    private Activity activity;

    private Mp3Recorder mRecorder;
    //语音操作对象
    private MediaPlayer mPlayer = null;
    private int totaltime;


    public RecordingDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void initView() {
        super.initView();
        mIvNext = findViewById(R.id.iv_next);
        mTvTime = findViewById(R.id.tv_time);
        mLlPrepare = findViewById(R.id.ll_prepare);
        mLlStartRecord = findViewById(R.id.ll_start);
        mProgress = findViewById(R.id.progress);
        mRlplay = findViewById(R.id.rl_play);
        mTvPlayTime = findViewById(R.id.tv_play_time);
        mStyle = findViewById(R.id.iv_style);
        cPlay = findViewById(R.id.cp_play);
        mTvPlay = findViewById(R.id.tv_play);
        mTvdelete = findViewById(R.id.tv_delete);
        mTvEnd = findViewById(R.id.tv_end);
        voiceWave = findViewById(R.id.videoView);

        mLlplay = findViewById(R.id.ll_play);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mLlStartRecord.setVisibility(View.GONE);
        mLlPrepare.setVisibility(View.VISIBLE);
        mIvNext.setOnClickListener(this);
        mLlPrepare.setOnClickListener(this);
        mTvTime.setOnClickListener(this);
        mRlplay.setOnClickListener(this);
        mLlStartRecord.setOnClickListener(this);
        mTvdelete.setOnClickListener(this);
        mTvEnd.setOnClickListener(this);
        mLlplay.setOnClickListener(this);
        voiceWave.setDecibel(0);

    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_recording;
    }

    @Override
    protected float getDialogWith() {
        return 1;
    }

    @Override
    protected void getWindows(Window window) {
        super.getWindows(window);
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.EnterDialog);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_next:
                InVoiceStatus();
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                }
                dismiss();
                break;
            case R.id.ll_start:
            case R.id.ll_prepare: //开始录音
                PermissionDialog.requestPermissions(activity, new PermissionDialog.PermissionCallBack() {
                    @Override
                    public void onSuccess() {
                        startRecording();
                    }

                    @Override
                    public void onFailure() {

                    }

                    @Override
                    public void onAlwaysFailure() {
                        PermissionDialog.showDialog(activity, "android.permission.WRITE_EXTERNAL_STORAGE");
                    }
                });
                break;
            case R.id.ll_play: //开始播放
                startPlay();
                break;
            case R.id.tv_delete: //删除
                deleteVoice();
                break;
            case R.id.tv_end:  //完成
                if (mMp3Path != null) {
                    dismiss();
                    InVoiceStatus();
                    close();
                    List<Object> list = new ArrayList<>();
                    list.add(mMp3Path);
                    list.add(totaltime);
                    if (onMoreClickCallback != null) {
                        onMoreClickCallback.onMoreClick(1, list);
                    }
                }
                break;
        }
    }

    /**
    * @description 删除录音
    */
    public void deleteVoice(){
        EndAudition();
        deleteFile();
        mRlplay.setVisibility(View.GONE);
        mLlPrepare.setVisibility(View.VISIBLE);
    }


    public void close() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    /**
     * @description 开始录音
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startRecording() {
        try {
            if (isRecording == 0) {
                mLlPrepare.setVisibility(View.GONE);
                mLlStartRecord.setVisibility(View.VISIBLE);
                //将按钮换成停止录音
                isRecording = 1;
                mRecorder = new Mp3Recorder();
                mRecorder.setOnRecordListener(new Mp3Recorder.OnRecordListener() {
                    @Override
                    public void onStart() {
                        //开始录音
                        totaltime = 60;
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onError() {
                        totaltime = 60;
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onStop() {
                        //停止录音
                        InVoiceStatus();
                    }

                    @Override
                    public void onRecording(int i, double v) {
                        Log.e("MainActivity", "采样:" + i + "Hz   音量:" + v + "分贝");
                        //播放动画
                        if (mRecorder != null) {
                            int finalDb = (int) v;
                            activity.runOnUiThread(() -> voiceWave.setDecibel(finalDb));
                        }
                    }
                });
                if (!mRecorder.isRecording()) {
                    try {
                        mRecorder.startRecording(StorageUtil.getCacheDirectory(activity).getAbsolutePath(), FileUtil.getVoicName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else { //停止录音
                mMp3Path = mRecorder.mp3File.getAbsolutePath();
                int duration = VideoUtil.getLocalVideoDuration(activity,mMp3Path);
                if (duration==0){
                    ToastUtil.showToast("说话时间太短!");
                    stopRecording();
                    deleteVoice();
                }else {
                    if (time != 0) {
                        stopRecording();
                    }
                }
            }
        } catch (Exception e) {
            Log.e("Recording-----.", e.toString());
        }
    }

    /**
     * @description 停止录音
     */
    public void stopRecording() {
        isRecording = 0;
        mProgress.reset();
        stopRecord();
        mProgress.cleanCountDown();
        mLlStartRecord.setVisibility(View.GONE);
        mRlplay.setVisibility(View.VISIBLE);
        totaltime = time;
        mTvPlayTime.setText(totaltime + "S");
    }

    //停止录音
    private void stopRecord() {
        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.stopRecording();
        }
    }


    /**
     * @description 开始播放
     */
    public void startPlay() {
        if (isPlaying == 0) {
            mStyle.setBackgroundResource(R.drawable.bg_voice_stop);
            mTvPlay.setText("正在播放");
            isPlaying = 1;
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(mMp3Path);
                mPlayer.prepare();
                mPlayer.start();
                //动画
                voiceWave.setMediaPlayer(mPlayer);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);
        } else {
            //结束试听
            EndAudition();
        }
    }

    public void EndAudition() {
        cPlay.reset();
        endAudition();
        close();
        InVoiceStatus();
    }

    /**
     * @description 初始化分贝
     */

    public void InVoiceStatus() {
        voiceWave.stopRecord();
    }

    public void deleteFile() {
        if (mMp3Path != null) {
            //每一次调用录音，可以录音多次，至多满意为至，最后只将最后一次的录音文件保存，其他的删除
            File oldFile = new File(mMp3Path);
            oldFile.delete();
            mMp3Path = null;
            mRecorder = null;
        }
    }

    public void setProgress(int type, CircleProgressView view, TextView textView) {
        view.setProgress(100, totaltime);
        view.setAddCountDownListener(new CircleProgressView.OnCountDownFinishListener() {
            @Override
            public void countDown(int second) {
                if (second <= 0) {
                    textView.setText("0S");
                } else {
                    textView.setText(second + "S");
                }
                if (type == 1) {
                    time = totaltime - second;
                }
            }

            @Override
            public void countDownFinished() {
                time = totaltime;
                if (type == 1) {
                    stopRecording();
                } else {
                    EndAudition();
                }
            }
        });
    }

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    setProgress(1, mProgress, mTvTime);
                    break;
                case 2:
                    setProgress(2, cPlay, mTvPlayTime);
                    break;
            }
        }
    };


    public void endAudition() {
        isPlaying = 0;
        cPlay.cleanCountDown();
        mTvPlayTime.setText(totaltime + "S");
        mStyle.setBackgroundResource(R.drawable.drifting_play);
        mTvPlay.setText("点击播放");
    }

}
