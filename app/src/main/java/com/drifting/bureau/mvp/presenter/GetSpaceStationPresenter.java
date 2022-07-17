package com.drifting.bureau.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.drifting.bureau.mvp.contract.GetSpaceStationContract;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.MysteryboxEntity;
import com.drifting.bureau.mvp.model.entity.PrizeEntity;
import com.drifting.bureau.mvp.model.entity.SpaceAboutEntity;
import com.drifting.bureau.mvp.model.entity.SpaceCheckEntity;
import com.drifting.bureau.mvp.model.entity.SpaceExchangeEntity;
import com.drifting.bureau.mvp.model.entity.SpaceStationEntity;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/24 12:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GetSpaceStationPresenter extends BasePresenter<GetSpaceStationContract.Model, GetSpaceStationContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public GetSpaceStationPresenter(GetSpaceStationContract.Model model, GetSpaceStationContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 奖品预览（获取空间站）
     */
    public void getAwardList() {
        mModel.awardpreview().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<List<PrizeEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<List<PrizeEntity>> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onAwardPreviewSuccess(baseEntity.getData());
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
     * 弹幕日志（获取空间站）
     */
    public void mysterybox(int limit) {
        mModel.mysterybox(limit).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<MysteryboxEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<MysteryboxEntity> baseEntity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (baseEntity.getCode() == 200) {
                                mRootView.onGetMysterybox(baseEntity.getData());
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
     * 盲盒列表
     */
    public void getSpaceList() {
        mModel.space().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<List<SpaceStationEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<List<SpaceStationEntity>> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onGetSpaceList(baseEntity.getData());
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
     * 创建订单（空间站）
     */
    public void createOrderSpace(String sku_code, String sku_num) {
        mModel.createOrderSpace(sku_code, sku_num).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CreateOrderEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CreateOrderEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onCreateOrderSpaceSuccess(baseEntity.getData());
                            } else {
                                if (!TextUtils.isEmpty(baseEntity.getMsg())) {
                                    mRootView.showMessage(baseEntity.getMsg());
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
     * 空间站玩法
     */
    public void spaceabout() {
        mModel.spaceabout().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<List<SpaceAboutEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<List<SpaceAboutEntity>> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onSpaceAbout(baseEntity.getData());
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
     * 兑换码兑换空间站
     */
    public void spaceexchange(String code) {
        mModel.spaceexchange(code).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<SpaceExchangeEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<SpaceExchangeEntity> baseEntity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (baseEntity.getCode() == 200) {
                                mRootView.showMessage("成功兑换"+baseEntity.getData().getName());
                            } else {
                                if (!TextUtils.isEmpty(baseEntity.getMsg())) {
                                    mRootView.showMessage("兑换码输入错误，请从新输入");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}