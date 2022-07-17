package com.drifting.bureau.util;

import static io.rong.imkit.utils.FileTypeUtils.formatFileSize;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtil {

    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值

    /**
     * 语音存储路径
     *
     * @param
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getVoicName() {
        String fileName = "voice_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".mp3";

        return fileName;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String saveVideoPath(Context context) {
        String fileName = "video_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".mp4";
        File appDir = StorageUtils.getCacheDirectory(context);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, fileName);
        if (!appDir.exists()) {
            appDir.mkdir();
        } else {
            if (file.exists()) {
                file.delete();
            }
        }
        return appDir.getPath() + "/" + fileName;
    }


    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return FormetFileSize(blockSize, sizeType);
    }


    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }


    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    //strFile 为文件名称 返回true为存在
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (f.exists()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUrlFileName(String url) {
        String fileName = null;
        int index = url.lastIndexOf(File.separator);
        if (index != -1) {
            fileName = url.substring(index + 1);
        } else {
            fileName = url;
        }
        return fileName;
    }


    /**
     * 获取网络资源文件大小
     */
    private void getNetworkFileSize(String fileUrl) {
        final Handler handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String fileSize = msg.getData().getString("fileSize");
                Log.d("fileSize", fileSize);
            }
        };


        if (fileUrl == null) {
            return;
        }

        if (fileUrl.length() == 0) {
            return;
        }

        URL url = null;
        try {
            url = new URL(fileUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (url == null) {
            return;
        }

        final URL finalUrl = url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection urlConnection = (HttpURLConnection) finalUrl.openConnection();
                    int fileLength = urlConnection.getContentLength();

                    Bundle bundle = new Bundle();
                    bundle.putString("fileSize", "文件大小：" + formatFileSize(fileLength));

                    Message message = handler.obtainMessage();
                    message.setData(bundle);

                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
