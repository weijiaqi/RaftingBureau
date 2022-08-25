package com.drifting.bureau.view.chart;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.drifting.bureau.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jeanboy on 2017/6/12.
 */

public class LineChartView extends View {

    private Paint linePaint, linePaint2;//曲线画笔
    private Paint pointPaint, pointPaint2, pointPaint3;
    private Paint tablePaint;//表格画笔
    private Paint textRulerPaint;//标尺文本画笔
    private Paint textPointPaint;//曲线上锚点文本画笔

    private Path linePath;//曲线路径
    private Path tablePath;//表格路径

    private int mWidth, mHeight;

    private List<Data> dataList = new ArrayList<>();

    private Canvas canvasTop;

    private Point[] linePoints;
    private int stepStart;
    private int stepEnd;
    private int stepSpace;
    private int stepSpaceDefault = 70;
    private int stepSpaceDP = stepSpaceDefault;//item宽度默认dp
    private int topSpace, bottomSpace;
    private int tablePadding;
    private int tablePaddingDP = 60;//view四周padding默认dp
    private Bitmap mBitmap, mBitmap2;      //定义一个Bitmap
    private int maxValue, minValue;
    private int rulerValueDefault = 10;
    private int rulerValue = rulerValueDefault;//刻度单位跨度
    private int rulerValuePadding;//刻度单位与轴的间距
    private int rulerValuePaddingDP = 8;//刻度单位与轴的间距默认dp
    private float heightPercent = 1f;

    private int lineColor = Color.parseColor("#5DC9C6");//曲线颜色
    private float lineWidthDP = 0.7f;//曲线宽度dp

    private int pointColor = Color.parseColor("#5DC8C5");//锚点颜色
    private float pointWidthDefault = 10f;
    private float pointWidthDP = pointWidthDefault;//锚点宽度dp

    private int tableColor = Color.parseColor("#BBBBBB");//表格线颜色
    private float tableWidthDP = 0.5f;//表格线宽度dp

    private int rulerTextColor = tableColor;//表格标尺文本颜色
    private float rulerTextSizeSP = 10f;//表格标尺文本大小

    private int pointTextColor = Color.parseColor("#009688");//锚点文本颜色
    private float pointTextSizeSP = 10f;//锚点文本大小

    private float startX, startY;
    private boolean isShowTable = false;


    private boolean isInitialized = false;
    private boolean isPlayAnim = false;


    private int type;

    private ValueAnimator valueAnimator, valueAnimator2;

    private float currentValue = 0f;
    private boolean isAnimating = false;

    private ExposeState mExposeState;

    private int index = -1;
    private int animatorValue = 0;

    private Point point;

    private RectF mSrcRect = new RectF();//图片绘制区域
    private RectF mTopRect = new RectF();//图片绘制区域

    private RectF mOpenRect = new RectF();//图片绘制区域

    private OpenMessageListener mOpenMessageListener;


