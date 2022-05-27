package com.drifting.bureau.util;


import com.drifting.bureau.mvp.model.entity.LoginEntity;
import com.drifting.bureau.storageinfo.Preferences;


/**
 * @Description:
 * @Author: wjq
 * @CreateDate: 2022/2/18 16:15
 */
public class LogInOutDataUtil {
    public static void successInSetData(LoginEntity entity) {
        Preferences.setAnony(true);
        Preferences.saveToken(entity == null ? "" : entity.getToken());
        Preferences.savePhone(entity == null ? "" : entity.getMobile());
        Preferences.savePassword(entity == null ? "" : entity.getPassword());
    }

    public static void successOutClearData() {
        Preferences.clearUserLoginData();
    }
}