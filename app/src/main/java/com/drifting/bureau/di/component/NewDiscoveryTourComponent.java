package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.DiscoveryTourModule;
import com.drifting.bureau.mvp.contract.DiscoveryTourContract;
import com.drifting.bureau.mvp.ui.activity.home.NewDiscoveryTourActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = DiscoveryTourModule.class,dependencies = AppComponent.class)
public interface NewDiscoveryTourComponent {

    void inject(NewDiscoveryTourActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(DiscoveryTourContract.View view);

        Builder appComponent(AppComponent appComponent);

        NewDiscoveryTourComponent build();
    }
}