    public void setClockListener(OpenMessageListener listener) {
        this.mOpenMessageListener = listener;
    }


    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setupView();
    }

    private void setupView() {
        linePaint = new Paint();
        linePaint.setAntiAlias(true);//抗锯齿
        linePaint.setStyle(Paint.Style.STROKE);//STROKE描边FILL填充
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(dip2px(lineWidthDP));//边框宽度


        linePaint2 = new Paint();
        linePaint2.setAntiAlias(true);//抗锯齿
        linePaint2.setStyle(Paint.Style.STROKE);//STROKE描边FILL填充
        linePaint2.setColor(lineColor);
        linePaint2.setStrokeWidth(dip2px(lineWidthDP));//边框宽度
        PathEffect effects = new DashPathEffect(new float[]{4, 8}, 1);
        linePaint2.setPathEffect(effects);

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.parseColor("#5DC8C5"));
        pointPaint.setStrokeWidth(dip2px(pointWidthDP));

        pointPaint2 = new Paint();
        pointPaint2.setAntiAlias(true);
        pointPaint2.setStyle(Paint.Style.FILL);
        pointPaint2.setColor(Color.parseColor("#FFFFFF"));
        pointPaint2.setStrokeWidth(dip2px(pointWidthDP));

        pointPaint3 = new Paint();
        pointPaint3.setAntiAlias(true);
        pointPaint3.setStyle(Paint.Style.FILL);
        pointPaint3.setColor(Color.parseColor("#335DC9C6"));
        pointPaint3.setStrokeWidth(dip2px(pointWidthDP));

        tablePaint = new Paint();
        tablePaint.setAntiAlias(true);
        tablePaint.setStyle(Paint.Style.STROKE);
        tablePaint.setColor(tableColor);
        tablePaint.setStrokeWidth(dip2px(tableWidthDP));

        textRulerPaint = new Paint();
        textRulerPaint.setAntiAlias(true);
        textRulerPaint.setStyle(Paint.Style.FILL);
        textRulerPaint.setTextAlign(Paint.Align.CENTER);
        textRulerPaint.setColor(rulerTextColor);//文本颜色
        textRulerPaint.setTextSize(sp2px(rulerTextSizeSP));//字体大小

        textPointPaint = new Paint();
        textPointPaint.setAntiAlias(true);
        textPointPaint.setStyle(Paint.Style.FILL);
        textPointPaint.setTextAlign(Paint.Align.CENTER);
        textPointPaint.setColor(pointTextColor);//文本颜色
        textPointPaint.setTextSize(sp2px(pointTextSizeSP));//字体大小

        linePath = new Path();
        tablePath = new Path();

        resetParam();
    }

    private void initAnim() {
        valueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(dataList.size() * 500);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValue = (float) animation.getAnimatedValue();
                //    postInvalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                currentValue = 0f;
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                currentValue = 1f;
                isAnimating = false;
                isPlayAnim = false;
            }
        });
        valueAnimator.setStartDelay(500);
    }

    private void resetParam() {
        linePath.reset();
        tablePath.reset();
        stepSpace = dip2px(stepSpaceDP);
        tablePadding = dip2px(tablePaddingDP);
        rulerValuePadding = dip2px(rulerValuePaddingDP);
        stepStart = tablePadding * (isShowTable ? 2 : 1);
        stepEnd = stepStart + stepSpace * (dataList.size() - 1);
        topSpace = bottomSpace = tablePadding;
        linePoints = new Point[dataList.size()];

        initAnim();
        isInitialized = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = tablePadding + getTableEnd() + getPaddingLeft() + getPaddingRight();//计算自己的宽度
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);//父类期望的高度
        if (MeasureSpec.EXACTLY == heightMode) {
            height = getPaddingTop() + getPaddingBottom() + height;
        }
        setMeasuredDimension(width, height);//设置自己的宽度和高度
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


    private abstract class ExposeState {
        abstract void drawState(Canvas canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);//绘制背景颜色
        canvas.translate(0f, mHeight / 2f + (getViewDrawHeight() + topSpace + bottomSpace) / 2f);//设置画布中心点垂直居中
        if (!isInitialized) {
            setupLine();
        }
        if (isShowTable) {
            drawTable(canvas);//绘制表格
        }
        drawLine(canvas);//绘制曲线
        if (mExposeState == null) {   //绘制小圆点动画
            mExposeState = new RotateState();
        }
        mExposeState.drawState(canvas);

    }


    private void drawText(Canvas canvas, Paint textPaint, String text, float x, float y) {
        canvas.drawText(text, x, y, textPaint);

    }


    private class RotateState extends ExposeState {
        public RotateState() {
            valueAnimator2 = ValueAnimator.ofInt(0, 100);
            valueAnimator2.setDuration(1000);
            valueAnimator2.setInterpolator(new LinearInterpolator());//使用了overshootInterpolator插值器
            valueAnimator2.setRepeatCount(Animation.INFINITE);
            valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    animatorValue = (int) valueAnimator.getAnimatedValue();
                    postInvalidate();
                }
            });
            valueAnimator2.start();
        }

        @Override
        void drawState(Canvas canvas) {
            if (type == 1) {  //开启漂流
                drawOpenLinePoints(canvas);//绘制曲线上的点
            } else {
                drawLinePoints(canvas);//绘制曲线上的点
            }
        }

    }


    //防止Alpha值超过[0,255]而引起错误1
    private int limitAlpha(int value) {
        return Math.max(0, Math.min(value, 255));
    }

    private int limitScale(int value) {
        return Math.max(0, Math.min(value, 15));
    }


    private int limitFrame(int value) {
        return Math.max(0, Math.min(value, 40));
    }


    /**
     * 绘制标尺y轴文本
     *
     * @param canvas
     * @param text
     * @param x
     * @param y
     */
    private void drawRulerYText(Canvas canvas, String text, float x, float y) {
        textRulerPaint.setTextAlign(Paint.Align.RIGHT);
        Paint.FontMetrics fontMetrics = textRulerPaint.getFontMetrics();
        float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        float offsetY = fontTotalHeight / 2 - fontMetrics.bottom;
        float newY = y + offsetY;
        float newX = x - rulerValuePadding;
        drawText(canvas, textRulerPaint, text, newX, newY);
    }

    /**
     * 绘制标尺x轴文本
     *
     * @param canvas
     * @param text
     * @param x
     * @param y
     */
    private void drawRulerXText(Canvas canvas, String text, float x, float y) {
        textRulerPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = textRulerPaint.getFontMetrics();
        float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        float offsetY = fontTotalHeight / 2 - fontMetrics.bottom;
        float newY = y + offsetY + rulerValuePadding;
        drawText(canvas, textRulerPaint, text, x, newY);
    }

    /**
     * 绘制曲线上锚点文本
     *
     * @param canvas
     * @param text
     * @param x
     * @param y
     */
    private void drawLinePointText(Canvas canvas, String text, float x, float y) {
        textPointPaint.setTextAlign(Paint.Align.LEFT);
        textPointPaint.setTextSize(300);
        float newY = y - rulerValuePadding;
        drawText(canvas, textPointPaint, text, x, newY);
    }

    private int getTableStart() {
        return isShowTable ? stepStart + tablePadding : stepStart;
    }

    private int getTableEnd() {
        return isShowTable ? stepEnd + tablePadding : stepEnd;
    }

    /**
     * 绘制背景表格
     *
     * @param canvas
     */
    private void drawTable(Canvas canvas) {
        int tableEnd = getTableEnd();

        int rulerCount = maxValue / rulerValue;
        int rulerMaxCount = maxValue % rulerValue > 0 ? rulerCount + 1 : rulerCount;
        int rulerMax = rulerValue * rulerMaxCount + rulerValueDefault;

        tablePath.moveTo(stepStart, -getValueHeight(rulerMax));//加上顶部的间隔
        tablePath.lineTo(stepStart, 0);//标尺y轴
        tablePath.lineTo(tableEnd, 0);//标尺x轴

        int startValue = minValue - (minValue > 0 ? 0 : minValue % rulerValue);
        int endValue = (maxValue + rulerValue);

        //标尺y轴连接线
        do {
            int startHeight = -getValueHeight(startValue);
            tablePath.moveTo(stepStart, startHeight);
            tablePath.lineTo(tableEnd, startHeight);
            //绘制y轴刻度单位
            drawRulerYText(canvas, String.valueOf(startValue), stepStart, startHeight);
            startValue += rulerValue;
        } while (startValue < endValue);

        canvas.drawPath(tablePath, tablePaint);
        //绘制x轴刻度单位
        drawRulerXValue(canvas);
    }

    /**
     * 绘制标尺x轴上所有文本
     *
     * @param canvas
     */
    private void drawRulerXValue(Canvas canvas) {
        if (linePoints == null) return;
        for (int i = 0; i < linePoints.length; i++) {
            Point point = linePoints[i];
            if (point == null) break;
            drawRulerXText(canvas, String.valueOf(i), linePoints[i].x, 0);
        }
    }

    /**
     * 绘制曲线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        if (isPlayAnim) {
            Path dst = new Path();
            PathMeasure measure = new PathMeasure(linePath, false);
            measure.getSegment(0, measure.getLength() * currentValue, dst, true);
            canvas.drawPath(dst, linePaint);
        } else {
            canvas.drawPath(linePath, linePaint);
        }

    }

    /**
     * 绘制曲线上的锚点
     *
     * @param canvas
     */
    private void drawLinePoints(Canvas canvas) {
        if (linePoints == null) return;

        float pointWidth = dip2px(pointWidthDP) / 2;
        int pointCount = linePoints.length;
//        if (isPlayAnim) {
//            pointCount = Math.round(currentValue * linePoints.length);
//        }
        for (int i = 0; i < pointCount; i++) {
            point = linePoints[i];
            if (point == null) break;
            if (i == pointCount - 2 || i == pointCount - 1) {
                pointPaint3.setAlpha(limitAlpha(animatorValue));
                if (i == pointCount - 2) {
                    canvas.drawCircle(point.x, point.y, pointWidth + limitScale(animatorValue), pointPaint3);
                    canvas.drawCircle(point.x, point.y, pointWidth + 4, pointPaint2);
                    canvas.drawCircle(point.x, point.y, pointWidth, pointPaint);
                    if (index == -1) {
                        index = pointCount - 2;
                        showMessage(canvas);
                    }
                } else {
//                        canvas.drawCircle(point.x, point.y, pointWidth + 15, pointPaint3);
                    if (animatorValue < 33) {
                        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.transit1);
                    } else if (animatorValue < 66) {
                        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.transit2);
                    } else {
                        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.transit3);
                    }
                    canvas.drawBitmap(mBitmap, point.x - 65, point.y - 80, pointPaint);
                }
            } else {
                canvas.drawCircle(point.x, point.y, pointWidth + 4, pointPaint2);
                canvas.drawCircle(point.x, point.y, pointWidth, pointPaint);
            }
            //绘制点的文本
            if (i == index) {
                showMessage(canvas);
            }
        }
    }

    /**
     * @description 飘来新消息
     */

    public void showMessage(Canvas canvas) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.layout_new_message, null, false);
        v.measure(v.getWidth(), v.getHeight());
        mBitmap2 = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        int mBitmapWidth = mBitmap2.getWidth();
        int mBitmapHeight = mBitmap2.getHeight();
        canvasTop = new Canvas(mBitmap2);
        v.layout(0, 0, mBitmapWidth, mBitmapHeight);
        v.draw(canvasTop);
        canvasTop.setBitmap(null);

        floatAnim(mBitmap2, canvas, point.x + 30, point.y - mBitmapHeight);

        mTopRect.left = point.x + 30;
        mTopRect.top = mHeight / 2f + (getViewDrawHeight() + topSpace + bottomSpace) / 2f + point.y - mBitmapHeight;
        mTopRect.right = point.x + 30 + mBitmapWidth;
        mTopRect.bottom = mHeight / 2f + (getViewDrawHeight() + topSpace + bottomSpace) / 2f + point.y + mBitmapHeight;

