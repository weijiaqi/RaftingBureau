package com.drifting.bureau.util;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.drifting.bureau.app.application.RBureauApplication;

/**
 * @Description:
 * @Author: wjq
 * @CreateDate: 2022/2/14 13:51
 */
public class SystemUtil {


    /**
     * 包名判断是否为主进程
     *
     * @param
     * @return
     */
    public static boolean isMainProcess() {
        return RBureauApplication.getContext().getPackageName().equals(getCurrentProcessName());
    }


    /**
     * 获取当前进程名
     * @return 返回当前进程名
     */
    public static String getCurrentProcessName() {
        try {
            final int pid = android.os.Process.myPid();
            ActivityManager activityManager = (ActivityManager) RBureauApplication.getContext()
                    .getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcessInfo : activityManager
                    .getRunningAppProcesses()) {
                if (appProcessInfo.pid == pid) {
                    return appProcessInfo.processName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getUserAgent(Context context) {
        String userAgent = "";
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            try {
//                userAgent = WebSettings.getDefaultUserAgent(context);
//            } catch (Exception e) {
//                userAgent = System.getProperty("http.agent");
//            }
//        } else {
//            userAgent = System.getProperty("http.agent");
//        }
        userAgent = System.getProperty("http.agent");
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    /**
     * 键盘下落
     */
    public static void setKeyboardDown(Context mContext, EditText mEtContent) {
        if (mEtContent != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) mContext.getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(mEtContent.getWindowToken(), 0);
            }
        }
    }


    /**
     * 键盘下落
     */
    public static void setKeyboardDown(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null && inputmanger != null) {
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * 获取系统状态栏高度
     *
     * @param context 上下文
     * @return 返回系统状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 复制文本
     */
    public static void copyText(Context activity, String content) {
        ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", content);
        if (cm != null) {
            cm.setPrimaryClip(mClipData);
        }
    }

}
