package com.drifting.bureau.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.drifting.bureau.R;
import com.drifting.bureau.app.application.RBureauApplication;
import com.drifting.bureau.view.VoiceWave;
import com.hw.videoprocessor.VideoProcessor;

import java.io.IOException;
import java.util.HashMap;

import timber.log.Timber;

public class VideoUtil {

    //语音操作对象
    private static MediaPlayer mPlayer = null;

    private static CountDownTimer timer;

    private static int isPlaying = 0;

    /**
     * @description 语音播放
     */
    public static void startVoicePlay(String path, Object totaltime, ImageView mIvPlay, VoiceWave mVideoView, TextView mTvTime) {
        if (isPlaying == 0) {
            mIvPlay.setImageResource(R.drawable.voice_pause);
            isPlaying = 1;
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(path);
                mPlayer.prepare();
                mPlayer.start();
                //动画
               mVideoView.setMediaPlayer(mPlayer);
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
            startCountDown(mVideoView,mIvPlay, mTvTime, totaltime);
        } else {
            stop(mVideoView,mIvPlay, mTvTime, totaltime);
        }
    }

    /**
     * 开始倒计时
     */
    private static void startCountDown( VoiceWave mVideoView, ImageView mIvPlay, TextView mTvTime, Object animTime) {
        if (timer == null) {
            timer = new CountDownTimer(((int) animTime) * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //剩余秒数
                    mTvTime.setText((Math.round((double) millisUntilFinished / 1000) - 1) + "S");
                }

                @Override
                public void onFinish() {
                    //倒计时结束回调
                    stop(mVideoView,mIvPlay, mTvTime, animTime);
                }
            }.start();
        }
    }


    /**
     * @description 语音播放停止
     */
    public static void stop(VoiceWave mVideoView,ImageView mIvPlay, TextView mTvTime, Object animTime) {
        mVideoView.stop();
        mVideoView.setDecibel(0);
        cleanCountDown();
        mTvTime.setText(((int) animTime) + "S");
        mIvPlay.setImageResource(R.drawable.voice_play);
        close();
    }



    /**
     * @description 播放器清除
     */
    public static void close() {
        if (mPlayer != null) {
            isPlaying = 0;
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    /**
     * 结束倒计时
     */
    public static void cleanCountDown() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    /**
     * @description 获取视频path
     */
    public static String getLocalVideoPath(Activity activity, Uri uri) {
        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            return path;
        }
        return null;
    }

    /**
     * get Local video and audio duration
     *
     * @return
     */
    public static int getLocalVideoDuration(String videoPath) {
        //时长(毫秒)
        int duration;
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(videoPath);
            duration = Integer.parseInt(mmr.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_DURATION)) / 1000;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return duration;
    }


    private static String runLog = "";

    /**
     * 视频压缩
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void compressVideo(Context context, String videoPath) {
        String filepath = FileUtil.saveVideoPath(context);
        new Thread(() -> {
            boolean success = true;
            try {
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(videoPath);
                int originWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
                int originHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
                int bitrate = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
                double fileSize = FileUtil.getFileOrFilesSize(videoPath, FileUtil.SIZETYPE_MB);
                Timber.e("1----------" + fileSize);
                VideoProcessor.processor(RBureauApplication.getContext())
                        .input(videoPath)
                        .output(filepath)
                        .outWidth(originWidth)
                        .outHeight(originHeight)
                        .bitrate(bitrate / 2)
                        .process();
            } catch (Exception e) {
                success = false;
                e.printStackTrace();
                Timber.e("压缩失败");
            }
            if (success) {
                Timber.e("压缩成功---" + filepath);
                runLog=filepath;
                double fileSize = FileUtil.getFileOrFilesSize(filepath, FileUtil.SIZETYPE_MB);
                Timber.e("1----------" + fileSize);

            }
        }).start();
    }

    public static String getRunLog() {
        return runLog;
    }
}
