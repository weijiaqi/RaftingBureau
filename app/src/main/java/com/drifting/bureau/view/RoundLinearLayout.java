package com.drifting.bureau.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.drifting.bureau.R;

import java.math.BigDecimal;

/**
 * 用于需要圆角矩形框背景的LinearLayout的情况,减少直接使用LinearLayout时引入的shape资源文件
 */
public class RoundLinearLayout extends LinearLayout {

    private Paint mPaint;
    private int rowNumber;//行数量
    private int columnNumber;//列数量
    private int gridLineColor;//网格线的颜色
    private float rowSpacing;//网格行间距
    private float columnSpacing;//网格列间距
    private float gridLineWidth = 2;//网格线的线宽
    private float zoomSize = 5;//缩放尺寸,为了让最高点的数值显示出来


    private RoundViewDelegate delegate;
    private boolean intercept = false;

    public RoundLinearLayout(Context context) {
        this(context, null);
    }

    public RoundLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        delegate = new RoundViewDelegate(this, context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    /**
     * use delegate to set attr
     */
    public RoundViewDelegate getDelegate() {
        return delegate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (delegate.isWidthHeightEqual() && getWidth() > 0 && getHeight() > 0) {
            int max = Math.max(getWidth(), getHeight());
            int measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (delegate.isRadiusHalfHeight()) {
            delegate.setCornerRadius(getHeight() / 2);
        } else {
            delegate.setBgSelector();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (intercept) {
            return intercept;
        }
        return super.onInterceptTouchEvent(ev);
    }



    @Override
    public void draw(Canvas canvas) {

//        canvas.drawColor(getResources().getColor(R.color.color_f1f2));

        rowNumber = getHeight() / 40;
        columnNumber = getWidth() / 40;

        //计算行间距(去除缩放)
        rowSpacing = getFloat((int) (getHeight() - 2 * zoomSize), rowNumber);

        //计算列间距
        columnSpacing = getFloat((int) (getWidth() - 2 * zoomSize), columnNumber);

        //画行网格线
        mPaint.setStrokeWidth(gridLineWidth);
        for (int i = 0; i < rowNumber + 1; i++) {
            mPaint.setColor(getResources().getColor(R.color.color_black_19000000));
            canvas.drawLine(zoomSize - gridLineWidth * 0.5f, rowSpacing * i + zoomSize,
                    getWidth() - zoomSize + gridLineWidth * 0.5f, rowSpacing * i + zoomSize,
                    mPaint);
        }
        //画列网格线
        for (int i = 0; i < columnNumber + 1; i++) {
            mPaint.setColor(getResources().getColor(R.color.color_black_19000000));
            canvas.drawLine(columnSpacing * i + zoomSize, zoomSize,
                    columnSpacing * i + zoomSize, getHeight() - zoomSize,
                    mPaint);
        }

        super.draw(canvas);
    }

    public float getFloat(int a, int b) {
        float value = new BigDecimal((float) a / b).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return value;
    }
}