//        canvas.drawRect(mTopRect, customPaint(Color.WHITE));
    }


    private Paint customPaint(int color) {
        Paint paint = new Paint();
        paint.setColor(color);//画笔颜色
        paint.setStyle(Paint.Style.FILL);//实心
        paint.setStrokeWidth(6);//画笔宽度
        paint.setAntiAlias(true);//光滑
        return paint;
    }


    /**
     * 绘制曲线上的锚点
     *
     * @param canvas
     */
    private void drawOpenLinePoints(Canvas canvas) {
        if (linePoints == null) return;

        float pointWidth = dip2px(pointWidthDP) / 2;
        int pointCount = linePoints.length;

        for (int i = 0; i < pointCount; i++) {
            point = linePoints[i];
            if (point == null) break;
            if (i == 0) {
                canvas.drawCircle(point.x, point.y, pointWidth + limitScale(animatorValue), pointPaint3);
                canvas.drawCircle(point.x, point.y, pointWidth + 4, pointPaint2);
                canvas.drawCircle(point.x, point.y, pointWidth, pointPaint);
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View v = inflater.inflate(R.layout.layout_start_drifting, null, false);
                v.measure(v.getWidth(), v.getHeight());
                mBitmap2 = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                int mBitmapWidth = mBitmap2.getWidth();
                int mBitmapHeight = mBitmap2.getHeight();
                canvasTop = new Canvas(mBitmap2);
                v.layout(0, 0, mBitmapWidth, mBitmapHeight);
                v.draw(canvasTop);
                canvasTop.setBitmap(null);

                floatAnim(mBitmap2, canvas, point.x - (mBitmapWidth / 2), point.y - (mBitmapWidth / 2));

                mOpenRect.left = point.x - (mBitmapWidth / 2);
                mOpenRect.top = mHeight / 2f + (getViewDrawHeight() + topSpace + bottomSpace) / 2f + point.y - (mBitmapWidth / 2);
                mOpenRect.right = point.x - (mBitmapWidth / 2) + mBitmapWidth;
                mOpenRect.bottom = mHeight / 2f + (getViewDrawHeight() + topSpace + bottomSpace) / 2f + point.y + (mBitmapWidth / 2);
            }
        }
    }

    //飘来新消息上下浮动

    private void floatAnim(Bitmap mBitmap, Canvas canvas, int x, int y) {
        if (animatorValue < 5) {
            canvas.drawBitmap(mBitmap, x, y - 39, null);
        } else if (animatorValue < 10) {
            canvas.drawBitmap(mBitmap, x, y - 38, null);
        } else if (animatorValue < 15) {
            canvas.drawBitmap(mBitmap, x, y - 37, null);
        } else if (animatorValue < 20) {
            canvas.drawBitmap(mBitmap, x, y - 36, null);
        } else if (animatorValue < 25) {
            canvas.drawBitmap(mBitmap, x, y - 35, null);
        } else if (animatorValue < 30) {
            canvas.drawBitmap(mBitmap, x, y - 34, null);
        } else if (animatorValue < 35) {
            canvas.drawBitmap(mBitmap, x, y - 33, null);
        } else if (animatorValue < 40) {
            canvas.drawBitmap(mBitmap, x, y - 32, null);
        } else if (animatorValue < 45) {
            canvas.drawBitmap(mBitmap, x, y - 31, null);
        } else if (animatorValue < 50) {
            canvas.drawBitmap(mBitmap, x, y - 30, null);
        } else if (animatorValue < 55) {
            canvas.drawBitmap(mBitmap, x, y - 31, null);
        } else if (animatorValue < 60) {
            canvas.drawBitmap(mBitmap, x, y - 32, null);
        } else if (animatorValue < 65) {
            canvas.drawBitmap(mBitmap, x, y - 33, null);
        } else if (animatorValue < 70) {
            canvas.drawBitmap(mBitmap, x, y - 34, null);
        } else if (animatorValue < 75) {
            canvas.drawBitmap(mBitmap, x, y - 35, null);
        } else if (animatorValue < 80) {
            canvas.drawBitmap(mBitmap, x, y - 36, null);
        } else if (animatorValue < 85) {
            canvas.drawBitmap(mBitmap, x, y - 37, null);
        } else if (animatorValue < 90) {
            canvas.drawBitmap(mBitmap, x, y - 38, null);
        } else if (animatorValue < 95) {
            canvas.drawBitmap(mBitmap, x, y - 39, null);
        } else {
            canvas.drawBitmap(mBitmap, x, y - 40, null);
        }
    }


    /**
     * 获取value值所占的view高度
     *
     * @param value
     * @return
     */
    private int getValueHeight(int value) {
        float valuePercent = Math.abs(value - minValue) * 100f / (Math.abs(maxValue - minValue) * 100f);//计算value所占百分比
        return (int) (getViewDrawHeight() * valuePercent + bottomSpace + 0.5f);//底部加上间隔
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
        int stepTemp = getTableStart();
        Point pre = new Point();
        pre.set(stepTemp, -getValueHeight(dataList.get(0).getValue()));//坐标系从0,0默认在第四象限绘制
        linePoints[0] = pre;
        linePath.moveTo(pre.x, pre.y);

        if (dataList.size() == 1) {
            isInitialized = true;
            return;
        }

        for (int i = 1; i < dataList.size(); i++) {
            Data data = dataList.get(i);
            Point next = new Point();
            next.set(stepTemp += stepSpace, -getValueHeight(data.getValue()));
            int cW = pre.x + stepSpace / 2;
            Point p1 = new Point();//控制点1
            p1.set(cW, pre.y);
            Point p2 = new Point();//控制点2
            p2.set(cW, next.y);
            if (type == 1) {
                if (i != dataList.size() - 1) {
                    linePath.cubicTo(p1.x, p1.y, p2.x, p2.y, next.x, next.y);//创建三阶贝塞尔曲线
                }
            } else {
                linePath.cubicTo(p1.x, p1.y, p2.x, p2.y, next.x, next.y);//创建三阶贝塞尔曲线
            }
            pre = next;
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
    public void setData(List<Data> dataList, int type) {
        index = -1;
        this.type = type;
        if (dataList == null) {
            throw new RuntimeException("dataList cannot is null!");
        }
        if (dataList.isEmpty()) return;
        this.dataList.clear();
        this.dataList.addAll(dataList);

        maxValue = Collections.max(this.dataList, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.getValue() - o2.getValue();
            }
        }).getValue() + 50;

        minValue = Collections.min(this.dataList, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.getValue() - o2.getValue();
            }
        }).getValue() + 30;

        refreshLayout();
    }

    /**
     * 设置是否显示标尺表格
     *
     * @param showTable
     */
    public void setShowTable(boolean showTable) {
        isShowTable = showTable;
        refreshLayout();
    }


    /**
     * 设置标尺y轴间距
     *
     * @param space
     */
    public void setRulerYSpace(int space) {
        if (space <= 0) {
            space = rulerValueDefault;
        }
        this.rulerValue = space;
        refreshLayout();
    }


    /**
     * 设置锚点尺寸
     *
     * @param dp
     */
    public void setPointWidth(float dp) {
        if (dp <= 0) {
            dp = pointWidthDefault;
        }
        this.pointWidthDP = dp;
        refreshLayout();
    }

    /**
     * 播放动画
     */
    public void playAnim() {
        this.isPlayAnim = true;
        if (isAnimating) return;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
    }


    public static class Data {

        int value;

        public Data(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    /**
     * 回收bitmap
     */
    public void recycleBitmap() {
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
        if (mBitmap2 != null && !mBitmap2.isRecycled()) {
            mBitmap2.recycle();
            mBitmap2 = null;
        }
    }

    public interface OpenMessageListener {
        void onClick(int index);

        void onOPenClick(int index);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                //获取屏幕上点击的坐标
                startX = event.getX();
                startY = event.getY();
                Log.e("HJQresult", "点击了-----" + startX + "----" + startY);
                Log.e("HJQresult", "控件范围：" + mOpenRect);
                if (type == 1) {
                    if (mOpenRect.contains(startX, startY)) {
                        mOpenMessageListener.onClick(-1);
                    }
                } else {
                    if (mTopRect.contains(startX, startY)) {
                        mOpenMessageListener.onOPenClick(index);
                        Log.e("HJQresult", "点击了-----");
                    } else {

                        for (int i = 0; i < linePoints.length - 1; i++) {
                            Point point = linePoints[i];
                            mSrcRect.left = point.x - 30;
                            mSrcRect.top = point.y - dip2px(pointWidthDP) / 2 + (point.y - getViewDrawHeight());
                            mSrcRect.right = point.x + dip2px(pointWidthDP) + 20;
                            mSrcRect.bottom = mHeight + point.y - (dip2px(pointWidthDP) / 2) - (point.y - getViewDrawHeight());
                            if (mSrcRect.contains(startX, startY)) {
                                index = i;
                                mOpenMessageListener.onClick(index);
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                invalidate();//更新视图
                return true;
        }
        return super.onTouchEvent(event);
    }


}
