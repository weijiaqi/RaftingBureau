package com.drifting.bureau.mvp.model;

import android.app.Application;

import com.drifting.bureau.app.api.ApiService;
import com.drifting.bureau.mvp.contract.DiscoveryTourContract;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
import com.drifting.bureau.mvp.model.entity.PlanetEntity;
import com.drifting.bureau.mvp.model.entity.VersionUpdateEntity;
import com.google.gson.Gson;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

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
public class DiscoveryTourModel extends BaseModel implements DiscoveryTourContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public DiscoveryTourModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseEntity<List<PlanetEntity>>> exploretypelist() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).exploretypelist();
    }

    @Override
    public Observable<BaseEntity<MessageReceiveEntity>> messagereceive() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).messagereceive();
    }

    @Override
    public Observable<BaseEntity> information(String lng,String lat) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).information(lng,lat);
    }

    @Override
    public Observable<BaseEntity<VersionUpdateEntity>> checkVersion() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).checkVersion();
    }

}