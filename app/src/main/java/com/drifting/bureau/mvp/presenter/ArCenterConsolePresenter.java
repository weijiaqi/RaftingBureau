package com.drifting.bureau.mvp.presenter;

import android.app.Application;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.drifting.bureau.app.application.RBureauApplication;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
import com.drifting.bureau.mvp.model.entity.MakingRecordEntity;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsEntity;
import com.drifting.bureau.mvp.model.entity.OrderOneEntity;
import com.drifting.bureau.mvp.model.entity.QuestionAssessEntity;
import com.drifting.bureau.mvp.model.entity.QuestionEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.model.entity.SpaceCheckEntity;
import com.drifting.bureau.mvp.model.entity.SpaceInfoEntity;
import com.drifting.bureau.mvp.model.entity.TeamStatisticEntity;
import com.drifting.bureau.util.FileUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
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

import com.drifting.bureau.mvp.contract.ArCenterConsoleContract;
import com.jess.arms.utils.RxLifecycleUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/08/20 10:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ArCenterConsolePresenter extends BasePresenter<ArCenterConsoleContract.Model, ArCenterConsoleContract.View> {
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
    public ArCenterConsolePresenter(ArCenterConsoleContract.Model model, ArCenterConsoleContract.View rootView) {
        super(model, rootView);
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
     * 星际团队信息
     */

    public void team() {
        mModel.team().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<TeamStatisticEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<TeamStatisticEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.OnTeamSuccess(baseEntity.getData());
                            } else {
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


    /**
     * 提现记录
     */
    public void withdrawnLogs(int page, boolean loadType) {
        mModel.withdrawnLogs(page, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<IncomeRecordEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<IncomeRecordEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                if (baseEntity.getData().getList() == null || baseEntity.getData().getList().size() == 0) {
                                    mRootView.loadState(ViewUtil.NOT_DATA);
                                    mRootView.loadFinish(loadType, true);
                                } else {
                                    mRootView.loadState(ViewUtil.HAS_DATA);
                                    mRootView.loadFinish(loadType, false);
                                }
                                mRootView.myBillLogsSuccess(baseEntity.getData(), loadType);
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


    /**
     * 传递详情（新版）
     */
    public void moreDetails(int message_id, int type) {

        mModel.moreDetails(message_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<MoreDetailsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<MoreDetailsEntity> entity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (entity.getCode() == 200) {
                                mRootView.onMoreDetailsSuccess(entity.getData(), type);
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
     * 空间站信息（我的空间站）
     */

    public void spaceinfo(String user_id) {
        mModel.spaceinfo(user_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<SpaceInfoEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<SpaceInfoEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onSpcaeInfoSuccess(baseEntity.getData());
                            } else {
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


    /**
     * 漂流新订单（我的空间站）
     */

    public void orderone() {
        mModel.orderone().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<OrderOneEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<OrderOneEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onOrderOneSuccess(baseEntity.getData());
                            } else {
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


    /**
     * 查看空间站评论或话题详情
     */
    public void details(int log_id, int level, int user_id) {
        mModel.details(log_id, level, user_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CommentDetailsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CommentDetailsEntity> entity) {
                        if (mRootView != null) {
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
                            mRootView.onNetError();
                        }
                    }
                });
    }


    /**
     * 空间站丢回太空
     */
    public void orderthrow(int space_order_id) {
        mModel.orderthrow(space_order_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onOrderThrowSuccess();
                            } else {
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


    /**
     * 制作订单（空间站为他制作）
     */
    public void ordermaking(int space_order_id) {
        mModel.ordermaking(space_order_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onOrderMakingSuccess();
                            } else {
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


    /**
     * 制作记录
     */
    public void ordermadelog(int page,  boolean loadType) {

        mModel.ordermadelog(page, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<MakingRecordEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<MakingRecordEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                if (baseEntity.getData().getList() == null || baseEntity.getData().getList().size() == 0) {
                                    mRootView.loadMakeState(ViewUtil.NOT_DATA);
                                    mRootView.loadMakeFinish(loadType, true);
                                } else {
                                    mRootView.loadMakeState(ViewUtil.HAS_DATA);
                                    mRootView.loadMakeFinish(loadType, false);
                                }
                                mRootView.myOrderMadeSuccess(baseEntity.getData(), loadType);
                            } else {
                                mRootView.loadMakeState(ViewUtil.NOT_SERVER);
                                mRootView.loadMakeFinish(loadType, false);
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


    /**
     * 收支记录
     */
    public void spacebillogs(int page, boolean loadType) {

        mModel.spacebillogs(page, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<IncomeRecordEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<IncomeRecordEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                if (baseEntity.getData().getList() == null || baseEntity.getData().getList().size() == 0) {
                                    mRootView.loadIncomeState(ViewUtil.NOT_DATA);
                                    mRootView.loadIncomeFinish(loadType, true);
                                } else {
                                    mRootView.loadIncomeState(ViewUtil.HAS_DATA);
                                    mRootView.loadIncomeFinish(loadType, false);
                                }
                                mRootView.myIncomeSuccess(baseEntity.getData(), loadType);
                            } else {
                                mRootView.loadIncomeState(ViewUtil.NOT_SERVER);
                                mRootView.loadIncomeFinish(loadType, false);
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


    /**
     * 检查是否有空间站
     */
    public void spacecheck() {
        mModel.spacecheck().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<SpaceCheckEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<SpaceCheckEntity> baseEntity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (baseEntity.getCode() == 200) {
                                mRootView.onSpaceCheck(baseEntity.getData());
                            } else {
                                if (!TextUtils.isEmpty(baseEntity.getMsg())) {
                                    mRootView.showMessage((baseEntity.getMsg()));
                                }
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
     *问题列表（搬离星球）
     */
    public void questionlist() {
        mModel.questionlist().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<List<QuestionEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<List<QuestionEntity>> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onQuestionListSuccess(baseEntity.getData());
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
     *答题测评（搬离星球）
     */
    public void questionassess(Map<String, String> map) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), new JSONObject(map).toString());
        mModel.questionassess(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<QuestionAssessEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<QuestionAssessEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onQuestionAssessSuccess(baseEntity.getData());
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