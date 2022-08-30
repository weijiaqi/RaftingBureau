package com.drifting.bureau.util.request;


import android.util.SparseArray;

import com.drifting.bureau.app.api.ApiProxy;
import com.drifting.bureau.mvp.model.entity.AnnouncementEntity;
import com.drifting.bureau.mvp.model.entity.BoxOpenEntity;
import com.drifting.bureau.mvp.model.entity.DidAttendEntity;
import com.drifting.bureau.mvp.model.entity.FriendEntity;
import com.drifting.bureau.mvp.model.entity.FriendInfoEntity;
import com.drifting.bureau.mvp.model.entity.MySpaceStationEntity;
import com.drifting.bureau.mvp.model.entity.MyTreasuryEntity;
import com.drifting.bureau.mvp.model.entity.PlanetArEntity;
import com.drifting.bureau.mvp.model.entity.PlanetLocationEntity;
import com.drifting.bureau.mvp.model.entity.PlatformTimesEntity;
import com.drifting.bureau.mvp.model.entity.StarUpIndexEntity;
import com.drifting.bureau.mvp.model.entity.SysmessageEntity;
import com.drifting.bureau.mvp.model.entity.TopicTagsEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.model.entity.VersionUpdateEntity;
import com.drifting.bureau.mvp.model.entity.WriteOffInfoEntity;
import com.drifting.bureau.util.callback.BaseDataCallBack;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.base.BaseObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;

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
     * @description 版本更新
     */
    public void checkVersion(BaseDataCallBack<VersionUpdateEntity> callBack) {
        ApiProxy.getApiService().checkVersion()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<VersionUpdateEntity>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<VersionUpdateEntity> entity) {
                        callBack.getData(entity);
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
    public void mysteryboxtransfer(String box_id, String mobile, BaseDataCallBack callBack) {
        ApiProxy.getApiService().transfer(box_id, mobile)
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
    public void mysteryboxtransfer(int object_id, String mobile, BaseDataCallBack callBack) {
        ApiProxy.getApiService().storagetransfer(object_id, mobile)
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
        ApiProxy.getApiService().writeOffInfo(order_sub_id + "")
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
    public void storageusing(int object_id, int object_num, BaseDataCallBack callBack) {
        ApiProxy.getApiService().storageusing(object_id, object_num)
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
     * AR  公告
     */
    public void latest(BaseDataCallBack<List<AnnouncementEntity>> callBack) {
        ApiProxy.getApiService().latest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<List<AnnouncementEntity>>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<List<AnnouncementEntity>> entity) {
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
    public void reportcommit(int message_id, int comment_id, int report_type, String reason, BaseDataCallBack callBack) {
        ApiProxy.getApiService().reportcommit(message_id, comment_id, report_type, reason)
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
    public void platformtimes(int explore_id, BaseDataCallBack<PlatformTimesEntity> callBack) {
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
     * 未读消息数（消息中心）
     */
    public void unread(BaseDataCallBack<SysmessageEntity> callBack) {
        ApiProxy.getApiService().unread()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<SysmessageEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<SysmessageEntity> entity) {
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
     * 话题标签
     */
    public void tags(BaseDataCallBack<List<TopicTagsEntity>> callBack) {
        ApiProxy.getApiService().tagslist()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<List<TopicTagsEntity>>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<List<TopicTagsEntity>> entity) {
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
     * 标记为已读（消息中心）
     */
    public void markread(int sys_msg_id, BaseDataCallBack callBack) {
        ApiProxy.getApiService().markread(sys_msg_id)
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
     * 同意或拒绝好友申请（消息中心）
     */
    public void agreeRefuse(int apply_id, int status, BaseDataCallBack callBack) {
        ApiProxy.getApiService().agreeRefuse(apply_id, status)
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
     * 删除好友（消息中心）
     */
    public void frienddelete(int user_id, BaseDataCallBack callBack) {
        ApiProxy.getApiService().frienddelete(user_id)
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
     * 好友相关信息（头像、昵称）
     */
    public void friendinfo(String user_id, BaseDataCallBack<FriendInfoEntity> callBack) {
        ApiProxy.getApiService().friendinfo(user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<FriendInfoEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<FriendInfoEntity> entity) {
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
     * 是否是好友
     */
    public void isFriend(String user_id, BaseDataCallBack<FriendEntity> callBack) {
        ApiProxy.getApiService().isFriend(user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<FriendEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<FriendEntity> entity) {
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
     * 获取AR Url (登录前)
     */
    public void planetar(BaseDataCallBack<PlanetArEntity> callBack) {
        ApiProxy.getApiService().planetar()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<PlanetArEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<PlanetArEntity> entity) {
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
     * 青年创业营
     */
    public void startup(BaseDataCallBack<StarUpIndexEntity> callBack) {
        ApiProxy.getApiService().startup()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<StarUpIndexEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<StarUpIndexEntity> entity) {
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
     * 是否参与过漂流
     */
    public void didAttend(BaseDataCallBack<DidAttendEntity> callBack) {
        ApiProxy.getApiService().didAttend()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<DidAttendEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<DidAttendEntity> entity) {
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
     * 注销
     */
    public void unregister(BaseDataCallBack callBack) {
        ApiProxy.getApiService().unregister()
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
     * 取消订阅
     */
    public void disDispose() {
        CloseUtil.unSubscribeList(mDisposables);
    }


}
