package com.drifting.bureau.util.request;


import android.util.SparseArray;


import com.drifting.bureau.app.api.ApiProxy;
import com.drifting.bureau.mvp.model.entity.BoxOpenEntity;
import com.drifting.bureau.mvp.model.entity.MySpaceStationEntity;
import com.drifting.bureau.mvp.model.entity.MyTreasuryEntity;
import com.drifting.bureau.mvp.model.entity.PlanetLocationEntity;
import com.drifting.bureau.mvp.model.entity.PlatformTimesEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.model.entity.WriteOffInfoEntity;
import com.drifting.bureau.util.callback.BaseDataCallBack;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.base.BaseObserver;
import com.jess.arms.utils.RxLifecycleUtils;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
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
     * 查看漂流-玩家信息
     *
     * @param user_id
     * @param callBack
     */
    public void userplayer(String user_id, BaseDataCallBack<UserInfoEntity> callBack) {
        ApiProxy.getApiService().userplayer(user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<UserInfoEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<UserInfoEntity> entity) {
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
     * 开启盲盒（我的盲盒中用）
     *
     * @param box_id
     * @param callBack
     */
    public void mysteryboxopen(String box_id, BaseDataCallBack<BoxOpenEntity> callBack) {
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
     * 转赠盲盒
     *
     * @param box_id
     * @param callBack
     */
    public void mysteryboxtransfer(String box_id,String mobile, BaseDataCallBack callBack) {
        ApiProxy.getApiService().transfer(box_id,mobile)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity entity) {
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
     * 转赠物品（含空间站）
     *
     * @param object_id
     * @param callBack
     */
    public void mysteryboxtransfer(int object_id,String mobile, BaseDataCallBack callBack) {
        ApiProxy.getApiService().storagetransfer(object_id,mobile)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity entity) {
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
     * 丢入星空（首页弹窗、查看漂流页面可用）
     *
     * @param callBack
     */
    public void messagethrow(int message_id, BaseDataCallBack callBack) {
        ApiProxy.getApiService().messagethrow(message_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity entity) {
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
     * 添加好友（查看漂流）
     *
     * @param callBack
     */
    public void friendapply(String user_id, BaseDataCallBack callBack) {
        ApiProxy.getApiService().friendapply(user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity entity) {
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
     * 核销码信息（订单记录-核销）
     *
     * @param callBack
     */
    public void writeOffInfo(int order_sub_id, BaseDataCallBack<WriteOffInfoEntity> callBack) {
        ApiProxy.getApiService().writeOffInfo(order_sub_id+"")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<WriteOffInfoEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<WriteOffInfoEntity> entity) {
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
     * 使用物品（空间站）
     *
     * @param callBack
     */
    public void storageusing(int object_id,int object_num , BaseDataCallBack callBack) {
        ApiProxy.getApiService().storageusing(object_id,object_num)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity entity) {
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
     * 我的库藏(我的空间站)
     */
    public void storagemine(BaseDataCallBack<List<MyTreasuryEntity>> callBack) {
        ApiProxy.getApiService().storagemine()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<List<MyTreasuryEntity>>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<List<MyTreasuryEntity>> entity) {
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
     * 我的库藏(我的空间站)
     */
    public void levelcurrent(BaseDataCallBack<MySpaceStationEntity> callBack) {
        ApiProxy.getApiService().levelcurrent()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<MySpaceStationEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<MySpaceStationEntity> entity) {
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
     * 当前所在星球及答题状态
     */
    public void planetlocation(BaseDataCallBack<PlanetLocationEntity> callBack) {
        ApiProxy.getApiService().planetlocation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<PlanetLocationEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<PlanetLocationEntity> entity) {
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
     * 举报
     */
    public void reportcommit(int message_id,int report_type,String reason,   BaseDataCallBack callBack) {
        ApiProxy.getApiService().reportcommit(message_id,report_type,reason)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity entity) {
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
     * 免费漂流次数
     */
    public void platformtimes( int explore_id,BaseDataCallBack<PlatformTimesEntity> callBack) {
        ApiProxy.getApiService().platformtimes(explore_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<PlatformTimesEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<PlatformTimesEntity> entity) {
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
