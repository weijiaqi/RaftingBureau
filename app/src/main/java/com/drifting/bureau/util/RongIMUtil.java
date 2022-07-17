package com.drifting.bureau.util;


import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.drifting.bureau.app.RBConstant;
import com.drifting.bureau.app.application.RBureauApplication;
import com.drifting.bureau.storageinfo.Preferences;

import io.rong.imkit.GlideKitImageEngine;
import io.rong.imkit.RongIM;
import io.rong.imkit.config.RongConfigCenter;
import io.rong.imkit.userinfo.RongUserInfoManager;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

public class RongIMUtil {

    private static final RongIMUtil instance = new RongIMUtil();

    public static RongIMUtil getInstance() {
        return instance;
    }

    public void init() {
        // 融云 App Key
        RongIM.init(RBureauApplication.getInstance(), RBConstant.RONGIM_APPKEY,true);
        RongConfigCenter.featureConfig().setKitImageEngine(new GlideKitImageEngine() {
            @Override
            public void loadConversationListPortrait(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, Conversation conversation) {
                Glide.with(imageView).load(url)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(imageView);
            }

            @Override
            public void loadConversationPortrait(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, Message message) {
                Glide.with(imageView).load(url)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(imageView);
            }
        });
    }


    public void connect(String token,ConnectListener connectListener){
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String userId) {
                Log.e("RongIM","融云连接成功");
                UserInfo userInfo = new UserInfo(userId, Preferences.getUserName(), Uri.parse(Preferences.getUserPhoto()));
                RongUserInfoManager.getInstance().refreshUserInfoCache(userInfo);
                if (connectListener!=null){
                    connectListener.onConnectSuccess();
                }
            }

            @Override
            public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {
                if (connectListener!=null){
                    connectListener.onConnectError();
                }
            }
            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {
            }
        });

    }


    public interface ConnectListener {
        void onConnectSuccess();

        void onConnectError();
    }
}
