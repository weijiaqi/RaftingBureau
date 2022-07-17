package com.drifting.bureau.mvp.presenter;

import android.app.Application;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.drifting.bureau.app.application.RBureauApplication;
import com.drifting.bureau.mvp.contract.TopicDetailContract;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.util.FileUtil;
import com.hw.videoprocessor.VideoProcessor;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/07/12 17:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class TopicDetailPresenter extends BasePresenter<TopicDetailContract.Model, TopicDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TopicDetailPresenter(TopicDetailContract.Model model, TopicDetailContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 传递详情（新版）
     */
    public void moreDetails(int message_id,int type) {
        if (mRootView != null) {
            mRootView.showLoading();
        }
        mModel.moreDetails(message_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<MoreDetailsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<MoreDetailsEntity> entity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (entity.getCode() == 200) {
                                mRootView.onMoreDetailsSuccess(entity.getData(),type);
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
     * 视频压缩
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void compressVideo(Context context, int type, int explore_id, String content, int message_id, String videoPath, File voice, File image) {
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
                        .bitrate(bitrate / 10)

                        .process();
            } catch (Exception e) {
                success = false;
                e.printStackTrace();
                Timber.e("压缩失败");
            }
            if (success) {
                Timber.e("压缩成功---" + filepath);
                double fileSize = FileUtil.getFileOrFilesSize(filepath, FileUtil.SIZETYPE_MB);
                Timber.e("1----------" + fileSize);
                createwithword(type,explore_id,content,message_id,new File(filepath),voice,image);
            }
        }).start();
    }




    //发送漂流信息
    public  void createwithword(int type, int explore_id, String content, int message_id, File path, File voice, File image) {

        RequestBody requestBody, requestBody2;

        MultipartBody.Builder multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("explore_id", explore_id + "")
                .addFormDataPart("content", content)
                .addFormDataPart("message_id", message_id + "");

        if (type == 1) {  //视频
            requestBody = RequestBody.create(MediaType.parse("video/*"), path);
            requestBody2 = RequestBody.create(MediaType.parse("image/*"), image);
            multipartBody.addFormDataPart("vedio", path.getName(), requestBody);
            multipartBody.addFormDataPart("cover_image", image.getName(), requestBody2);
        }

        if (type == 2) {
            requestBody = RequestBody.create(MediaType.parse("image/*"), path);
            multipartBody.addFormDataPart("album_image", path.getName(), requestBody);
        }

        if (voice != null) {
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
     * 商品列表（发起话题和参与话题）
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
     * 查看评论或话题详情
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}