package com.drifting.bureau.mvp.model;

import android.app.Application;

import com.drifting.bureau.app.api.ApiService;
import com.drifting.bureau.mvp.contract.PostDriftingContract;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.google.gson.Gson;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

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
public class PostDriftingModel extends BaseModel implements PostDriftingContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PostDriftingModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseEntity<CreatewithfileEntity>> createwithword(int type_id, int explore_id, String content,int message_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).createwithword(type_id,explore_id,content,message_id);
    }

    @Override
    public Observable<BaseEntity<CreatewithfileEntity>> createwithvoice(MultipartBody shortVoice) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).createwithvoice(shortVoice);
    }

    @Override
    public Observable<BaseEntity<SkuListEntity>> skulist(int explore_id, int message_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).skulist(explore_id,message_id);
    }

    @Override
    public Observable<BaseEntity<CreateOrderEntity>> createOrder(int type_id, String sku_codes) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).createOrder(type_id,sku_codes);
    }

}