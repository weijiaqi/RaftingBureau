package com.drifting.bureau.util.request;


import android.util.SparseArray;


import com.drifting.bureau.app.api.ApiProxy;
import com.drifting.bureau.mvp.model.entity.BoxOpenEntity;
import com.drifting.bureau.util.callback.BaseDataCallBack;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.base.BaseObserver;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * @Description:请求公共类工具类
 * @Author: wjq
 * @CreateDate: 2021/9/10 18:19
 */
public class RequestUtil {
    private static RequestUtil mRequestUtil;
    private static SparseArray<Disposable> mDisposables = new SparseArray<>();
    private static int mRequestCount = 0;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static RequestUtil create() {
        if (mRequestUtil == null) {
            synchronized (RequestUtil.class) {
                if (mRequestUtil == null) {
                    mRequestUtil = new RequestUtil();
                }
            }
        }
        return mRequestUtil;
    }

    /**
     * 开启盲盒（我的盲盒中用）
     *
     * @param box_id
     * @param callBack
     */
    public void mysteryboxopen(String box_id, BaseDataCallBack callBack) {
        ApiProxy.getApiService().mysteryboxopen(box_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<BoxOpenEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<BoxOpenEntity> entity) {
                        if (callBack != null) {
                            callBack.getData(entity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callBack != null) {
                            callBack.getData(null);
                        }
                    }
                });
    }


    /**
     * 取消订阅
     */
    public void disDispose() {
        CloseUtil.unSubscribeList(mDisposables);
    }


}