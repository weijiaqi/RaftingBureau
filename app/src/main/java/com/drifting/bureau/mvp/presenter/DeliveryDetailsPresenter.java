package com.drifting.bureau.mvp.presenter;

import android.app.Application;

import com.drifting.bureau.mvp.model.entity.DeliveryDetailsEntity;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
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

import com.drifting.bureau.mvp.contract.DeliveryDetailsContract;
import com.jess.arms.utils.RxLifecycleUtils;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/19 09:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class DeliveryDetailsPresenter extends BasePresenter<DeliveryDetailsContract.Model, DeliveryDetailsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public DeliveryDetailsPresenter(DeliveryDetailsContract.Model model, DeliveryDetailsContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 传递详情
     */
    public void pathdetails(int message_id, int page, int limit, boolean loadType) {
        if (mRootView != null) {
            mRootView.onloadStart();
        }
        mModel.pathdetails(message_id, page, limit).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<DeliveryDetailsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<DeliveryDetailsEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                if (baseEntity.getData()== null ) {
                                    mRootView.loadState(ViewUtil.NOT_DATA);
                                    mRootView.loadFinish(loadType, true);
                                } else {
                                    mRootView.loadState(ViewUtil.HAS_DATA);
                                    mRootView.loadFinish(loadType, false);
                                }
                                mRootView.onPathDetailSuccess(baseEntity.getData(), loadType);
                            } else {
                                mRootView.loadState(ViewUtil.NOT_SERVER);
                                mRootView.loadFinish(loadType, false);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
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