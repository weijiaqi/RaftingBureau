package com.drifting.bureau.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.google.ar.core.ArCoreApk;
import com.google.ar.sceneform.Sceneform;

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
            message = "请安装 Google Play Services以使用场景展示";
<<<<<<< HEAD
=======
            new Handler().postDelayed(() -> checkArCoreAvailability(context), 250);
>>>>>>> origin/dev
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


}
