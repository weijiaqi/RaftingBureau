package com.drifting.bureau.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.drifting.bureau.mvp.model.entity.LoginEntity;
import com.drifting.bureau.util.ToastUtil;
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

import com.drifting.bureau.mvp.contract.LoginContract;
import com.jess.arms.utils.RxLifecycleUtils;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/09 13:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    /**
     * @description 获取验证码
     */
    public void getCode(String mobile, int type, int status) {
        mModel.getCode(mobile, type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.getCodeOk();
                            } else {
                                mRootView.loginFail(baseEntity.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (mRootView != null) {
                            mRootView.onNetError(status);
                        }
                    }
                });
    }


    /**
     * 登录
     */
    public void login(String mobile, String code, int status) {
        mModel.login(mobile, code).subscribeOn(Schedulers.io())
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
                                mRootView.loginFail(entity.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (mRootView != null) {
                            mRootView.onNetError(status);
                        }
                    }
                });
    }


    /**
     * 注册（含昵称设置）
     */
    public void register(String mobile, String name, int status) {
        mModel.register(mobile, name).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<LoginEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<LoginEntity> entity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (entity.getCode() == 200) {
                                mRootView.registerSuccess(entity.getData());
                            } else {
                                mRootView.loginFail(entity.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (mRootView != null) {
                            mRootView.onNetError(status);
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