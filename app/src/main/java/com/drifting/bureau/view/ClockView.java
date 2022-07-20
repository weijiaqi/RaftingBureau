package com.drifting.bureau.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 倒计时
 */
@SuppressLint("AppCompatCustomView")
public class ClockView extends TextView {
    public static int HOUR = 0;
    public static int MIN = 1;
    public static int SEC = 2;
    public static int TEXT = 3;

    private boolean mTickerStopped;
    private Handler mHandler;
    private Runnable mTicker;
    private int type;//显示提示类型
    private long endTime;//结束时间
    private String content;//显示文本
    private ClockListener mClockListener;

    public void setType(int type) {
        this.type = type;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setClockListener(ClockListener clockListener) {
        this.mClockListener = clockListener;
    }

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        mTickerStopped = false;
        super.onAttachedToWindow();
        mHandler = new Handler();
        getVisibility();
        //requests a tick on the next hard-second boundary
        mTicker = new Runnable() {
            public void run() {
                if (mTickerStopped)
                    return;
                long currentTime = System.currentTimeMillis();
                if (currentTime / 1000 == endTime / 1000 - 5 * 60 && null != mClockListener) {
                    mClockListener.remainFiveMinutes();
                }
                long distanceTime = endTime - currentTime;
                distanceTime /= 1000;
                if (distanceTime == 0) {
                    setTextContent();
                    onDetachedFromWindow();
                    if (null != mClockListener)
                        mClockListener.timeEnd();
                } else if (distanceTime < 0) {
                    setTextContent();
                } else {
                    setText(dealTime(distanceTime));
                    if (null != mClockListener)
                        mClockListener.timeRemaining(distanceTime);
                }
                invalidate();
                long now = SystemClock.uptimeMillis();
                //够不够一秒,保证一秒更新一次
                long next = now + (1000 - now % 1000);
                mHandler.postAtTime(mTicker, next);
            }
        };
        mTicker.run();
    }

    /**
     * 设置显示文本
     */
    private void setTextContent() {
        if (type == HOUR) {
            setText("00:00:00");
        } else if (type == MIN) {
            setText("00:00");
        } else if (type == SEC) {
            setText("00");
        } else {
            setText(content);
        }
    }

    /**
     * 计算倒计时
     */
    private String dealTime(long time) {
        StringBuffer returnString = new StringBuffer();
        long day = time / (24 * 60 * 60);
        long hours = (time % (24 * 60 * 60)) / (60 * 60);
        long minutes = ((time % (24 * 60 * 60)) % (60 * 60)) / 60;
        long second = ((time % (24 * 60 * 60)) % (60 * 60)) % 60;
        String dayStr = String.valueOf(day);
        String hoursStr = timeStrFormat(String.valueOf(hours));
        String minutesStr = timeStrFormat(String.valueOf(minutes));
        String secondStr = timeStrFormat(String.valueOf(second));

        if (type == HOUR) {
            returnString.append(hoursStr).append(":").append(minutesStr).append(":").append(secondStr);
        } else if (type == MIN) {
            returnString.append(minutesStr).append(":").append(secondStr);
        } else {
            returnString.append(secondStr);
        }
        return returnString.toString();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTickerStopped = true;
    }

    /**
     * 回收后启动
     */
    public void changeTicker() {
        mTickerStopped = !mTickerStopped;
        if (!mTickerStopped) {
            mHandler.post(mTicker);
        } else {
            mHandler.removeCallbacks(mTicker);
        }
    }


    /**
     * 时间格式
     */
    private static String timeStrFormat(String timeStr) {
        switch (timeStr.length()) {
            case 1:
                timeStr = "0" + timeStr;
                break;
        }
        return timeStr;
    }

    public interface ClockListener {
        void timeEnd();

        void timeRemaining(long time);

        void remainFiveMinutes();
    }
}
