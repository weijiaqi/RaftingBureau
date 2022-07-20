package com.drifting.bureau.util;


import com.drifting.bureau.mvp.model.entity.LoginEntity;
import com.drifting.bureau.storageinfo.Preferences;
import com.jess.arms.integration.AppManager;

import io.rong.imkit.RongIM;


/**
 * @Description:
 * @Author: wjq
 * @CreateDate: 2022/2/18 16:15
 */
public class LogInOutDataUtil {
    public static void successInSetData(LoginEntity entity) {
        Preferences.setAnony(true);
        Preferences.saveUserName(entity == null ? "" : entity.getName());
        Preferences.saveToken(entity == null ? "" : entity.getToken());
        Preferences.saveRcToken(entity == null ? "" : entity.getRc_token());
        Preferences.saveUserPhoto(entity == null ? "" : entity.getProfile_photo());
        Preferences.saveUserId(entity == null ? "" : entity.getUser_id());
        Preferences.savePhone(entity == null ? "" : entity.getMobile());
        Preferences.savePassword(entity == null ? "" : entity.getPassword());
    }

    public static void successOutClearData() {
        RongIM.getInstance().logout();
        Preferences.clearUserLoginData();
        AppManager.getAppManager().killAll();
    }

    public static void successOutClearAllData() {
        RongIM.getInstance().logout();
        Preferences.clear();
        AppManager.getAppManager().killAll();

    }
}
