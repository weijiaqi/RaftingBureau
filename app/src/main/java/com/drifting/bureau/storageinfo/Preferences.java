package com.drifting.bureau.storageinfo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

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
    private static final String KEY_USER_CITY= "user_city";
    private static final String KEY_USER_IS_ANONY = "isanony";//是否为匿名状态
    private static final String KEY_USER_IS_ARMODEL = "isarmodel";//是否为AR模式
    private static final String KEY_USER_IS_DIDATTEND = "isdidAttend";//是否参与过漂流
    public static final String KEY_IS_TEST = "isTest"; //是不是测试环境

    /**
     * 初始化构建
     */
    public static void initialize(Application app) {
        mContext = app.getApplicationContext();
    }

    static SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }


    public static void saveTestState(boolean isTest) {
        saveBoolean(KEY_IS_TEST, isTest);
    }

    public static boolean isTest() {
        return getBoolean(KEY_IS_TEST);
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


    /**
     * @return true选择AR模式  false选择普通模式
     */
    public static boolean isARModel() {
        return !(getString(KEY_USER_IS_ARMODEL) == null || "0".equals(getString(KEY_USER_IS_ARMODEL))) && "1".equals(getString(KEY_USER_IS_ARMODEL));
    }

    public static void setARModel(boolean isARModel) {
        if (isARModel) {
            saveString(KEY_USER_IS_ARMODEL, 1 + "");
        } else {
            saveString(KEY_USER_IS_ARMODEL, 0 + "");
        }
    }


    /**
     * @return 是否参与过漂流
     */
    public static boolean isDidAttend() {
        return !(getString(KEY_USER_IS_DIDATTEND) == null || "0".equals(getString(KEY_USER_IS_DIDATTEND))) && "1".equals(getString(KEY_USER_IS_DIDATTEND));
    }

    public static void setDidAttend(boolean isARModel) {
        if (isARModel) {
            saveString(KEY_USER_IS_DIDATTEND, 1 + "");
        } else {
            saveString(KEY_USER_IS_DIDATTEND, 0 + "");
        }
    }




    //用户唯一标识符
    public static void saveToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }

    public static String getToken() {
        return getString(KEY_USER_TOKEN);
    }


    //用户所在城市
    public static void saveCity(String lat) {
        saveString(KEY_USER_CITY, lat);
    }

    public static String getCity() {
        return getString(KEY_USER_CITY);
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


    public static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
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
        boolean isTest = isTest();//测试环境
        String phone = getPhone();
        String password = getPassword();
        clear();
        savePhone(phone);
        savePassword(password);
        saveTestState(isTest);//测试环境
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
