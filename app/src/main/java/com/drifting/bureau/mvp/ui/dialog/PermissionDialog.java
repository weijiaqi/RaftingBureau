package com.drifting.bureau.mvp.ui.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.KeyEvent;

import com.jess.arms.base.BaseDialog;

import java.util.List;

/**
* @description 动态权限
*/
public class PermissionDialog {
    private static PermissionDialog mPermissionUtils;
    private PermissionDialog() {
    }
    public static PermissionDialog create() {
        if (mPermissionUtils == null) {
            synchronized (PermissionDialog.class) {
                if (mPermissionUtils == null) {
                    mPermissionUtils = new PermissionDialog();
                }
            }
        }
        return mPermissionUtils;
    }





    /**
     * 设置权限dialog
     */
    public void showDialog(Activity activity, List<String> permissions) {
        String permissionsTitle = "存储权限管理";
        String permissionsDescribe = "请在“权限管理”中开启存储权限，开通后可正常使用相关功能";

        for (int i=0;i<permissions.size();i++){
            switch (permissions.get(i)){
                case "android.permission.CAMERA":
                    permissionsTitle = "相机权限管理";
                    permissionsDescribe = "请在“权限管理”中开启相机权限，开通后可正常使用相关功能";
                    break;
                case "android.permission.RECORD_AUDIO":
                    permissionsTitle = "录音权限管理";
                    permissionsDescribe = "请在“权限管理”中开启录音权限，开通后可正常使用相关功能";
                    break;
                case "android.permission.WRITE_EXTERNAL_STORAGE":
                    permissionsTitle = "存储权限管理";
                    permissionsDescribe = "请在“权限管理”中开启存储权限，开通后可正常使用相关功能";
                    break;
            }
        }
        CurrencyDialog currencyDialog = new CurrencyDialog(activity);
        currencyDialog.show();
        currencyDialog.setType(2)
                .setText("去设置")
                .setTitleText(permissionsTitle, permissionsDescribe);
        currencyDialog.setCanceledOnTouchOutside(false);
        currencyDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        currencyDialog.setOnClickCallback(new BaseDialog.OnClickCallback() {
            @Override
            public void onClickType(int type) {
                if (type == CurrencyDialog.SELECT_FINISH) {
                    getSettingIntent(activity);
                }
            }
        });
    }

    /**
     * 获取应用详情页面intent
     *
     * @param activity activity_release_scheme
     */
    private void getSettingIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(intent);
    }
}
