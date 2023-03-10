package com.drifting.bureau.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.baidu.mapapi.model.LatLng;
import com.drifting.bureau.app.application.RBureauApplication;
import com.drifting.bureau.mvp.model.entity.BoxEntity;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderOpenBoxEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsForMapEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.AppUtil;
import com.drifting.bureau.util.FileUtil;
import com.drifting.bureau.util.LocationUtil;
import com.drifting.bureau.util.ToastUtil;
import com.hw.videoprocessor.VideoProcessor;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

import javax.inject.Inject;

import com.drifting.bureau.mvp.contract.DriftTrackMapContract;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/08/26 20:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">?????????????????????</a>
 * ================================================
 */
@ActivityScope
public class DriftTrackMapPresenter extends BasePresenter<DriftTrackMapContract.Model, DriftTrackMapContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public DriftTrackMapPresenter(DriftTrackMapContract.Model model, DriftTrackMapContract.View rootView) {
        super(model, rootView);
    }

    /**
     * ?????????????????????????????????
     */
    public void moreDetails(int message_id, int type) {
        if (mRootView != null) {
            mRootView.showLoading();
        }
        mModel.moreDetailsForMap(message_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<MoreDetailsForMapEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<MoreDetailsForMapEntity> entity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (entity.getCode() == 200) {
                                mRootView.onMoreDetailsForMapSuccess(entity.getData(), type);
                            } else {
                                mRootView.showMessage(entity.getMsg());
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


    /**
     * ???????????????????????????
     */
    public void details(int log_id, int level, int user_id) {
        mModel.details(log_id, level, user_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CommentDetailsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CommentDetailsEntity> entity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (entity.getCode() == 200) {
                                mRootView.onCommentDetailsSuccess(entity.getData());
                            } else {
                                mRootView.showMessage(entity.getMsg());
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

    /**
     * ??????????????????????????????????????????
     */
    public void messageattending(int message_id) {
        mModel.messageattending(message_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity entity) {
                        if (mRootView != null) {
                            if (entity.getCode() == 200) {
                                mRootView.onMessageAttendingSuccess();
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


    /**
     * ?????????????????????????????????????????????
     */
    public void skulist(int explore_id, int message_id) {
        mModel.skulist(explore_id, message_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<SkuListEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<SkuListEntity> entity) {
                        if (mRootView != null) {
                            if (entity.getCode() == 200) {
                                mRootView.onSkuListSuccess(entity.getData(),message_id);
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

    /**
     * ????????????
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void compressVideo(Context context, int type, String content, int message_id, String videoPath, File voice, File image, String tag, int free) {
        String filepath = FileUtil.saveVideoPath(context);
        new Thread(() -> {
            boolean success = true;
            try {
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(videoPath);
                int originWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
                int originHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
                int bitrate = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
                double fileSize = FileUtil.getFileOrFilesSize(videoPath, FileUtil.SIZETYPE_MB);
                Timber.e("1----------" + fileSize);
                VideoProcessor.processor(RBureauApplication.getContext())
                        .input(videoPath)
                        .output(filepath)
                        .outWidth(originWidth)
                        .outHeight(originHeight)
                        .bitrate(bitrate / 2)
                        .process();
            } catch (Exception e) {
                success = false;
                e.printStackTrace();
                Timber.e("????????????");
            }
            if (success) {
                Timber.e("????????????---" + filepath);
                double fileSize = FileUtil.getFileOrFilesSize(filepath, FileUtil.SIZETYPE_MB);
                Timber.e("1----------" + fileSize);
                createwithword(type, content, message_id, new File(filepath), voice, image, tag, free);
            }
        }).start();
    }


    //??????????????????
    public void createwithword(int type, String content, int message_id, File path, File voice, File image, String tag, int free) {

        RequestBody requestBody, requestBody2;

        MultipartBody.Builder multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("message_id", message_id + "")
                .addFormDataPart("free", free + "")
                .addFormDataPart("tag", tag == null ? "" : tag);

        //??????
        if (content != null && !TextUtils.isEmpty(content)) {
            multipartBody.addFormDataPart("content", content);
        }


        if (type == 1) {  //??????
            requestBody = RequestBody.create(MediaType.parse("video/*"), path);
            requestBody2 = RequestBody.create(MediaType.parse("image/*"), image);
            multipartBody.addFormDataPart("vedio", path.getName(), requestBody);
            multipartBody.addFormDataPart("cover_image", image.getName(), requestBody2);
        }

        if (type == 2) { //??????
            requestBody = RequestBody.create(MediaType.parse("image/*"), path);
            multipartBody.addFormDataPart("album_image", path.getName(), requestBody);
        }

        if (voice != null) { //??????
            requestBody = RequestBody.create(MediaType.parse("voice/*"), voice);
            multipartBody.addFormDataPart("audio", voice.getName(), requestBody);
        }


        MultipartBody body = multipartBody.build();
        mModel.createwithfileword(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CreatewithfileEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CreatewithfileEntity> entity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (entity.getCode() == 200) {
                                mRootView.onCreatewithwordSuccess(entity.getData());
                            } else {
                                mRootView.showMessage(entity.getMsg());
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


    /**
     * ????????????(????????????)
     */
    public void createOrder(int message_id, String sku_codes,int id) {
        mModel.createOrder(message_id, sku_codes).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CreateOrderEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CreateOrderEntity> entity) {
                        if (mRootView != null) {
                            if (entity.getCode() == 200) {
                                mRootView.onCreateOrderSuccess(entity.getData(),id);
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


    /**
     * ???????????????
     */
    public void getLocation(Activity activity) {
        PermissionUtil.launchLocation(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                LocationUtil.getCurrentLocation(new LocationUtil.LocationCallBack() {
                    @Override
                    public void onSuccess(Location location) {
                        try {
                            RBureauApplication.latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            Preferences.saveCity(AppUtil.getAddress(activity, location).get(0).getLocality());
                            getLoaction(location.getLongitude() + "", location.getLatitude() + "");
                        } catch (Exception e) {
                            Log.e(activity.getPackageName(), e.toString());
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        activity.startActivity(intent);
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
     * ??????????????????
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
     * ??????????????????
     */
    public void getBox() {
        mModel.getbox().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<List<BoxEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<List<BoxEntity>> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.OnBoxSuccess(baseEntity.getData());
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
     * ?????????????????????????????????
     */
    public void createOrderOpenBoxDaily(String box_no, int box_type) {
        mModel.createOrderOpenBoxDaily(box_no, box_type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CreateOrderOpenBoxEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CreateOrderOpenBoxEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.OnCreateOrderOpenBoxDailySuccess(baseEntity.getData());
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