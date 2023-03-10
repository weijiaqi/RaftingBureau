package com.drifting.bureau.mvp.model;

import android.app.Application;

import com.drifting.bureau.app.api.ApiService;
import com.drifting.bureau.mvp.contract.MySpaceStationContract;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.OrderDetailEntity;
import com.drifting.bureau.mvp.model.entity.OrderOneEntity;
import com.drifting.bureau.mvp.model.entity.SpaceInfoEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.google.gson.Gson;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;

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
public class MySpaceStationModel extends BaseModel implements MySpaceStationContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MySpaceStationModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseEntity<SpaceInfoEntity>> spaceinfo(String user_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).spaceinfo(user_id);
    }

    @Override
    public Observable<BaseEntity<OrderOneEntity>> orderone() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).orderone();
    }

    @Override
    public Observable<BaseEntity<UserInfoEntity>> userplayer(String user_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).userplayer(user_id);
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
    public Observable<BaseEntity<CommentDetailsEntity>> details(int log_id, int level, int user_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).details(log_id,level,user_id);
    }
}