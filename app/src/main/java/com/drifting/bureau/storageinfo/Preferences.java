package com.drifting.bureau.storageinfo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    private static final String KEY_USER_CITY = "user_city";

    private static final String KEY_AR_ANSWER = "ar_answer";

    private static final String KEY_USER_IS_ANONY = "isanony";//是否为匿名状态
    private static final String KEY_USER_IS_PRIVACY= "isprivacy";//是否同意隐私条款
    private static final String KEY_USER_IS_ARMODEL = "isarmodel";//是否为AR模式
    private static final String KEY_USER_IS_DIDATTEND = "isdidAttend";//是否参与过漂流
    public static final String KEY_IS_TEST = "isTest"; //是不是测试环境

    private static final String KEY_IS_ORDINARY = "is_ar_ordinary";// 普通引导
    private static final String KEY_IS_POST = "is_ar_post";// 普通发布引导
    private static final String KEY_IS_AR = "is_ar";// AR引导
    private static final String KEY_IS_AR_RIGHT_HAND = "is_ar_right_hand";// AR右手引导
    private static final String KEY_IS_BEAR_AR = "is_ar_bear";// AR小熊引导
    private static final String KEY_IS_AR_PLANET = "is_ar_planet";// AR星球答题引导
    private static final String KEY_IS_AR_AIRPLANE = "is_ar_airplane";// AR星球飞机引导

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
     * @return true已同意隐私条款 false退出应用
     */
    public static boolean isAgreePrivacy() {
        return !(getString(KEY_USER_IS_PRIVACY) == null || "0".equals(getString(KEY_USER_IS_PRIVACY))) && "1".equals(getString(KEY_USER_IS_PRIVACY));
    }

    public static void setAgreePrivacy(boolean isAgreePrivacy) {
        if (isAgreePrivacy) {
            saveString(KEY_USER_IS_PRIVACY, 1 + "");
        } else {
            saveString(KEY_USER_IS_PRIVACY, 0 + "");
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


    //普通版本引导
    public static boolean isOrdinaryGuide() {
        return !(getString(KEY_IS_ORDINARY) == null || "0".equals(getString(KEY_IS_ORDINARY))) && "1".equals(getString(KEY_IS_ORDINARY));
    }

    public static void setOrdinaryGuide(boolean isOrdinary) {
        if (isOrdinary) {
            saveString(KEY_IS_ORDINARY, 1 + "");
        } else {
            saveString(KEY_IS_ORDINARY, 0 + "");
        }
    }

    //普通版本发布漂流
    public static boolean isPostGuide() {
        return !(getString(KEY_IS_POST) == null || "0".equals(getString(KEY_IS_POST))) && "1".equals(getString(KEY_IS_POST));
    }

    public static void setPostGuide(boolean isOrdinary) {
        if (isOrdinary) {
            saveString(KEY_IS_POST, 1 + "");
        } else {
            saveString(KEY_IS_POST, 0 + "");
        }
    }


    //AR版本主页引导
    public static boolean isArGuide() {
        return !(getString(KEY_IS_AR) == null || "0".equals(getString(KEY_IS_AR))) && "1".equals(getString(KEY_IS_AR));
    }

    public static void setArGuide(boolean isAr) {
        if (isAr) {
            saveString(KEY_IS_AR, 1 + "");
        } else {
            saveString(KEY_IS_AR, 0 + "");
        }
    }


    //AR版本转盘右手引导
    public static boolean isArRightHandGuide() {
        return !(getString(KEY_IS_AR_RIGHT_HAND) == null || "0".equals(getString(KEY_IS_AR_RIGHT_HAND))) && "1".equals(getString(KEY_IS_AR_RIGHT_HAND));
    }

    public static void setArRightHandGuide(boolean isAr) {
        if (isAr) {
            saveString(KEY_IS_AR_RIGHT_HAND, 1 + "");
        } else {
            saveString(KEY_IS_AR_RIGHT_HAND, 0 + "");
        }
    }


    //AR版本小熊引导
    public static boolean isArBear() {
        return !(getString(KEY_IS_BEAR_AR) == null || "0".equals(getString(KEY_IS_BEAR_AR))) && "1".equals(getString(KEY_IS_BEAR_AR));
    }

    public static void setArBear(boolean isAr) {
        if (isAr) {
            saveString(KEY_IS_BEAR_AR, 1 + "");
        } else {
            saveString(KEY_IS_BEAR_AR, 0 + "");
        }
    }

    //AR版本星球飞机引导
    public static boolean isArAirplane() {
        return !(getString(KEY_IS_AR_AIRPLANE) == null || "0".equals(getString(KEY_IS_AR_AIRPLANE))) && "1".equals(getString(KEY_IS_AR_AIRPLANE));
    }

    public static void setArAirplane(boolean isAr) {
        if (isAr) {
            saveString(KEY_IS_AR_AIRPLANE, 1 + "");
        } else {
            saveString(KEY_IS_AR_AIRPLANE, 0 + "");
        }
    }

    //AR星球答题引导
    public static boolean isArPlanet() {
        return !(getString(KEY_IS_AR_PLANET) == null || "0".equals(getString(KEY_IS_AR_PLANET))) && "1".equals(getString(KEY_IS_AR_PLANET));
    }

    public static void setArPlanet(boolean isAr) {
        if (isAr) {
            saveString(KEY_IS_AR_PLANET, 1 + "");
        } else {
            saveString(KEY_IS_AR_PLANET, 0 + "");
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




    /**
     * 将map集合转化为json数据保存在sharePreferences中
     *
     * @param
     * @param map map数据
     * @return 保存结果
     */
    public static  <K,V> void putHashMapData(Map<K,V> map){
        try {
            String json = new Gson().toJson(map);
            saveString(KEY_AR_ANSWER,json);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 读取本地sharePreferences数据、转换成map集合
     *
     * @return HashMap
     */
    public static HashMap<String , String> getHashMapData(){
        String localJson = getString(KEY_AR_ANSWER);
        if (localJson==null ||localJson.equals("null")){
            return null;
        }

        HashMap<String , String> map = new HashMap<>();
        JsonObject object = JsonParser.parseString(localJson).getAsJsonObject();
        Set<Map.Entry<String , JsonElement>> entrySet = object.entrySet();
        for (Map.Entry<String , JsonElement> entry : entrySet) {
            String entryKey =entry.getKey();
            JsonPrimitive entryValue = (JsonPrimitive) entry.getValue();
            map.put(entryKey ,entryValue.getAsString());
        }

        return map;
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
        //是否同意隐私协议条款
        boolean isPrivacy=isAgreePrivacy();

        boolean isTest = isTest();//测试环境
        String phone = getPhone();
        String password = getPassword();
        //退出不重新加载引导
        boolean isOrdinaryGuide = isOrdinaryGuide();
        boolean isPostGuide = isPostGuide();
        boolean isArGuide = isArGuide();
        boolean isArRightHandGuide = isArRightHandGuide();
        boolean isArBear = isArBear();
        boolean isArAirplane = isArAirplane();
        boolean isArPlanet = isArPlanet();
        clear();
        setAgreePrivacy(isPrivacy);
        savePhone(phone);
        savePassword(password);
        saveTestState(isTest);//测试环境
        //退出不重新加载引导
        setOrdinaryGuide(isOrdinaryGuide);
        setPostGuide(isPostGuide);
        setArGuide(isArGuide);
        setArRightHandGuide(isArRightHandGuide);
        setArBear(isArBear);
        setArAirplane(isArAirplane);
        setArPlanet(isArPlanet);
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
