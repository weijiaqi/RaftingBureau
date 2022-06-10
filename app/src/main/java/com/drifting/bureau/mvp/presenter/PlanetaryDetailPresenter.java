package com.drifting.bureau.mvp.presenter;

import android.app.Application;

import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.PlanetaryDetailEntity;
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

import com.drifting.bureau.mvp.contract.PlanetaryDetailContract;
import com.jess.arms.utils.RxLifecycleUtils;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/06/01 11:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class PlanetaryDetailPresenter extends BasePresenter<PlanetaryDetailContract.Model, PlanetaryDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PlanetaryDetailPresenter(PlanetaryDetailContract.Model model, PlanetaryDetailContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 星球详情
     */
    public void planetdetails(int pl_id) {
        mModel.planetdetails(pl_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<PlanetaryDetailEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<PlanetaryDetailEntity> entity) {
                        if (mRootView != null) {
                            if (entity.getCode() == 200) {
                                mRootView.onPlanetaryDetailSuccess(entity.getData());
                            } else {
                              mRootView.showMessage(entity.getMsg());
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

    public void showMessage(String content) {
        ToastUtil.showToast(content);
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