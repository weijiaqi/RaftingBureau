package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.DiscoveryTourContract;
import com.drifting.bureau.mvp.contract.LoginContract;
import com.drifting.bureau.mvp.ui.activity.user.LoginActivity;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.DiscoveryTourModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;

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
@Component(modules = DiscoveryTourModule.class,dependencies = AppComponent.class)
public interface DiscoveryTourComponent {

    void inject(DiscoveryTourActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(DiscoveryTourContract.View view);

        Builder appComponent(AppComponent appComponent);

        DiscoveryTourComponent build();
    }
}