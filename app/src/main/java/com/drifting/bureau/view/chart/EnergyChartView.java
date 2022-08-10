package com.drifting.bureau.view.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.drifting.bureau.R;

import java.util.ArrayList;
import java.util.List;

public class EnergyChartView extends View {

    private Paint pointPaint;//曲线上锚点画笔
    private Path linePath;//曲线路径
    private Paint textPointPaint;//曲线上锚点文本画笔
    private int mWidth, mHeight;
    private List<Data> dataList = new ArrayList<>();
    private Point[] linePoints;
    private Bitmap mBitmap, mBitmap2;
    private int index = -1;
    private float heightPercent = 1f;
    private int pointColor = Color.parseColor("#FF4081");//锚点颜色
    private int pointTextColor = Color.parseColor("#FFFFFF");//锚点文本颜色
    private RectF mSrcRect = new RectF();//图片绘制区域
    private RectF mTopRect = new RectF();//图片绘制区域
    private float pointTextSizeSP = 12f;//锚点文本大小
    private float startX, startY;
    private boolean isInitialized = false;
    private Canvas canvasTop;
    private Point point;
    private int mBitmapWidth, mBitmapHeight;
    private String nebula;

    public EnergyChartView(Context context) {
        this(context, null);
    }

    public EnergyChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EnergyChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView();
    }

    private void setupView() {
        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);//抗锯齿
        pointPaint.setColor(pointColor);
        pointPaint.setStyle(Paint.Style.FILL);

        textPointPaint = new Paint();
        textPointPaint.setAntiAlias(true);
        textPointPaint.setStyle(Paint.Style.FILL);
        textPointPaint.setTextAlign(Paint.Align.CENTER);
        textPointPaint.setColor(pointTextColor);//文本颜色
        textPointPaint.setTextSize(sp2px(pointTextSizeSP));//字体大小
        Typeface font = Typeface.create(Typeface.DEFAULT , Typeface.BOLD);
        textPointPaint.setTypeface( font );
        linePath = new Path();
        resetParam();
    }

    private void resetParam() {
        linePath.reset();
        linePoints = new Point[dataList.size()];
        isInitialized = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(4600, 5600);//设置自己的宽度和高度
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInitialized) {
            setupLine();
        }
        drawLinePoints(canvas);//绘制曲线上的点
    }


    /**
     * 绘制曲线上的锚点
     *
     * @param canvas
     */
    private void drawLinePoints(Canvas canvas) {
        if (linePoints == null) return;
        int pointCount = linePoints.length;
        for (int i = 0; i < pointCount; i++) {
            point = linePoints[i];
            if (point == null) break;
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.star);
            mBitmapWidth = mBitmap.getWidth();
            mBitmapHeight = mBitmap.getHeight();
            canvas.drawBitmap(mBitmap, point.x, point.y, pointPaint);
            // canvas.drawRect(point.x, point.y, point.x + pointWidth, point.y+pointWidth*2, pointPaint);
            if (index==-1&& TextUtils.equals(nebula, dataList.get(i).getNebula_code())) {
                showMessage(i, canvas);
            }

            //绘制点的文本
            canvas.drawText(dataList.get(i).getNebula(), point.x + mBitmapWidth / 2, point.y+mBitmapHeight/2, textPointPaint);
            //绘制点的文本
            if (i == index) {
                showMessage(i, canvas);
            }
        }
    }


    public void showMessage(int i, Canvas canvas) {
        if (i + 1 == 100 || i + 1 == 94 || i + 1 == 86 || i + 1 == 39 || i + 1 == 27 || i + 1 == 97 || i + 1 == 82 || i + 1 == 111 || i + 1 == 105 || i + 1 == 119 || i + 1 == 120 || i + 1 == 106 || i + 1 == 43 || i + 1 == 44 || i + 1 == 98 || i + 1 == 42 || i + 1 == 30 || i + 1 == 41 || i + 1 == 40 || i + 1 == 66 || i + 1 == 65 || i + 1 == 89 || i + 1 == 87 || i + 1 == 110 || i + 1 == 60 || i + 1 == 36) {
            showLeftMessage(canvas, dataList.get(i).getIntro());
        } else {
            showRightMessage(canvas, dataList.get(i).getIntro());
        }
    }

    /**
     * @description 飘来新消息
     */

    public void showLeftMessage(Canvas canvas, String intro) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.layout_left_planet_introduction, null, false);
        v.measure(v.getWidth(), v.getHeight());
        TextView mTvMessage = v.findViewById(R.id.tv_message);
        mTvMessage.setText(intro);
        mBitmap2 = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        int mBitmapWidth2 = mBitmap2.getWidth();
        int mBitmapHeight2 = mBitmap2.getHeight();
        canvasTop = new Canvas(mBitmap2);
        v.layout(0, 0, mBitmapWidth2, mBitmapHeight2);
        v.draw(canvasTop);
        canvasTop.setBitmap(null);

        canvas.drawBitmap(mBitmap2, point.x - mBitmapWidth2-40, point.y - mBitmapHeight, null);

        mTopRect.left = point.x + 30;
        mTopRect.top = mHeight + point.y - mBitmapHeight2;
        mTopRect.right = point.x + 30 + mBitmapWidth2;
        mTopRect.bottom = mHeight + point.y + mBitmapHeight2;

        //canvas.drawRect(mTopRect, customPaint(Color.BLUE));
    }


    /**
     * @description 飘来新消息
     */

    public void showRightMessage(Canvas canvas, String intro) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.layout_planet_introduction, null, false);
        v.measure(v.getWidth(), v.getHeight());
        TextView mTvMessage = v.findViewById(R.id.tv_message);
        mTvMessage.setText(intro);
        mBitmap2 = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        int mBitmapWidth2 = mBitmap2.getWidth();
        int mBitmapHeight2 = mBitmap2.getHeight();
        canvasTop = new Canvas(mBitmap2);
        v.layout(0, 0, mBitmapWidth2, mBitmapHeight2);
        v.draw(canvasTop);
        canvasTop.setBitmap(null);

        canvas.drawBitmap(mBitmap2, point.x + mBitmapWidth+40, point.y - mBitmapHeight, null);

        mTopRect.left = point.x + 30;
        mTopRect.top = mHeight + point.y - mBitmapHeight2;
        mTopRect.right = point.x + 30 + mBitmapWidth2;
        mTopRect.bottom = mHeight + point.y + mBitmapHeight2;

        //canvas.drawRect(mTopRect, customPaint(Color.BLUE));
    }


    /**
     * 获取绘制区域高度
     *
     * @return
     */
    private float getViewDrawHeight() {
        return getMeasuredHeight() * heightPercent;
    }

    /**
     * 初始化曲线数据
     */
    private void setupLine() {
        if (dataList.isEmpty()) return;
        Point pre = new Point();
        pre.set(dataList.get(0).getX(), dataList.get(0).getY());
        linePoints[0] = pre;
        linePath.moveTo(pre.x, pre.y);
        if (dataList.size() == 1) {
            isInitialized = true;
            return;
        }
        for (int i = 1; i < dataList.size(); i++) {
            Data data = dataList.get(i);
            Point next = new Point();
            next.set(data.getX(), data.getY());
            linePath.lineTo(next.x, next.y);
            linePoints[i] = next;
        }
        isInitialized = true;
    }

    private int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private void refreshLayout() {
        resetParam();
        requestLayout();
        postInvalidate();
    }

    /*-------------可操作方法---------------*/

    /**
     * 设置数据
     *
     * @param dataList
     */
    public void setData(List<Data> dataList, String nebula) {
        index = -1;
        this.nebula = nebula;
        if (dataList == null) {
            throw new RuntimeException("dataList cannot is null!");
        }
        if (dataList.isEmpty()) return;
        this.dataList.clear();
        this.dataList.addAll(dataList);

        refreshLayout();
    }


    public static class Data {
        int x;
        int y;
        String nebula;
        String nebula_code;
        String intro;

        public String getNebula_code() {
            return nebula_code;
        }

        public void setNebula_code(String nebula_code) {
            this.nebula_code = nebula_code;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public String getNebula() {
            return nebula;
        }

        public void setNebula(String nebula) {
            this.nebula = nebula;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                Log.e("HJQresult", "点击了-----" + startX + "----" + startY);
                for (int i = 0; i < linePoints.length; i++) {
                    Point point = linePoints[i];
                    mSrcRect.left = point.x;
                    mSrcRect.top = point.y;
                    mSrcRect.right = point.x + mBitmapWidth;
                    mSrcRect.bottom = point.y + mBitmapHeight;
                    if (mSrcRect.contains(startX, startY)) {
                        index = i;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                invalidate();//更新视图
                break;
        }
        return true;
    }
}
