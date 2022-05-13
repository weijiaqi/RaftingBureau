package com.drifting.bureau.util;

import android.annotation.SuppressLint;
import android.view.Gravity;
import com.drifting.bureau.R;
import com.hjq.toast.ToastUtils;

/**
 * @Description: Toast输出
 * @Author: wjq
 * @CreateDate: 2022/2/15 11:09
 */
@SuppressLint("StaticFieldLeak")
public class ToastUtil {

    /**
     * 普通中间Toast
     */
    public static void showToast(String content) {
        ToastUtils.setView(R.layout.toast_layout_center);
        ToastUtils.setGravity(Gravity.CENTER);
        ToastUtils.show(content);
    }

}
