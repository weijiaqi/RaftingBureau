package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.PlanetarySelectModule;
import com.drifting.bureau.mvp.contract.PlanetarySelectContract;
import com.drifting.bureau.mvp.ui.activity.index.PlanetarySelectActivity;
import com.drifting.bureau.mvp.ui.activity.index.StarDistributionActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

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
@Component(modules = PlanetarySelectModule.class,dependencies = AppComponent.class)
public interface StarDistributionComponent {

    void inject(StarDistributionActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(PlanetarySelectContract.View view);

        Builder appComponent(AppComponent appComponent);

        StarDistributionComponent build();
    }
}