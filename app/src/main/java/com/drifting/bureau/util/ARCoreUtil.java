package com.drifting.bureau.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import com.google.ar.core.ArCoreApk;
import com.google.ar.sceneform.Sceneform;

import java.util.Objects;

/**
 * 检查AR是否支持
 */
public class ARCoreUtil {

    /**
     * 检测是否支持AR
     *
     * @param context
     * @return
     */
    public static boolean checkArCoreAvailability(Context context) {
        String message;
        ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(context);
        if (!Sceneform.isSupported(context)) {
            message = "此设备不支持 AR！";
        } else if (availability == availability.UNKNOWN_ERROR) {
            message = "此设备不支持 AR！";
        } else if (availability == availability.UNKNOWN_CHECKING) {
            message = "请安装 Google Play Services插件以使用场景展示";
        } else if (availability == availability.UNKNOWN_TIMED_OUT) {
            message = "此设备不支持 AR！";
        } else if (availability == availability.UNSUPPORTED_DEVICE_NOT_CAPABLE) {
            message = "此设备不支持 AR！";
        } else if (availability == availability.SUPPORTED_NOT_INSTALLED) {
            message = "此设备不支持 AR！";
        } else if (availability == availability.SUPPORTED_APK_TOO_OLD) {
            message = "请更新 ArCore 以使用场景展示";
        } else if (availability == availability.SUPPORTED_INSTALLED) {
            return true;
        } else {
            message = "此设备不支持 AR！";
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        return false;
    }


    public static boolean checkSystemSupport(Activity activity) {
        //checking whether the API version of the running Android >= 24 that means Android Nougat 7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String openGlVersion = ((ActivityManager) Objects.requireNonNull(activity.getSystemService(Context.ACTIVITY_SERVICE))).getDeviceConfigurationInfo().getGlEsVersion();
            //checking whether the OpenGL version >= 3.0
            if (Double.parseDouble(openGlVersion) >= 3.0) {
                return true;
            } else {
                Toast.makeText(activity, "App needs OpenGl Version 3.0 or later", Toast.LENGTH_SHORT).show();
                activity.finish();
                return false;
            }
        } else {
            Toast.makeText(activity, "App does not support required Build Version", Toast.LENGTH_SHORT).show();
            activity.finish();
            return false;
        }

    }


}
