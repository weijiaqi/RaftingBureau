package com.drifting.bureau.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.drifting.bureau.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author baker
 * @date 2020年4月21日16:56:09
 * @Description: 音浪
 */
public class VoiceWave extends View {
    private Paint paint;
    private boolean flag1, flag2, flag3;
    private List<Bean> dataList = new ArrayList<>();
    private boolean isHeadSetOn;
    private float heightRate = 1.0f;//默认1
    Object mLock;
    private Visualizer visualizer;

    private int currentVolume;
    private int currentFrequency;

    private float mi;

    public VoiceWave(Context context) {
        this(context, null);
    }

    public VoiceWave(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceWave(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);//设置要重绘（不要不重绘）
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(7);
        paint.setColor(getContext().getResources().getColor(R.color.color_7d));
        paint.setStyle(Paint.Style.FILL);
        heightRate = 0.8f;
        mLock = new Object();
    }

    //设置分贝
    public void setDecibel(int decibel) {
        initData(decibel);
        postInvalidate();
    }

    private void initData(int decibel) {
        int quietDecibel;
        AudioManager localAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        isHeadSetOn = localAudioManager.isWiredHeadsetOn();
        if (isHeadSetOn) {//插耳机,比较敏感
            quietDecibel = 70;
        } else {//外放
            quietDecibel = 60;
        }
        int base = (int) ((decibel - quietDecibel) / 30f * 100);//70是最低分贝 40是最高90分贝-50分贝,90是基数区间0-90的最大值,通过枚举总结出来的
        boolean isQuiet;
        if (base < 0) {
            base = 0;
        }
        if (decibel < quietDecibel) {
            isQuiet = true;
        } else {
            isQuiet = false;
        }
        Log.i("tan6458", "base:" + base);
        if (dataList != null) {
            dataList.clear();
        }
        if (decibel == 0) {
            for (int i = 1; i <= 50; i++) {
                dataList.add(new Bean(i, 20, 20));
            }
        } else {
            dataList.add(new Bean(1, 20, 30));
            dataList.add(new Bean(2, 20, 30));
            dataList.add(new Bean(3, isQuiet ? 30 : base + 35, 20));
            dataList.add(new Bean(4, 20, 30));
            dataList.add(new Bean(5, 30, isQuiet ? 20 : base + 20));
            dataList.add(new Bean(6, isQuiet ? 20 : base + 20, 30));
            dataList.add(new Bean(7, 30, isQuiet ? 20 : base + 20));
            dataList.add(new Bean(8, isQuiet ? 20 : base + 50, 30));
            dataList.add(new Bean(9, 20, isQuiet ? 30 : base + 30));
            dataList.add(new Bean(10, isQuiet ? 20 : base + 20, 30));
            dataList.add(new Bean(11, 30, isQuiet ? 20 : base + 50));
            dataList.add(new Bean(12, isQuiet ? 20 : base + 50, 30));
            //-----------------------------------------------------
            dataList.add(new Bean(13, 30, isQuiet ? 20 : base + 70));
            //-----------------------------------------------------
            dataList.add(new Bean(14, isQuiet ? 20 : base + 50, 30));
            dataList.add(new Bean(15, 30, isQuiet ? 20 : base + 50));
            dataList.add(new Bean(16, isQuiet ? 20 : base + 20, 30));
            dataList.add(new Bean(17, 20, isQuiet ? 30 : base + 30));
            dataList.add(new Bean(18, isQuiet ? 20 : base + 50, 30));
            dataList.add(new Bean(19, 30, isQuiet ? 20 : base + 20));
            dataList.add(new Bean(20, isQuiet ? 20 : base + 20, 30));
            dataList.add(new Bean(21, 30, isQuiet ? 20 : base + 20));
            dataList.add(new Bean(22, 20, 30));
            dataList.add(new Bean(23, isQuiet ? 30 : base + 35, 20));
            dataList.add(new Bean(24, 20, 30));//20
            dataList.add(new Bean(25, 20, 30));

            dataList.add(new Bean(26, 20, 30));
            dataList.add(new Bean(27, 20, 30));
            dataList.add(new Bean(28, isQuiet ? 30 : base + 35, 20));
            dataList.add(new Bean(29, 20, 30));
            dataList.add(new Bean(30, 30, isQuiet ? 20 : base + 20));
            dataList.add(new Bean(31, isQuiet ? 20 : base + 20, 30));
            dataList.add(new Bean(32, 30, isQuiet ? 20 : base + 20));
            dataList.add(new Bean(33, isQuiet ? 20 : base + 50, 30));
            dataList.add(new Bean(34, 20, isQuiet ? 30 : base + 30));
            dataList.add(new Bean(35, isQuiet ? 20 : base + 20, 30));
            dataList.add(new Bean(36, 30, isQuiet ? 20 : base + 50));
            dataList.add(new Bean(37, isQuiet ? 20 : base + 50, 30));
            //-----------------------------------------------------
            dataList.add(new Bean(38, 30, isQuiet ? 20 : base + 70));
            //-----------------------------------------------------
            dataList.add(new Bean(39, isQuiet ? 20 : base + 50, 30));
            dataList.add(new Bean(40, 30, isQuiet ? 20 : base + 50));
            dataList.add(new Bean(41, isQuiet ? 20 : base + 20, 30));
            dataList.add(new Bean(42, 20, isQuiet ? 30 : base + 30));
            dataList.add(new Bean(43, isQuiet ? 20 : base + 50, 30));
            dataList.add(new Bean(44, 30, isQuiet ? 20 : base + 20));
            dataList.add(new Bean(45, isQuiet ? 20 : base + 20, 30));
            dataList.add(new Bean(46, 30, isQuiet ? 20 : base + 20));
            dataList.add(new Bean(47, 20, 30));
            dataList.add(new Bean(48, isQuiet ? 30 : base + 35, 20));
            dataList.add(new Bean(49, 20, 30));//20
            dataList.add(new Bean(50, 20, 30));
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        Resources resources = getContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float rate = displayMetrics.scaledDensity;
        int baseX = getMeasuredWidth() / 2 - ((7 + 3) * 50 / 2);
        canvas.translate(0, getMeasuredHeight() / 2);
        for (int i = 0; i < dataList.size(); i++) {
            final Bean bean = dataList.get(i);
            //间隔间距
            int grep = 10;
            if (flag1) {//2 6
                int m1 = (int) (bean.getShortV() * heightRate + ((bean.getLongV() * heightRate - bean.getShortV() * heightRate) / 3));
//                Logger.i(TAG,  "画中下:"+m1 / 2 / 2f);
                canvas.drawLine(baseX + grep * i, m1 / 2 / 2f * rate, baseX + grep * i, -m1 / 2 / 2f * rate, paint);
                if (i == dataList.size() - 1) {
                    flag1 = false;
                }
            } else {//
                if (flag2) {//3 5
                    if (i == dataList.size() - 1) {
                        flag2 = false;
                        if (!flag3) {
                            flag1 = true;
                        }
                    }
                    int m2 = (int) (bean.getLongV() * heightRate - ((bean.getLongV() * heightRate - bean.getShortV() * heightRate) / 3));
//                   Logger.i(TAG, "画中上:"+m2 / 2 / 2f);
                    canvas.drawLine(baseX + grep * i, m2 / 2 / 2f * rate, baseX + grep * i, -m2 / 2 / 2f * rate, paint);
                } else {
                    if (flag3) {//4
                        if (i == dataList.size() - 1) {
                            flag3 = false;
                            flag2 = true;
                        }
//                       Logger.i(TAG,  "画长:"+bean.getLongV() / 2 / 2f);
                        canvas.drawLine(baseX + grep * i, bean.getLongV() * heightRate / 2 / 2f * rate, baseX + grep * i, -bean.getLongV() * heightRate / 2 / 2f * rate, paint);
                    } else {//1 7
//                         Logger.i(TAG, "画短:"+bean.getShortV() / 2 / 2f);
                        canvas.drawLine(baseX + grep * i, bean.getShortV() * heightRate / 2 / 2f * rate, baseX + grep * i, -bean.getShortV() * heightRate / 2 / 2f * rate, paint);
                        if (i == dataList.size() - 1) {
                            flag3 = true;
                            flag2 = true;
                            flag1 = true;
                        }
                    }
                }
            }
        }
    }

    private static class Bean implements Serializable {
        int longV, index;
        int shortV;

        public int getLongV() {
            return longV;
        }

        public void setLongV(int longV) {
            this.longV = longV;
        }

        public int getShortV() {
            return shortV;
        }

        public void setShortV(int shortV) {
            this.shortV = shortV;
        }

        public Bean(int index, int longV, int shortV) {
            this.index = index;
            this.longV = longV;
            this.shortV = shortV;
        }
    }


    /**
     * 只需传入 mediaPlayer 即可
     *
     * @param mediaPlayer
     */
    public void setMediaPlayer(final MediaPlayer mediaPlayer) {
        visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer2, byte[] waveform, int samplingRate) {
                if (visualizer!=null){
                    double sum = 0;
                    for (int i = 0; i < waveform.length / 2; i++) {
                        double y = (waveform[i * 2] | waveform[i * 2 + 1] << 8) / 2;
                        sum += y * y;
                    }
                    double rms = Math.sqrt(sum / waveform.length / 2);
                    double dbAmp = 20.0 * Math.log10(rms);
                    setDecibel((int) dbAmp);
                }
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {

            }
        }, Visualizer.getMaxCaptureRate() / 2, true, false);

        visualizer.setEnabled(true);
    }


    public synchronized void stopRecord() {
        if (visualizer != null) {
            visualizer.setEnabled(false);
            visualizer.release();
            visualizer=null;
        }
        dataList.clear();
        setDecibel(0);
    }
}
