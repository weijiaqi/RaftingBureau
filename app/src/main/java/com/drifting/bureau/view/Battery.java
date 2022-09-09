package com.drifting.bureau.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.drifting.bureau.R;

public class Battery extends View {
    public float currentX = 80;
    public float currentY = 80;
    private float secondY = 80;
    private Paint mPaint = new Paint();
    private Context mContext;
    private Handler mHandler;
    private Bitmap mBmp;
    private int speedTime = 20;

    private float with = 200;
    private float height = 50;
    private float percentage = 0.5f;

    public Battery(Context context) {
        super(context);
        this.mContext = context;

    }

    public Battery(Context context, AttributeSet set) {
        super(context, set);
        this.mContext = context;
    //    init();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        with = this.getWidth();
        height = this.getHeight();

        mPaint.setColor(Color.BLUE);
        Resources res = mContext.getResources();
        BitmapDrawable bmpDraw = (BitmapDrawable) res
                .getDrawable(R.drawable.about_me_progress_finish);
        mBmp = bmpDraw.getBitmap();

        canvas.clipRect(0, height, with*percentage, 0);

        canvas.drawBitmap(mBmp, 0, currentY, mPaint);

        canvas.drawBitmap(mBmp, 0, secondY, mPaint);
    }

    private void init() {
        percentage = 0;

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        currentX ++;
                        currentY ++;
                        if (mBmp != null && currentY > mBmp.getHeight()){
                            currentY = -mBmp.getHeight();
                        }
                        if (mBmp != null){
                            secondY = currentY+mBmp.getHeight();
                            if (secondY >= mBmp.getHeight()){
                                secondY = currentY-mBmp.getHeight();
                            }
                        }

                        percentage = percentage + 0.003f;
                        if (percentage > 1){
                            percentage = 0;
                        }
                        // 每次计算后都发送消息进入下一次循环，并刷新界面
                        mHandler.sendEmptyMessageDelayed(1, speedTime);
                        postInvalidate();
                        break;
                }
                super.handleMessage(msg);
                postInvalidate();
            }
        };

        // 首次循环刷新界面
        mHandler.sendEmptyMessageDelayed(1, speedTime);
    }
}