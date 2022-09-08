package com.drifting.bureau.app.interceptor;

import android.text.TextUtils;

import com.drifting.bureau.R;
import com.drifting.bureau.app.application.RBureauApplication;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.AppUtil;
import com.drifting.bureau.util.StringUtil;
import com.jess.arms.utils.SystemUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @description  拦截器
 * @author 卫佳琪1
 * @time 15:06 15:06
 */

public class CommonParInterceptor implements Interceptor {
    //声明响应对象
    private Response response;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String method = originalRequest.method();
        if (TextUtils.equals(method,"POST")){
            RequestBody oldBody = originalRequest.body();
            originalRequest = originalRequest.newBuilder().removeHeader("User-Agent")//移除旧的
                    .addHeader("User-Agent", SystemUtil.getUserAgent(RBureauApplication.getContext()))//添加真正的头部
                    .addHeader("Token", StringUtil.formatNullString(Preferences.getToken()))
                    .addHeader("Version", StringUtil.formatNullString(AppUtil.getVerName(RBureauApplication.getContext()) + ""))
                    .addHeader("Sign",StringUtil.formatNullString(AppUtil.getSign(Preferences.getPhone())))
                    .addHeader("source","Android")
                    .addHeader("Accept", "application/json")
                    .post(oldBody)
                    .build();
            response = chain.proceed(originalRequest);
        }else {
            Request request = new Request.Builder()
                    .removeHeader("User-Agent").addHeader("User-Agent",
                            SystemUtil.getUserAgent(RBureauApplication.getContext()))
                    .addHeader("Token", StringUtil.formatNullString(Preferences.getToken()))
                    .addHeader("Version", StringUtil.formatNullString(AppUtil.getVerName(RBureauApplication.getContext()) + ""))
                    .addHeader("Sign",StringUtil.formatNullString(AppUtil.getSign(Preferences.getPhone())))
                    .addHeader("source","Android")
                    .url(originalRequest.url())
                    .build();
            response = chain.proceed(request);
        }
        return response;
    }


}
