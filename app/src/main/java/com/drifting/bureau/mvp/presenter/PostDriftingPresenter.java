package com.drifting.bureau.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.LoginEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.ui.activity.index.VideoActivity;
import com.drifting.bureau.mvp.ui.activity.index.VideoRecordingActivity;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.util.ToastUtil;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.drifting.bureau.mvp.contract.PostDriftingContract;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/19 09:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class PostDriftingPresenter extends BasePresenter<PostDriftingContract.Model, PostDriftingContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PostDriftingPresenter(PostDriftingContract.Model model, PostDriftingContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 文字漂
     */
    public void createwithword(int type_id, int explore_id, String content) {
        mModel.createwithword(type_id, explore_id, content).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CreatewithfileEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CreatewithfileEntity> entity) {
                        if (mRootView != null) {
                            if (entity.getCode() == 200) {
                                mRootView.onCreatewithwordSuccess(entity.getData());
                            } else {
                                showMessage(entity.getMsg());
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
     * 语音漂
     */
    public void createwithVoice(int type_id, int explore_id, File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("voice/*"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("type_id", type_id + "")
                .addFormDataPart("explore_id", explore_id + "")
                .addFormDataPart("content", file.getName(), requestBody)
                .build();

        mModel.createwithvoice(multipartBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CreatewithfileEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CreatewithfileEntity> entity) {
                        if (mRootView != null) {
                            if (entity.getCode() == 200) {
                                mRootView.onCreatewithwordSuccess(entity.getData());
                            } else {
                                showMessage(entity.getMsg());
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
     * 视频漂
     */
    public void createwithVideo(int type_id, int explore_id, File video, File image) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("voice/*"), video);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("image/*"), image);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("type_id", type_id + "")
                .addFormDataPart("explore_id", explore_id + "")
                .addFormDataPart("content", video.getName(), requestBody)
                .addFormDataPart("image", image.getName(), requestBody2)
                .build();

        mModel.createwithvoice(multipartBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CreatewithfileEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CreatewithfileEntity> entity) {
                        if (mRootView != null) {
                            if (entity.getCode() == 200) {
                                mRootView.onCreatewithwordSuccess(entity.getData());
                            } else {
                                showMessage(entity.getMsg());
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
     * 商品列表（发起话题和参与话题）
     */
    public void skulist(int type_id, int explore_id, int message_id) {
        mModel.skulist(type_id, explore_id, message_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<SkuListEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<SkuListEntity> entity) {
                        if (mRootView != null) {
                            if (entity.getCode() == 200) {
                                mRootView.onSkuListSuccess(entity.getData());
                            } else {
                                showMessage(entity.getMsg());
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
     * 创建订单(话题漂流)
     */
    public void createOrder(int message_id, String sku_codes) {
        mModel.createOrder(message_id, sku_codes).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CreateOrderEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CreateOrderEntity> entity) {
                        if (mRootView != null) {
                            if (entity.getCode() == 200) {
                                mRootView.onCreateOrderSuccess(entity.getData());
                            } else {
                                showMessage(entity.getMsg());
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
     * @description 语音
     */
    public void showDialog(Activity activity) {
        PermissionUtil.launchAudioStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mRootView.PermissionVoiceSuccess();
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
     * @description 视频
     */
    public void startVideo(Activity activity) {
        PermissionUtil.launchVideo(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                VideoRecordingActivity.start(activity, false);
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
     * @description 播放视频
     */
    public void PlayVideo(Activity activity, String uri) {
        PermissionUtil.externalReadStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                if (uri != null) {
                    VideoActivity.start(activity, uri, false);
                }
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

    public void  showMessage(String message){
        ToastUtil.showToast(message);
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