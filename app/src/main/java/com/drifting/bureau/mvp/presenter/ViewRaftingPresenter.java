package com.drifting.bureau.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.MessageContentEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.ui.activity.index.VideoActivity;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.view.VoiceWave;
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

import javax.inject.Inject;

import com.drifting.bureau.mvp.contract.ViewRaftingContract;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/18 14:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ViewRaftingPresenter extends BasePresenter<ViewRaftingContract.Model, ViewRaftingContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ViewRaftingPresenter(ViewRaftingContract.Model model, ViewRaftingContract.View rootView) {
        super(model, rootView);
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
     * 查看漂流（话题详请）
     */
    public void messageContent(int message_id) {
        mModel.messagecontent(message_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<MessageContentEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<MessageContentEntity> entity) {
                        if (mRootView != null) {
                            if (entity.getCode() == 200) {
                                mRootView.onMessageContent(entity.getData());
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
     * 参与话题（有免费次数时使用）
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}