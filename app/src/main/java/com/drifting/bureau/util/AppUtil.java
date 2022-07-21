package com.drifting.bureau.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.drifting.bureau.app.application.RBureauApplication;

import java.util.List;

/**
 * @author 卫佳琪1
 * @description 工具类
 * @time 15:23 15:23
 */

public class AppUtil {

    private static String VERNAME;
    private static int VERCODE;
    /**
     * 得到软件显示版本信息
     *
     * @param context 上下文
     * @return 当前版本信息
     */
    public static String getVerName(Context context) {
        if (VERNAME == null)
            try {
                String packageName = context.getPackageName();
                VERNAME = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        return VERNAME;
    }


    /**
     * @description 获取版本号
     */
    public static int getVersionCode(Context context){
        if (VERCODE == 0)
            try {
                String packageName = context.getPackageName();
                VERCODE = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        return VERCODE;

    }


    /**
     * 判断app是否处于前台
     *
     * @return
     */
    public static boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) RBureauApplication.getContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = RBureauApplication.getContext().getPackageName();
        /**
         * 获取Android设备中所有正在运行的App
         */
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }


    /**
     * 获取腾讯打包工具生成的渠道信息，直接运行或无渠道信息默认
     *
     * @param context
     * @return
     */
    public static String getChannel(Context context) {
        String channelName = SubChannelUtil.getChannel(context);
        if (!TextUtils.isEmpty(channelName)) {
            return channelName;
        }
        if (TextUtils.isEmpty(channelName)) {
            channelName = "a_drifting_user";
        }
        return channelName;
    }


    /**
     * 判断程序是否在后台
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
