package com.drifting.bureau.storageinfo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author 卫佳琪1
 * @description 本地存储
 * @time 14:11 14:11
 */

public class Preferences {
    private static Context mContext;
    public static final String SP_NAME = "config";

    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_RC_TOKEN = "rc_token";
    private static final String KEY_USER_PHOTO = "user_photo";
    private static final String KEY_USER_TOKEN = "user_token";
    private static final String KEY_USER_PHONE = "user_phone";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_MASCOT = "user_mascot";
    private static final String KEY_USER_PASSWORD = "user_password";

    private static final String KEY_USER_IS_ANONY = "isanony";//是否为匿名状态

    /**
     * 初始化构建
     */
    public static void initialize(Application app) {
        mContext = app.getApplicationContext();
    }

    static SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }


    /**
     * @return true已登录 false未登录
     */
    public static boolean isAnony() {
        return !(getString(KEY_USER_IS_ANONY) == null || "0".equals(getString(KEY_USER_IS_ANONY))) && "1".equals(getString(KEY_USER_IS_ANONY));
    }

    public static void setAnony(boolean isAnony) {
        if (isAnony) {
            saveString(KEY_USER_IS_ANONY, 1 + "");
        } else {
            saveString(KEY_USER_IS_ANONY, 0 + "");
        }
    }

    //用户唯一标识符
    public static void saveToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }

    public static String getToken() {
        return getString(KEY_USER_TOKEN);
    }


    //用户第一次注册昵称
    public static void saveUserName(String name) {
        saveString(KEY_USER_NAME, name);
    }

    public static String getUserName() {
        return getString(KEY_USER_NAME);
    }


    //用户第一次注册头像
    public static void saveUserPhoto(String photo) {
        saveString(KEY_USER_PHOTO, photo);
    }

    public static String getUserPhoto() {
        return getString(KEY_USER_PHOTO);
    }



    //吉祥物头像
    public static void saveMascot(String photo) {
        saveString(KEY_USER_MASCOT, photo);
    }

    public static String getMascot() {
        return getString(KEY_USER_MASCOT);
    }




    //融云token
    public static void saveRcToken(String RcToken) {
        saveString(KEY_RC_TOKEN, RcToken);
    }

    public static String getRcToken() {
        return getString(KEY_RC_TOKEN);
    }




    //用户手机号
    public static void savePhone(String phone) {
        saveString(KEY_USER_PHONE, phone);
    }

    public static String getPhone() {
        return getString(KEY_USER_PHONE);
    }


    //用户ID
    public static void saveUserId(String userid) {
        saveString(KEY_USER_ID, userid);
    }

    public static String getUserId() {
        return getString(KEY_USER_ID);
    }


    //用户随机密码
    public static void savePassword(String password) {
        saveString(KEY_USER_PASSWORD, password);
    }

    public static String getPassword() {
        return getString(KEY_USER_PASSWORD);
    }

    public static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }


    /**
     * 清除某个内容
     */
    public static void removeSF(String key) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(key).apply();
    }


    /**
     * 退出登录清除用户数据
     */
    public static void clearUserLoginData() {
        String phone = getPhone();
        String password=getPassword();
        clear();
        savePhone(phone);
        savePassword(password);
    }



    /**
     * 清空所有数据
     */
    public static void clear() {
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
