package com.drifting.bureau.di.module;

import com.drifting.bureau.mvp.contract.PostDriftingContract;
import com.drifting.bureau.mvp.model.PostDriftingModel;

import dagger.Binds;
import dagger.Module;

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
@Module
//构建PostDriftingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class PostDriftingModule {

    @Binds
    abstract PostDriftingContract.Model bindPostDriftingModel(PostDriftingModel model);
}