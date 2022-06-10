package com.drifting.bureau.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drifting.bureau.R;



public class CountDownShowView extends LinearLayout {
    private Context mContext;

    private TextView mTvByTime;
    private long mCurTime;
    private long mCurrentTime;

    public CountDownShowView(Context context) {
        super(context);
    }

    public CountDownShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public CountDownShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTvByTime = findViewById(R.id.tv_by_time);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        setTime();
    }

    public void setStopTime(long curTime) {
        this.mCurTime = curTime;
        setTime();
    }

    public long getCurrentTime() {
        return mCurrentTime;
    }

    private void setTime() {
        String[] countTimeByLong2 = getCountTimeByLong(mCurTime);
        mTvByTime.setText(countTimeByLong2[0]+":"+countTimeByLong2[1]+":"+countTimeByLong2[2]);
    }

    private String[] getCountTimeByLong(long finishTime) {
        mCurrentTime = finishTime;
        String[] stringArray = new String[3];
        int totalTime = (int) (finishTime / 1000);
        int hour = 0, minute = 0, second = 0;

        if (totalTime >= 3600) {
            hour = totalTime / 3600;
            totalTime = totalTime - hour * 3600;
        }
        if (totalTime >= 60) {
            minute = totalTime / 60;
            totalTime = totalTime - minute * 60;
        }
        if (totalTime >= 0) {
            second = totalTime;
        }

        if (hour < 10) {
            stringArray[0] = "0" + hour;
        } else {
            stringArray[0] = "" + hour;
        }
        if (minute < 10) {
            stringArray[1] = "0" + minute;
        } else {
            stringArray[1] = "" + minute;
        }
        if (second < 10) {
            stringArray[2] = "0" + second;
        } else {
            stringArray[2] = "" + second;
        }
        return stringArray;
    }
}

