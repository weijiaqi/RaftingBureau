package com.drifting.bureau.mvp.model;

import android.app.Application;

import com.drifting.bureau.app.api.ApiService;
import com.drifting.bureau.mvp.contract.TopicDetailContract;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.google.gson.Gson;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

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
public class TopicDetailModel extends BaseModel implements TopicDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TopicDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseEntity<MoreDetailsEntity>> moreDetails(int message_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).moreDetails(message_id);
    }

    @Override
    public Observable<BaseEntity<CreatewithfileEntity>> createwithfileword(MultipartBody shortVoice) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).creatingwithfileword(shortVoice);
    }


    @Override
    public Observable<BaseEntity<SkuListEntity>> skulist(int explore_id, int message_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).skulist(explore_id, message_id);
    }

    @Override
    public Observable<BaseEntity<CreateOrderEntity>> createOrder(int type_id, String sku_codes) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).createOrder(type_id, sku_codes);
    }

    @Override
    public Observable<BaseEntity<CommentDetailsEntity>> details(int log_id, int level, int user_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).details(log_id, level, user_id);
    }

    @Override
    public Observable<BaseEntity> messageattending(int message_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).messageattending(message_id);
    }
}