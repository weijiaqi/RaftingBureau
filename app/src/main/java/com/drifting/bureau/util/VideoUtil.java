package com.drifting.bureau.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
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


    private static int total_duration;
    private static int current_pos;

    private static int time_remaining;

    private static Runnable runnable;
    private static int isPlaying = 0;

    private static Handler handler1 = new Handler();

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
                mPlayer.setOnPreparedListener(mediaPlayer -> {
                    total_duration = mPlayer.getDuration() / 1000;
                    current_pos = mPlayer.getCurrentPosition() / 1000;
                    mTvTime.setText(total_duration + "S");
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                current_pos = mPlayer.getCurrentPosition() / 1000;
                                time_remaining = total_duration - current_pos;
                                mTvTime.setText(time_remaining + "S");
                                if (time_remaining <= 0) {
                                    stop(mVideoView, mIvPlay, mTvTime, totaltime);
                                } else {
                                    handler1.postDelayed(this, 1000);
                                }
                            } catch (IllegalStateException ed) {
                                ed.printStackTrace();
                            }
                        }
                    };
                    handler1.postDelayed(runnable, 1000);
                });
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
        } else {
            stop(mVideoView, mIvPlay, mTvTime, totaltime);
        }
    }


    /**
     * @description 语音播放停止
     */
    public static void stop(VoiceWave mVideoView, ImageView mIvPlay, TextView mTvTime, Object animTime) {
        close();
        mTvTime.setText(((int) animTime) + "S");
        mIvPlay.setImageResource(R.drawable.voice_play);
        mVideoView.stopRecord();
    }

    /**
     * @description 播放器清除
     */
    public static void close() {
        if (handler1 != null) {
            handler1.removeCallbacks(runnable);
        }
        if (mPlayer != null) {
            isPlaying = 0;
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
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
                runLog = filepath;
                double fileSize = FileUtil.getFileOrFilesSize(filepath, FileUtil.SIZETYPE_MB);
                Timber.e("1----------" + fileSize);

            }
        }).start();
    }

    public static String getRunLog() {
        return runLog;
    }
}
