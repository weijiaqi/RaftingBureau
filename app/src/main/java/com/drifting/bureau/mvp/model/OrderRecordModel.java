package com.drifting.bureau.mvp.model;
import android.app.Application;

import com.drifting.bureau.app.api.ApiService;
import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;
import com.google.gson.Gson;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.ActivityScope;
import javax.inject.Inject;
import com.drifting.bureau.mvp.contract.OrderRecordContract;

import io.reactivex.Observable;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/28 11:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class OrderRecordModel extends BaseModel implements OrderRecordContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderRecordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseEntity<OrderRecordEntity>> ordermine(int page, int limit) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).ordermine(page,limit);
    }
}