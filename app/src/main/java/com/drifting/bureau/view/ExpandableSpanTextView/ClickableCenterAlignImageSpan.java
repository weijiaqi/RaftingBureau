package com.drifting.bureau.view.ExpandableSpanTextView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.view.View;

public abstract class ClickableCenterAlignImageSpan extends ImageSpan {
    public static final String TAG = "ClickableCenterAlignImageSpan";

    public ClickableCenterAlignImageSpan(Context context, Bitmap b) {
        super(context, b);
    }

    public ClickableCenterAlignImageSpan(Context context, Bitmap b, int verticalAlignment) {
        super(context, b, verticalAlignment);
    }

    public ClickableCenterAlignImageSpan(Drawable d) {
        super(d);
    }

    public ClickableCenterAlignImageSpan(Drawable d, int verticalAlignment) {
        super(d, verticalAlignment);
    }

    public ClickableCenterAlignImageSpan(Drawable d, String source) {
        super(d, source);
    }

    public ClickableCenterAlignImageSpan(Drawable d, String source, int verticalAlignment) {
        super(d, source, verticalAlignment);
    }

    public ClickableCenterAlignImageSpan(Context context, Uri uri) {
        super(context, uri);
    }

    public ClickableCenterAlignImageSpan(Context context, Uri uri, int verticalAlignment) {
        super(context, uri, verticalAlignment);
    }

    public ClickableCenterAlignImageSpan(Context context, int resourceId) {
        super(context, resourceId);
    }

    public ClickableCenterAlignImageSpan(Context context, int resourceId, int verticalAlignment) {
        super(context, resourceId, verticalAlignment);

    }

    @Override
    public String getSource() {
        return super.getSource();
    }

    public abstract void onClick(View view);

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false); //去掉下划线
    }

}