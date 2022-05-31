package com.drifting.bureau.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.drifting.bureau.mvp.model.entity.MyBlindBoxEntity;
import com.drifting.bureau.mvp.model.entity.OrderDetailEntity;
import com.drifting.bureau.mvp.model.entity.OrderOneEntity;
import com.drifting.bureau.mvp.model.entity.SpaceInfoEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.util.ViewUtil;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.drifting.bureau.mvp.contract.MySpaceStationContract;
import com.jess.arms.utils.RxLifecycleUtils;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/24 12:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MySpaceStationPresenter extends BasePresenter<MySpaceStationContract.Model, MySpaceStationContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MySpaceStationPresenter(MySpaceStationContract.Model model, MySpaceStationContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 空间站信息（我的空间站）
     */

    public void spaceinfo(String user_id) {
        mModel.spaceinfo(user_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<SpaceInfoEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<SpaceInfoEntity> baseEntity) {
                        if (mRootView != null) {
                            mRootView.onSpcaeInfoSuccess(baseEntity.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (mRootView != null) {
                            mRootView.onNetError();
                        }
                    }
                });
    }


    /**
     * 漂流新订单（我的空间站）
     */

    public void orderone() {
        mModel.orderone().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<OrderOneEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<OrderOneEntity> baseEntity) {
                        if (mRootView != null) {
                            mRootView.onOrderOneSuccess(baseEntity.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (mRootView != null) {
                            mRootView.onNetError();
                        }
                    }
                });
    }


    /**
     * 新订单的详情（空间站查看漂来的订单）
     */

    public void orderdetail(int space_order_id) {
        mModel.orderdetail(space_order_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<OrderDetailEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<OrderDetailEntity> baseEntity) {
                        if (mRootView != null) {
                            mRootView.onOrderDetailSuccess(baseEntity.getData());
                        }
                    }
                    @Override
                    public void onError(Throwable t) {
                        if (mRootView != null) {
                            mRootView.onNetError();
                        }
                    }
                });
    }


    /**
     * 新订单的详情（空间站查看漂来的订单）
     */

    public void userplayer(String  user_id) {
        mModel.userplayer(user_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<UserInfoEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<UserInfoEntity> baseEntity) {
                        if (mRootView != null) {
                            mRootView.onUserInfoSuccess(baseEntity.getData());
                        }
                    }
                    @Override
                    public void onError(Throwable t) {
                        if (mRootView != null) {
                            mRootView.onNetError();
                        }
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}