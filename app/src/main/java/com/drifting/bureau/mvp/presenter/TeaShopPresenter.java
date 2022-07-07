package com.drifting.bureau.mvp.presenter;
import android.app.Activity;
import android.app.Application;
import android.location.Location;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;
import com.drifting.bureau.mvp.model.entity.TeaShopEntity;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.util.LocationUtil;
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
import com.drifting.bureau.mvp.contract.TeaShopContract;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

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
public class TeaShopPresenter extends BasePresenter<TeaShopContract.Model, TeaShopContract.View>{
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TeaShopPresenter (TeaShopContract.Model model, TeaShopContract.View rootView) {
        super(model, rootView);
    }




    /**
     * 获取经纬度
     */

    public void getLocation(Activity activity) {
        PermissionUtil.launchLocation(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                LocationUtil.getCurrentLocation(new LocationUtil.LocationCallBack() {
                    @Override
                    public void onSuccess(Location location) {
                        mRootView.onLocation(location.getLongitude() + "", location.getLatitude() + "");
                    }

                    @Override
                    public void onFail(String msg) {
                        mRootView.onLocation("",  "");
                    }
                });

            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.onLocation("",  "");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.onLocation("",  "");
            }
        }, new RxPermissions((FragmentActivity) activity), mErrorHandler);

    }



    /**
     * 实体门店
     */
    public void nearby( String name, int page, int limit,String lng,String lat, boolean loadType) {
        if (mRootView != null) {
            mRootView.onloadStart();
        }
        mModel.nearby(name,page, limit,lng,lat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<TeaShopEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<TeaShopEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                if (baseEntity.getData().getList() == null || baseEntity.getData().getList().size() == 0) {
                                    mRootView.loadState(ViewUtil.NOT_DATA);
                                    mRootView.loadFinish(loadType, true);
                                } else {
                                    mRootView.loadState(ViewUtil.HAS_DATA);
                                    mRootView.loadFinish(loadType, false);
                                }
                                mRootView.onBusinessSuccess(baseEntity.getData(), loadType);
                            } else {
                                mRootView.loadState(ViewUtil.NOT_SERVER);
                                mRootView.loadFinish(loadType, false);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("错误信息", t.getMessage().toString() + "");
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