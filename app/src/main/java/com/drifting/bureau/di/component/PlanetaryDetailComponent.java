package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.PlanetaryDetailContract;
import com.drifting.bureau.mvp.contract.PlanetarySelectContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.PlanetaryDetailModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.index.PlanetaryDetailActivity;

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
@Component(modules = PlanetaryDetailModule.class,dependencies = AppComponent.class)
public interface PlanetaryDetailComponent {

    void inject(PlanetaryDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view( PlanetaryDetailContract.View view);

        Builder appComponent(AppComponent appComponent);

        PlanetaryDetailComponent build();
    }
}