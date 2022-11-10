package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.jess.arms.di.component.AppComponent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;

/**
 * @description   地图校园版
 * @author 卫佳琪1
 * @time 14:31 14:31
 */

public class DriftTrackCampusMapActivity  extends BaseManagerActivity {
    @BindView(R.id.mFrameLayout)
    FrameLayout mFrameLayout;

    // 地图View实例
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private UiSettings mUiSettings;
    private MapStatus.Builder builder;

    // 用于设置个性化地图的样式文件
    private static final String CUSTOM_FILE_NAME_GRAY = "school.sty";


    public static void start(Context context,boolean closePage) {
        Intent intent = new Intent(context, DriftTrackCampusMapActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_drift_track_campus_map;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initListener();
    }

    public void initListener() {
        mMapView = new MapView(this, new BaiduMapOptions());
        // 获取.sty文件路径
        String customStyleFilePath = getCustomStyleFilePath(this, CUSTOM_FILE_NAME_GRAY);
        // 设置个性化地图样式文件的路径和加载方式
        mMapView.setMapCustomStylePath(customStyleFilePath);
        // 动态设置个性化地图样式是否生效
        mMapView.setMapCustomStyleEnable(true);
        //通过设置enable为true或false 选择是否显示比例尺
        mMapView.showScaleControl(false);
        mMapView.showZoomControls(false);  //隐藏加减缩放按钮
        //删除logo
        mMapView.removeViewAt(1);
        mFrameLayout.addView(mMapView);
        mBaiduMap = mMapView.getMap();
        mUiSettings = mBaiduMap.getUiSettings();
        //通过设置enable为true或false 选择是否启用地图旋转功能
        mUiSettings.setRotateGesturesEnabled(false);
        //设置当前缩放等级
        builder = new MapStatus.Builder();
        builder.zoom(10.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //隐藏底图标注
        mBaiduMap.showMapPoi(false);
        //隐藏底部+-按钮
        mBaiduMap.setMyLocationEnabled(true);
        //设置缩放等级范围
        mBaiduMap.setMaxAndMinZoomLevel(13, 6);

    }


    /**
     * @description 获取assets下的文件
     */
    private String getCustomStyleFilePath(Context context, String customStyleFileName) {
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String parentPath = null;

        try {
            inputStream = context.getAssets().open("customConfigdir/" + customStyleFileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            parentPath = context.getFilesDir().getAbsolutePath();
            File customStyleFile = new File(parentPath + "/" + customStyleFileName);
            if (customStyleFile.exists()) {
                customStyleFile.delete();
            }
            customStyleFile.createNewFile();
            outputStream = new FileOutputStream(customStyleFile);
            outputStream.write(buffer);
        } catch (IOException e) {
            Log.e("CustomMapDemo", "Copy custom style file failed", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                Log.e("CustomMapDemo", "Close stream failed", e);
                return null;
            }
        }
        return parentPath + "/" + customStyleFileName;
    }
}
