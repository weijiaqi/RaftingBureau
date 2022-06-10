package com.drifting.bureau.mvp.presenter;

import android.app.Application;

import com.drifting.bureau.mvp.model.entity.MysteryboxEntity;
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

import com.drifting.bureau.mvp.contract.FeedBackContract;
import com.jess.arms.utils.RxLifecycleUtils;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/30 09:46
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class FeedBackPresenter extends BasePresenter<FeedBackContract.Model, FeedBackContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FeedBackPresenter(FeedBackContract.Model model, FeedBackContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 意见反馈
     */
    public void feedback(String content, String phone) {
        mModel.feedback(content, phone).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onFeedBackAddSuccess();
                            }else {
                                mRootView.showMessage(baseEntity.getMsg());
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