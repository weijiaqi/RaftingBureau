package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.AboutMeContract;
import com.drifting.bureau.mvp.contract.DiscoveryTourContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.AboutMeModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.user.AboutMeActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/12 12:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AboutMeModule.class,dependencies = AppComponent.class)
public interface AboutMeComponent {

    void inject(AboutMeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(AboutMeContract.View view);

        Builder appComponent(AppComponent appComponent);

        AboutMeComponent build();
    }
}