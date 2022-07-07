package com.drifting.bureau.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.drifting.bureau.mvp.model.entity.CustomerEntity;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
import com.drifting.bureau.mvp.model.entity.PlanetEntity;
import com.drifting.bureau.mvp.ui.activity.index.VideoActivity;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.AppUtil;
import com.drifting.bureau.util.LocationUtil;
import com.drifting.bureau.util.MapsUtil;
import com.drifting.bureau.util.ToastUtil;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imkit.userinfo.RongUserInfoManager;
import io.rong.imkit.userinfo.UserDataProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.drifting.bureau.mvp.contract.DiscoveryTourContract;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

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
public class DiscoveryTourPresenter extends BasePresenter<DiscoveryTourContract.Model, DiscoveryTourContract.View> {
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
    public DiscoveryTourPresenter(DiscoveryTourContract.Model model, DiscoveryTourContract.View rootView) {
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
                        getLoaction(location.getLongitude() + "", location.getLatitude() + "");
//                        CoordinateConverter converter  = new CoordinateConverter(activity);
//                        // CoordType.GPS 待转换坐标类型
//                        converter.from(CoordinateConverter.CoordType.GPS);
//                        DPoint sourceLatLng=new DPoint();
//                        sourceLatLng.setLatitude(location.getLatitude());
//                        sourceLatLng.setLongitude( location.getLongitude());
//                        try {
//                            converter.coord(sourceLatLng);
//                            DPoint desLatLng = converter.convert();
//                            Log.e("1111111111",desLatLng.getLatitude()+"----------"+ desLatLng.getLongitude());
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });

            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {

            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                PermissionDialog.create().showDialog(activity, permissions);
            }
        }, new RxPermissions((FragmentActivity) activity), mErrorHandler);

    }


    /**
     * 探索方式列表
     */
    public void getLoaction(String lng, String lat) {
        mModel.information(lng, lat).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onLocationSuccess();
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
     * 探索方式列表
     */
    public void getExploreList() {
        mModel.exploretypelist().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<List<PlanetEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<List<PlanetEntity>> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onExploretypeSuccess(baseEntity.getData());
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
    public void getMessage() {
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