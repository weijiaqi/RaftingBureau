package com.drifting.bureau.mvp.presenter;

import android.app.Application;

import com.drifting.bureau.mvp.contract.MoveAwayPlanetaryContract;
import com.drifting.bureau.mvp.model.entity.QuestionAssessEntity;
import com.drifting.bureau.mvp.model.entity.QuestionEntity;
import com.drifting.bureau.mvp.model.entity.QuestionStagesEntity;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/06/01 11:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MoveAwayPlanetaryPresenter extends BasePresenter<MoveAwayPlanetaryContract.Model, MoveAwayPlanetaryContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MoveAwayPlanetaryPresenter(MoveAwayPlanetaryContract.Model model, MoveAwayPlanetaryContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 问题列表（搬离星球）
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
     * 答题测评（搬离星球）
     */
    public void questionassess(Map<String, String> map) {
        if (mRootView != null) {
            mRootView.showLoading();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), new JSONObject(map).toString());
        mModel.questionassess(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<QuestionAssessEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<QuestionAssessEntity> baseEntity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
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
     * 答题结果
     */
    public void assessResult() {
        if (mRootView != null) {
            mRootView.showLoading();
        }
        mModel.assessResult().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<QuestionAssessEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<QuestionAssessEntity> baseEntity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (baseEntity.getCode() == 200) {
                                mRootView.onAssessResultSuccess(baseEntity.getData());
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
     * 答题过程阶段
     */
    public void questionStages() {
        mModel.stages().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<List<QuestionStagesEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<List<QuestionStagesEntity>> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.onQuestionStagesSuccess(baseEntity.getData());
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