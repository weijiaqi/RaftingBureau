package com.drifting.bureau.view.ExpandableSpanTextView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextPaint;
import android.view.View;

import androidx.annotation.NonNull;

public class ImageSpanImageSpan extends ClickableCenterAlignImageSpan {
    private int mIndex=-1;
    public ImageSpanImageSpan(Context context, Bitmap b) {
        super(context, b);
    }

    public ImageSpanImageSpan(Context context, Bitmap b, int verticalAlignment) {
        super(context, b, verticalAlignment);
    }

    public ImageSpanImageSpan(int index, Drawable imgDrawable) {
        super(imgDrawable);
        mIndex=index;
    }

    public int getmIndex() {
        return mIndex;
    }

    public void setmIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    public ImageSpanImageSpan(Drawable d) {
        super(d);
    }

    public ImageSpanImageSpan(Drawable d, int verticalAlignment) {
        super(d, verticalAlignment);
    }

    public ImageSpanImageSpan(Drawable d, String source) {
        super(d, source);
    }

    public ImageSpanImageSpan(Drawable d, String source, int verticalAlignment) {
        super(d, source, verticalAlignment);
    }

    public ImageSpanImageSpan(Context context, Uri uri) {
        super(context, uri);
    }

    public ImageSpanImageSpan(Context context, Uri uri, int verticalAlignment) {
        super(context, uri, verticalAlignment);
    }

    public ImageSpanImageSpan(Context context, int resourceId) {
        super(context, resourceId);
    }

    public ImageSpanImageSpan(Context context, int resourceId, int verticalAlignment) {
        super(context, resourceId, verticalAlignment);
    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                     @NonNull Paint paint) {

        Drawable b = getDrawable();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;//计算y方向的位移
        canvas.save();
        canvas.translate(x, transY);//绘制图片位移一段距离
        b.draw(canvas);
        canvas.restore();
    }
    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.bgColor =  0;
        ds.setUnderlineText(false);

//        ds.setColor(Color.parseColor("#00000000"));
    }
}