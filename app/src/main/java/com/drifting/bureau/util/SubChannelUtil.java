package com.drifting.bureau.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * User: lzy
 * Date: 2020/3/12
 */
public class SubChannelUtil {
    public static String channel;
    public static String sub_channel;
    private static Map<String, String> configMap;

    private static final String TAG = "SubChannelUtil";
    private static final String CHANNEL_KEY = "channel";
    private static final String SUB_CHANNEL_KEY = "subchannel";

    public static String getChannel(Context context) {
        if (TextUtils.isEmpty(channel)) {
            getMap(context);
            if (configMap != null) {
                channel = configMap.get(CHANNEL_KEY);
            }
        }
        return channel;
    }

    public static String getSubChannel(Context context) {
        if (TextUtils.isEmpty(sub_channel)) {
            getMap(context);
            if (configMap != null) {
                sub_channel = configMap.get(SUB_CHANNEL_KEY);
            }
        }
        return sub_channel;
    }

    private static void getMap(Context context) {
        if (configMap == null || configMap.size() == 0) {
            configMap = getChannelFromApk(context);
            if (configMap == null || configMap.size() == 0) {
                configMap = getAssetPropConfig(context, "channel_config.properties");
            }
        }
    }


    public static Map<String, String> getAssetPropConfig(Context context, String fileName) {
        Map<String, String> map = null;
        Properties assetProperties = new Properties();
        try {
            assetProperties.load(context.getAssets().open(fileName));

            map = new HashMap<String, String>((Map) assetProperties);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
//            context.getAssets().close();
            return map;
        }
    }

    /**
     * 从apk中获取版本信息
     *
     * @param context
     * @return
     */
    private static Map<String, String> getChannelFromApk(Context context) {
        Map<String, String> channelParams = new HashMap<>();
        //从apk包中获取
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;

        //默认放在meta-inf/里， 所以需要再拼接一下
        String filename = "META-INF/channel_config.properties";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();

                if (entryName.startsWith(filename)) {
                    Log.e(TAG, "channel_config.properties 文件存在");
                    if (entry.getSize() > 0) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(zipfile.getInputStream(entry)));
                        String line;
                        while ((line = br.readLine()) != null) {
                            Log.e(TAG, "channel_config.properties   : " + line);
                            String decrypt = line;
                            if (!TextUtils.isEmpty(decrypt)) {
                                String[] params = decrypt.split(";");
                                for (String str : params) {
                                    Log.e(TAG, "channel_config.properties   参数 : " + str);
                                    String[] strings = str.split("=");
                                    if (strings.length == 2) {
                                        channelParams.put(strings[0], strings[1]);
                                    }
                                }
                            }
                        }
                        br.close();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return channelParams;
    }

}
