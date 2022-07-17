package com.drifting.bureau.di.module;

import com.drifting.bureau.mvp.contract.PlanetaryDetailContract;
import com.drifting.bureau.mvp.model.PlanetaryDetailModel;

import dagger.Binds;
import dagger.Module;

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
@Module
 //构建PlanetaryDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class PlanetaryDetailModule {

    @Binds
    abstract PlanetaryDetailContract.Model bindPlanetaryDetailModel(PlanetaryDetailModel model);
}