package com.drifting.bureau.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import com.drifting.bureau.R;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.hjq.toast.ToastUtils;

/**
 * @Description: Toast输出
 * @Author: wjq
 * @CreateDate: 2022/2/15 11:09
 */
@SuppressLint("StaticFieldLeak")
public class ToastUtil {

    private static PublicDialog publicDialog;
    /**
     * 普通中间Toast
     */
    public static void showToast(String content) {
        ToastUtils.setView(R.layout.toast_layout_center);
        ToastUtils.setGravity(Gravity.CENTER);
        ToastUtils.show(content);
    }

    /**
     * 普通中间Toast
     */
    public static void showBottomToast(String content) {
        ToastUtils.setView(R.layout.toast_layout_center);
        ToastUtils.setGravity(Gravity.BOTTOM);
        ToastUtils.show(content);
    }

    /**
     * 添加好友dialog
     * @param context
     */
    public static void  showAddFriendDialog(Context context){
        publicDialog = new PublicDialog(context);
        publicDialog.show();
        publicDialog.setCancelable(false);
        publicDialog.setTitleText("已发送添加信息");
        publicDialog.setContentText("请等待对方回复吧");
    }

}
