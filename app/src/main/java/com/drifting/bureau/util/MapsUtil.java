package com.drifting.bureau.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import com.hjq.toast.ToastUtils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MapsUtil {
    public static final String PN_GAODE_MAP = "com.autonavi.minimap";// 高德地图包名
    public static final String PN_BAIDU_MAP = "com.baidu.BaiduMap"; // 百度地图包名
    public static final String PN_TENCENT_MAP = "com.tencent.map"; // 腾讯地图包名

    public static double x_PI = 3.14159265358979324 * 3000.0 / 180.0;
    public static double PI = 3.1415926535897932384626;
    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;

    /**
     * 启动百度App进行导航
     *
     * @param address 目的地
     * @param lat     必填 纬度
     * @param lon     必填 经度
     */
    public static void goToBaiduActivity(Context context, String address, double lon, double lat) {
        double[] doubles = wgs84tobd09(lon, lat);
        //启动路径规划页面
        baiduMap(context, doubles[0], doubles[1]);
    }

    /**
     * 百度地图
     */
    public static void baiduMap(Context context, double lng, double lat) {
        if (isAppInstalled(context, PN_BAIDU_MAP)) {//传入指定应用包名
            Intent il = new Intent();
            il.setData(Uri.parse("baidumap://map/direction?destination=" + lat + "," + lng + "&mode=driving"));
            context.startActivity(il);
        } else {//未安装
            //market为路径，id为包名
            //显示手机上所有的market商店
            ToastUtil.showToast("您尚未安装百度地图");

            //显示手机上所有的market商店
            Uri uri = Uri.parse("market://details?id=" + PN_BAIDU_MAP);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }


    /**
     * 启动高德App进行导航
     *
     * @param lat 必填 纬度
     * @param lon 必填 经度
     */
    public static void goToGodeActivity(Context context, double lon, double lat) {
        double[] doubles = wgs84togcj02(lon, lat);
        //启动路径规划页面
        goToGaoDeMap(context, doubles[0], doubles[1]);
    }

    /**
     * 高德地图
     */
    public static void goToGaoDeMap(Context context, double lng, double lat) {
        if (isAvilible(context, PN_GAODE_MAP)) {//传入指定应用包名
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setPackage(PN_GAODE_MAP);
            try {
                intent = Intent.getIntent("amapuri://route/plan/?dlat=" + lat + "&dlon=" + lng + "&d&dev=0&t=0");
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {//未安装
            //market为路径，id为包名
            //显示手机上所有的market商店
            ToastUtils.show("您尚未安装高德地图");
            Uri uri = Uri.parse("market://details?id=" + PN_GAODE_MAP);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }


    /**
     * 启动腾讯App进行导航
     *
     * @param lat 必填 纬度
     * @param lon 必填 经度
     */
    public static void goToTencentActivity(Context context, double lon, double lat) {
        double[] doubles = wgs84togcj02(lon, lat);
        //启动路径规划页面
        goToTencentMap(context, null, doubles[0], doubles[1]);
    }


    /**
     * 腾讯地图
     */
    public static void goToTencentMap(Context context, String address, double lng, double lat) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //将功能Scheme以URI的方式传入data
        Uri uri = Uri.parse("qqmap://map/routeplan?type=drive&to=" + address + "&tocoord=" + lat + "," + lng);
        intent.setData(uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            //启动该页面即可
            context.startActivity(intent);
        } else {
            ToastUtils.show("您尚未安装腾讯地图");
            //显示手机上所有的market商店
            Uri uri1 = Uri.parse("market://details?id=" + PN_TENCENT_MAP);
            Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
            context.startActivity(intent1);
        }
    }


    private static boolean isAppInstalled(Context context, String packageName) {
        PackageInfo packageInfo = null;

        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }


    }

    /**
     * 检测应用是否安装
     */
    private static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();//获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名
        //从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     *
     * @param lat
     * @param lon
     */
    public static double[] gcj02_To_Bd09(double lat, double lon) {
        double x = lon, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002;
        double theta = Math.atan2(y, x) + 0.000003;
        double tempLat = z * Math.sin(theta) + 0.006;
        double tempLon = z * Math.cos(theta) + 0.0065;
        double[] gps = {tempLat, tempLon};
        return gps;
    }


    /**
     * WGS84 转换为 BD-09(百度)
     *
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    private static double[] wgs84tobd09(double lng, double lat) {
        //第一次转换
        double dlat = transformlat(lng - 105.0, lat - 35.0);
        double dlng = transformlng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;

        //第二次转换
        double z = Math.sqrt(mglng * mglng + mglat * mglat) + 0.00002 * Math.sin(mglat * x_PI);
        double theta = Math.atan2(mglat, mglng) + 0.000003 * Math.cos(mglng * x_PI);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;

        double[] gps = {bd_lng, bd_lat};
        return gps;
    }


    /**
     * WGS84转GCj02
     *
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    public static double[] wgs84togcj02(double lng, double lat) {
        double dlat = transformlat(lng - 105.0, lat - 35.0);
        double dlng = transformlng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        //Point point=new Point(mglng, mglat);
        // return point;
        double[] gps = {mglng, mglat};
        return gps;
    }


    private static double transformlat(double lng, double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformlng(double lng, double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }
}