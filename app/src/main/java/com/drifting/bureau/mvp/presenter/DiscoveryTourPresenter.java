package com.drifting.bureau.mvp.presenter;
import android.app.Application;

import com.drifting.bureau.mvp.model.entity.CustomerEntity;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
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
import com.drifting.bureau.mvp.contract.DiscoveryTourContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/10 14:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class DiscoveryTourPresenter extends BasePresenter<DiscoveryTourContract.Model, DiscoveryTourContract.View>{
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private static boolean isExit;

    @Inject
    public DiscoveryTourPresenter (DiscoveryTourContract.Model model, DiscoveryTourContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 联系客服
     */
    public void getCustomerList() {
        mModel.customerlist().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<List<CustomerEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<List<CustomerEntity>> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.customerSuccess(baseEntity.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }
                });
    }




    /**
     * 飘来新消息（话题）
     */
    public void  getMessage() {
        mModel.messagereceive().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<MessageReceiveEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<MessageReceiveEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onMessageReceiveSuccess(baseEntity.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }
                });
    }


    /**
     * 调用双击退出函数
     */
    public void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true;
            ToastUtil.showToast("再按一次退出");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            mRootView.finishSuccess();
        }
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