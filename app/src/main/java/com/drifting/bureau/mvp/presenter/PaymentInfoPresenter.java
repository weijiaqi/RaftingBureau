package com.drifting.bureau.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.drifting.bureau.mvp.contract.PaymentInfoContract;
import com.drifting.bureau.mvp.model.entity.PayOrderEntity;
import com.drifting.bureau.mvp.model.entity.SandPayQueryEntity;
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
 * Created by MVPArmsTemplate on 2022/05/18 10:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class PaymentInfoPresenter extends BasePresenter<PaymentInfoContract.Model, PaymentInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PaymentInfoPresenter(PaymentInfoContract.Model model, PaymentInfoContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 支付订单
     */

    public void payOrder(String sn, String terminal) {
        mModel.payOrder(sn, terminal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<PayOrderEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<PayOrderEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.payOrderSuccess(baseEntity.getData());
                            }else {
                                mRootView.showMessage(baseEntity.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mRootView.onNetError();
                        t.printStackTrace();
                    }
                });
    }


    /**
     * 杉德支付主动查询
     *
     * @param sn
     */
    public void sandPayOrderQuery(String sn) {
        if (mRootView != null) {
            mRootView.showLoading();
        }
        mModel.sandPayQuery(sn).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<SandPayQueryEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<SandPayQueryEntity> baseEntity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (baseEntity.getCode() == 200) {
                                mRootView.sandPayQuerySuccess(baseEntity.getData());
                            } else {
                                if (!TextUtils.isEmpty(baseEntity.getMsg())) {
                                    mRootView.showMessage(baseEntity.getMsg());
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
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