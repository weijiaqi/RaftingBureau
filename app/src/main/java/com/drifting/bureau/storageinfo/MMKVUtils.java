package com.drifting.bureau.storageinfo;

import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.tencent.mmkv.MMKV;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description:  MMKV 跨进程同步数据  默认保存到手机文件中
 * @Author     : WeiJiaQI
 * @Time       : 2022/11/3 17:32
 */
public class MMKVUtils {
    private static final String KEY_AR_ANSWER = "ar_answer";
    private static final String KEY_USER_IS_ARMODEL = "isarmodel";//是否为AR模式

    private static MMKVUtils mKvUtil;
    private static MMKV smMKV;

    public static MMKVUtils getInstance() {
        if (mKvUtil == null) {
            synchronized (MMKVUtils.class) {
                if (mKvUtil == null) {
                    mKvUtil = new MMKVUtils();
                }
            }
        }
        if (smMKV == null) {
            smMKV = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null);
        }
        return mKvUtil;
    }




    /**
     * 将map集合转化为json数据保存在MMKV中
     *
     * @param
     * @param map map数据
     * @return 保存结果
     */
    public static  <K,V> void putHashMapData(Map<K,V> map){
        try {
            String json = new Gson().toJson(map);
            smMKV.encode(KEY_AR_ANSWER, json);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 读取本地MMKV数据、转换成map集合
     *
     * @return HashMap
     */
    public static HashMap<String , String> getHashMapData(){
        String localJson = smMKV.decodeString(KEY_AR_ANSWER);
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



    /**
     * @return true选择AR模式  false选择普通模式
     */
    public static boolean isARModel() {
        return !(decodeString(KEY_USER_IS_ARMODEL) == null || "0".equals(decodeString(KEY_USER_IS_ARMODEL))) && "1".equals(decodeString(KEY_USER_IS_ARMODEL));
    }

    public static void setARModel(boolean isARModel) {
        if (isARModel) {
            encode(KEY_USER_IS_ARMODEL, 1 + "");
        } else {
            encode(KEY_USER_IS_ARMODEL, 0 + "");
        }
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public static void encode(String key, Object object) {
        if (object instanceof String) {
            smMKV.encode(key, (String) object);
        } else if (object instanceof Integer) {
            smMKV.encode(key, (Integer) object);
        } else if (object instanceof Boolean) {
            smMKV.encode(key, (Boolean) object);
        } else if (object instanceof Float) {
            smMKV.encode(key, (Float) object);
        } else if (object instanceof Long) {
            smMKV.encode(key, (Long) object);
        } else if (object instanceof Double) {
            smMKV.encode(key, (Double) object);
        } else if (object instanceof byte[]) {
            smMKV.encode(key, (byte[]) object);
        } else {
            smMKV.encode(key, object.toString());
        }
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public Integer decodeInt(String key) {
        return smMKV.decodeInt(key, 0);
    }

    public Double decodeDouble(String key) {
        return smMKV.decodeDouble(key, 0.00);
    }

    public Long decodeLong(String key) {
        return smMKV.decodeLong(key, 0L);
    }

    public Boolean decodeBoolean(String key) {
        return smMKV.decodeBool(key, false);
    }

    public Float decodeFloat(String key) {
        return smMKV.decodeFloat(key, 0F);
    }

    public byte[] decodeBytes(String key) {
        return smMKV.decodeBytes(key);
    }

    public static String decodeString(String key) {
        return smMKV.decodeString(key, "");
    }

    public Set<String> decodeStringSet(String key) {
        return smMKV.decodeStringSet(key, Collections.<String>emptySet());
    }

    public Parcelable decodeParcelable(String key) {
        return smMKV.decodeParcelable(key, null);
    }




    //移除某个key对
    public void removeValueForKey(String key) {
        smMKV.removeValueForKey(key);
    }

    // 同时移除多个key对
    public void removeValuesForKeys(String[] strings) {
        smMKV.removeValuesForKeys(strings);
    }

    //清除所有key
    public void clearAll() {
        smMKV.clearAll();
    }


}
