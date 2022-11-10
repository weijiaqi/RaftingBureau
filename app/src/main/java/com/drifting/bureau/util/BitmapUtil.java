package com.drifting.bureau.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

public class BitmapUtil {


    public static Bitmap captureView(View view) {
        // 根据View的宽高创建一个空的Bitmap
        Bitmap bitmap = Bitmap.createBitmap(
                view.getWidth(),
                view.getHeight(),
                Bitmap.Config.RGB_565);
        // 利用该Bitmap创建一个空的Canvas
        Canvas canvas = new Canvas(bitmap);
        // 绘制背景(可选)
        canvas.drawColor(Color.WHITE);
        // 将view的内容绘制到我们指定的Canvas上
        view.draw(canvas);
        return bitmap;
    }


    /**
     * 获取视频第一帧
     *
     * @param
     * @return
     */
    public static Bitmap createVideoThumbnail(Context context, String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (filePath.startsWith("http://")
                    || filePath.startsWith("https://")
                    || filePath.startsWith("widevine://")) {
                retriever.setDataSource(filePath, new Hashtable<String, String>());
            } else {
                retriever.setDataSource(filePath);
            }
            bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC); //retriever.getFrameAtTime(-1);
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
                ex.printStackTrace();
            }
        }

        if (bitmap == null) {
            return null;
        }

        //缩放法压缩
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }


    /**
     * 把batmap 转file
     *
     * @param bitmap
     */
    public static File saveBitmapFile(Context context, Bitmap bitmap) {
        // 首先保存图片 创建文件夹
        File appDir = StorageUtil.getCacheDirectory(context);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //图片文件名称
        String fileName = "picture_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg";
        File file = new File(appDir, fileName);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 保存Bitmap到图库
     *
     * @param bmp
     * @return
     */
    public static void saveImageToGallery(Bitmap bmp, Context context) {
        // 首先保存图片 创建文件夹
        File appDir = StorageUtil.getCacheDirectory(context);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //图片文件名称
        String fileName = "picture_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        String path = file.getAbsolutePath();
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), path, fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }


    /**
     * 将本地图片转成Bitmap
     *
     * @param path 已有图片的路径
     * @return
     */
    public static Bitmap openImage(String path) {
        Bitmap bitmap = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 对图片进行旋转，拍照后应用老是显示图片横向，而且是逆时针90度，现在给他设置成显示顺时针90度
     *
     * @param bitmap 图片
     * @param degree 顺时针旋转的角度
     * @return 返回旋转后的位图
     */
    public static Bitmap rotateImage(Bitmap bitmap, float degree) {
        //create new matrix
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bmp;
    }


    public static Bitmap getBitmap(Uri uri, Context context) {
        if (uri == null) return null;

        Bitmap bitmap = null;
        try {
            ContentResolver cr = context.getContentResolver();
            bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        return bitmap;
    }


    private static Bitmap[] bitmaps;

    /**
     * 获取指定View的截图
     *
     * @param view
     */

    public static Bitmap getImageOfView(View view) {
        int width1 = view.getMeasuredWidth();
        int height1 = view.getMeasuredHeight();
        Bitmap bmp1 = Bitmap.createBitmap(width1, height1,
                Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bmp1));
        Canvas canvas = new Canvas(bmp1);
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        canvas.drawBitmap(bmp1, 0, 0, null);
        return bmp1;
    }


    /**
     * 实现bitmap一分为二的效果
     * @param bitmap
     * @return
     */
    public static void getCropBitmaps(Bitmap bitmap){
        if (bitmap == null) {
            return ;
        }
        int with = bitmap.getWidth(); // 得到图片的宽，高
        int height = bitmap.getHeight();
        int nw, nh, retX;
        nw = with / 2;
        nh = height;
        retX = with / 2;
        // 两张图片的容器
        Bitmap[] bitmapArray = new Bitmap[2];
        // 第一张图片从X坐标0的地方开始截取一半的宽度
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, nw, nh, null,
                false);
        // 第二张图片从X坐标为width一半的的地方开始截取一半的宽度
        Bitmap bmp1 = Bitmap.createBitmap(bitmap, retX, 0, nw, nh, null,
                false);
        bitmapArray[0] = bmp;// 左边图片
        bitmapArray[1] = bmp1;// 右边图片
        if (bitmap != null && !bitmap.equals(bmp) && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmaps = bitmapArray;//缓存
    }


    /**
     * 获取缓存的bitmap
     *
     * @return
     */
    public static Bitmap[] getBitmaps() {
        return bitmaps;
    }
}
