package com.drifting.bureau.util;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.text.TextUtils;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.drifting.bureau.app.application.RBureauApplication;

import java.util.List;

/**
 * @author 卫佳琪1
 * @description 工具类
 * @time 15:23 15:23
 */

public class AppUtil {

    private static String VERNAME;

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

}
