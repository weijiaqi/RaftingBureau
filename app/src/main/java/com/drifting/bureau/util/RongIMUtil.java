package com.drifting.bureau.util;


import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.drifting.bureau.app.application.RBureauApplication;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;

import io.rong.imkit.GlideKitImageEngine;
import io.rong.imkit.RongIM;
import io.rong.imkit.config.RongConfigCenter;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

public class RongIMUtil {

    private static final RongIMUtil instance = new RongIMUtil();

    public static RongIMUtil getInstance() {
        return instance;
    }

    public void init() {
        // 融云 App Key
        RongIM.init(RBureauApplication.getInstance(), "sfci50a7sgf6i",true);
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

}
