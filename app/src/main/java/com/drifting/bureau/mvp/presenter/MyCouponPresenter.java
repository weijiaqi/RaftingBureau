package com.drifting.bureau.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.drifting.bureau.mvp.contract.MyCouponContract;
import com.drifting.bureau.mvp.model.entity.CouponsMineEntity;
import com.drifting.bureau.util.ViewUtil;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/10/14 11:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MyCouponPresenter extends BasePresenter<MyCouponContract.Model, MyCouponContract.View>{
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MyCouponPresenter (MyCouponContract.Model model, MyCouponContract.View rootView) {
        super(model, rootView);
    }


    /**
     *卡券记录
     */
    public void openboxlist(int page, int limit,int status,String scene, boolean loadType) {
        if (mRootView != null) {
            mRootView.onloadStart();
        }
        mModel.couponsmine(page, limit,status,scene)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CouponsMineEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CouponsMineEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                if (baseEntity.getData().getList() == null || baseEntity.getData().getList().size() == 0) {
                                    mRootView.loadState(ViewUtil.NOT_DATA);
                                    mRootView.loadFinish(loadType, true);
                                } else {
                                    mRootView.loadState(ViewUtil.HAS_DATA);
                                    mRootView.loadFinish(loadType, false);
                                }
                                mRootView.onCouponsMineSuccess(baseEntity.getData(), loadType);
                            } else {
                                mRootView.loadState(ViewUtil.NOT_SERVER);
                                mRootView.loadFinish(loadType, false);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("错误信息", t.getMessage().toString() + "");
                        mRootView.loadState(ViewUtil.NOT_SERVER);
                        t.printStackTrace();
                    }
                });
    }




    /**
     *卡券记录
     */
    public void listForScene(int page, int limit,int status,String scene, boolean loadType) {
        if (mRootView != null) {
            mRootView.onloadStart();
        }
        mModel.listForScene(page, limit,status,scene)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CouponsMineEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CouponsMineEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                if (baseEntity.getData().getList() == null || baseEntity.getData().getList().size() == 0) {
                                    mRootView.loadState(ViewUtil.NOT_DATA);
                                    mRootView.loadFinish(loadType, true);
                                } else {
                                    mRootView.loadState(ViewUtil.HAS_DATA);
                                    mRootView.loadFinish(loadType, false);
                                }
                                mRootView.onCouponsMineSuccess(baseEntity.getData(), loadType);
                            } else {
                                mRootView.loadState(ViewUtil.NOT_SERVER);
                                mRootView.loadFinish(loadType, false);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("错误信息", t.getMessage().toString() + "");
                        mRootView.loadState(ViewUtil.NOT_SERVER);
                        t.printStackTrace();
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