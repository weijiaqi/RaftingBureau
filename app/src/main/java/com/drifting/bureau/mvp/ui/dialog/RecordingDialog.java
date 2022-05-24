package com.drifting.bureau.mvp.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.audiofx.Visualizer;
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

import com.drifting.bureau.R;
import com.drifting.bureau.util.FileUtil;
import com.drifting.bureau.view.CircleProgressView;
import com.drifting.bureau.view.VoiceWave;
import com.jess.arms.base.BottomDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 语音录制
 */
public class RecordingDialog extends BottomDialog implements View.OnClickListener {

    private ImageView mIvNext, mIvHorizontalLine;

    private TextView mTvTime, mTvPlayTime, mTvPlay, mTvdelete, mTvEnd;
    private LinearLayout mLlPrepare, mLlStartRecord, mLlplay;
    private CircleProgressView mProgress, cPlay;
    private RelativeLayout mRlplay;
    private View mStyle;
    private VoiceWave voiceWave;
    private int time;
    private int isRecording = 0;
    private int isPlaying = 0;
    //语音保存路径
    private String FilePath = null;
    private Activity activity;
    private MediaRecorder mRecorder = null;
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
        mIvHorizontalLine = findViewById(R.id.iv_horizontal_line);
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
                if (mHandler != null) {
                    mHandler.removeCallbacksAndMessages(null);
                }
                dismiss();
                break;
            case R.id.ll_start:
            case R.id.ll_prepare: //开始录音
                startRecording();
                break;
            case R.id.ll_play: //开始播放
                startPlay();
                break;
            case R.id.tv_delete: //删除
                InVoiceStatus();
                cPlay.reset();
                endAudition();
                deleteFile();
                close();
                mRlplay.setVisibility(View.GONE);
                mLlPrepare.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_end:  //完成
                if (FilePath != null) {
                    dismiss();
                    InVoiceStatus();
                    close();
                    List<Object> list = new ArrayList<>();
                    list.add(FilePath);
                    list.add(totaltime);
                    if (onMoreClickCallback != null) {
                        onMoreClickCallback.onMoreClick(1, list);
                    }
                }
                break;
        }
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
                deleteFile();
                FilePath = FileUtil.saveVoicePath(activity);
                mLlPrepare.setVisibility(View.GONE);
                mLlStartRecord.setVisibility(View.VISIBLE);
                //将按钮换成停止录音
                isRecording = 1;
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setOutputFile(FilePath);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                try {
                    mRecorder.prepare();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mRecorder.start();
                totaltime = 15;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
                //播放动画
                updateMicStatus();
            } else { //停止录音
                if (time != 0) {
                    stopRecording();
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
        mProgress.cleanCountDown();
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        mLlStartRecord.setVisibility(View.GONE);
        mRlplay.setVisibility(View.VISIBLE);
        totaltime = time;
        mTvPlayTime.setText(totaltime + "S");
        voiceWave.setDecibel(0);
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
                mPlayer.setDataSource(FilePath);
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
            InVoiceStatus();
            cPlay.reset();
            endAudition();
            close();
        }
    }

    /**
     * @description 初始化分贝
     */

    public void InVoiceStatus() {
        voiceWave.stop();
        voiceWave.setDecibel(0);
    }

    public void deleteFile() {
        if (FilePath != null) {
            //每一次调用录音，可以录音多次，至多满意为至，最后只将最后一次的录音文件保存，其他的删除
            File oldFile = new File(FilePath);
            oldFile.delete();
        }
    }

    public void setProgress(int type, CircleProgressView view, TextView textView) {
        view.setProgress(100, totaltime);
        view.setAddCountDownListener(new CircleProgressView.OnCountDownFinishListener() {
            @Override
            public void countDown(int second) {
                textView.setText(second + "S");
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
                    endAudition();
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


    private int BASE = 1;
    private int SPACE = 100;// 间隔取样时间


    private void updateMicStatus() {
        if (mRecorder != null) {
            int ratio = mRecorder.getMaxAmplitude() / BASE;
            int db = 0;// 分贝
            if (ratio > 1)
                db = (int) (20 * Math.log10(ratio));

            Log.e("1---------", ratio + "");
            int finalDb = db;

            activity .runOnUiThread(() -> voiceWave.setDecibel(finalDb));
            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
        }
    }

    private final Handler mHandler = new Handler();
    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };


}
