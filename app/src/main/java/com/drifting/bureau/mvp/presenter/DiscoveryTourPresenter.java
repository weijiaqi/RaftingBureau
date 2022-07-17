package com.drifting.bureau.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.location.Location;

import androidx.fragment.app.FragmentActivity;

import com.drifting.bureau.mvp.contract.DiscoveryTourContract;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
import com.drifting.bureau.mvp.model.entity.PlanetEntity;
import com.drifting.bureau.mvp.model.entity.VersionUpdateEntity;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.mvp.ui.dialog.VersionUpdateDialog;
import com.drifting.bureau.util.LocationUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

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
     * 版本更新
     *
     * @param activity this
     */
    public void getVersionInfo(Activity activity) {
        mModel.checkVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<VersionUpdateEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<VersionUpdateEntity> data) {
                        if (mRootView != null && data.getData() != null) {
                            if (StringUtil.compareVersions(data.getData().getVersion(), StringUtil.getVersion(activity))) {
                                showVersionDialog(activity, data.getData());
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
     * 版本更新dialog
     *
     * @param activity
     */
    public void showVersionDialog(Activity activity, VersionUpdateEntity data) {
        VersionUpdateDialog versionUpdateDialog = new VersionUpdateDialog(activity, data.getUrl() + "?" + System.currentTimeMillis(), data.getStatus(), data.getMessage(), data.getVersion());
        versionUpdateDialog.show();
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