package com.drifting.bureau.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;


import com.drifting.bureau.mvp.contract.SignLoginContract;
import com.drifting.bureau.mvp.model.entity.LoginEntity;
import com.drifting.bureau.util.ToastUtil;
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

@ActivityScope
public class SignLoginPresenter extends BasePresenter<SignLoginContract.Model, SignLoginContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SignLoginPresenter(SignLoginContract.Model model, SignLoginContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 密码登录
     */
    public void passwordlogin(String mobile, String password) {
        mModel.passwordlogin(mobile, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<LoginEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<LoginEntity> entity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (entity.getCode() == 200) {
                                mRootView.loginSuccess(entity.getData());
                            } else {
                                if (!TextUtils.isEmpty(entity.getMsg())) {
                                    mRootView.showMessage(entity.getMsg());
                                }
                            }
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
