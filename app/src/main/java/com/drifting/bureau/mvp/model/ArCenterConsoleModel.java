package com.drifting.bureau.mvp.model;
import android.app.Application;

import com.drifting.bureau.app.api.ApiService;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
import com.drifting.bureau.mvp.model.entity.MakingRecordEntity;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsEntity;
import com.drifting.bureau.mvp.model.entity.OrderOneEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.model.entity.SpaceCheckEntity;
import com.drifting.bureau.mvp.model.entity.SpaceInfoEntity;
import com.drifting.bureau.mvp.model.entity.TeamStatisticEntity;
import com.google.gson.Gson;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.ActivityScope;
import javax.inject.Inject;
import com.drifting.bureau.mvp.contract.ArCenterConsoleContract;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

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
public class ArCenterConsoleModel extends BaseModel implements ArCenterConsoleContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ArCenterConsoleModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseEntity<MessageReceiveEntity>> messagereceive() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).messagereceive();
    }

    @Override
    public Observable<BaseEntity<TeamStatisticEntity>> team() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).team();
    }

    @Override
    public Observable<BaseEntity<IncomeRecordEntity>> withdrawnLogs(int page, int limit) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).withdrawnLogs(page,limit);
    }

    @Override
    public Observable<BaseEntity<SpaceInfoEntity>> spaceinfo(String user_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).spaceinfo(user_id);
    }

    @Override
    public Observable<BaseEntity<MoreDetailsEntity>> moreDetails(int message_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).moreDetails(message_id);
    }

    @Override
    public Observable<BaseEntity<OrderOneEntity>> orderone() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).orderone();
    }

    @Override
    public Observable<BaseEntity<CommentDetailsEntity>> details(int log_id, int level, int user_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).details(log_id,level,user_id);
    }

    @Override
    public Observable<BaseEntity> orderthrow(int space_order_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).orderthrow(space_order_id);
    }

    @Override
    public Observable<BaseEntity> ordermaking(int space_order_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).ordermaking(space_order_id);
    }

    @Override
    public Observable<BaseEntity<MakingRecordEntity>> ordermadelog(int page, int limit) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).ordermadelog(page,limit);
    }

    @Override
    public Observable<BaseEntity<IncomeRecordEntity>> spacebillogs(int page, int limit) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).spacebillogs(page,limit);
    }

    @Override
    public Observable<BaseEntity<SpaceCheckEntity>> spacecheck() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).spacecheck();
    }

}