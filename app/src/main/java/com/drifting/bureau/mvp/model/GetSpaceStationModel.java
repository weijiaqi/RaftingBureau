package com.drifting.bureau.mvp.model;
import android.app.Application;

import com.drifting.bureau.app.api.ApiService;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.MysteryboxEntity;
import com.drifting.bureau.mvp.model.entity.PrizeEntity;
import com.drifting.bureau.mvp.model.entity.SpaceAboutEntity;
import com.drifting.bureau.mvp.model.entity.SpaceCheckEntity;
import com.drifting.bureau.mvp.model.entity.SpaceStationEntity;
import com.google.gson.Gson;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.ActivityScope;
import javax.inject.Inject;
import com.drifting.bureau.mvp.contract.GetSpaceStationContract;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;

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
public class GetSpaceStationModel extends BaseModel implements GetSpaceStationContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GetSpaceStationModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseEntity<List<SpaceStationEntity>>> space() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).space();
    }

    @Override
    public Observable<BaseEntity<CreateOrderEntity>> createOrderSpace(String sku_code,String sku_num) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).createOrderSpace(sku_code,sku_num);
    }

    @Override
    public Observable<BaseEntity<SpaceCheckEntity>> spacecheck() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).spacecheck();
    }

    @Override
    public Observable<BaseEntity<List<PrizeEntity>>> awardpreview() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).awardpreview();
    }

    @Override
    public Observable<BaseEntity<MysteryboxEntity>> mysterybox(int limit) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).mysterybox(limit);
    }

    @Override
    public Observable<BaseEntity<List<SpaceAboutEntity>>> spaceabout() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).spaceabout();
    }
}