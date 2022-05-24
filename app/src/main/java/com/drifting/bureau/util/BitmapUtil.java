package com.drifting.bureau.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

public class BitmapUtil {
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }
}
