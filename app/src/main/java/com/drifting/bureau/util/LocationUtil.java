package com.drifting.bureau.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;

import com.drifting.bureau.app.application.RBureauApplication;

import timber.log.Timber;

/**
 * 获取经纬度、位置工具类
 */
public class LocationUtil {

    public static void getCurrentLocation(LocationCallBack locationCallBack) {
        if (locationCallBack == null) {
            return;
        }
        if (RBureauApplication.getContext() == null) {
            locationCallBack.onFail("请确保传入的参数context不为null");
        }
        //如果系统版本号在23及其以上则检查权限
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(RBureauApplication.getContext(), Manifest.permission_group.LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationCallBack.onFail("请确保已经获取定位权限");
        }

        //获取LocationManager对象
        LocationManager locationM = (LocationManager) RBureauApplication.getContext().getSystemService(Context.LOCATION_SERVICE);
        //实例化MyLocationListener
        MyLocationListener locationListener = new MyLocationListener(locationM, locationCallBack);
        //配置Criteria耗电低
        Criteria cri = new Criteria();
        cri.setPowerRequirement(Criteria.POWER_LOW);
        // 获取可用的provider,第二个参数标识 provider是否可用.
        String bestProvider = locationM.getBestProvider(cri, true);

        if (!TextUtils.isEmpty(bestProvider)) {
            Timber.e("bestProvider = " + bestProvider + "可用");
            locationM.requestLocationUpdates(bestProvider, 0, 0, locationListener);
        } else if (locationM.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Timber.e(LocationManager.NETWORK_PROVIDER + "可用");
            locationM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } else if (locationM.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Timber.e(LocationManager.GPS_PROVIDER + "可用");
            locationM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            //定位不可用，提示打开GPS
            Timber.e("定位不可用，提示打开GPS");
            locationCallBack.onFail("无可用的定位方式，请打开GPS");

        }
    }

    /**
     * LocationListener 的实现类
     */
    private static class MyLocationListener implements LocationListener {
        private LocationManager mLocationManager;
        private LocationCallBack mLocationCallBack;

        public MyLocationListener(LocationManager locationManager, LocationCallBack locationCallBack) {
            this.mLocationManager = locationManager;
            this.mLocationCallBack = locationCallBack;
        }

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                if (mLocationCallBack != null) {
                    mLocationCallBack.onSuccess(location);
                }
                if (mLocationManager != null) {
                    mLocationManager.removeUpdates(this);
                }
            } else {
                if (mLocationCallBack != null) {
                    mLocationCallBack.onFail("location == null");
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    /**
     * 定位结果回调
     */
    public interface LocationCallBack {
        /**
         * 定位成功
         *
         * @param location
         */
        void onSuccess(Location location);

        /**
         * 定位失败
         *
         * @param msg
         */
        void onFail(String msg);
    }


}

