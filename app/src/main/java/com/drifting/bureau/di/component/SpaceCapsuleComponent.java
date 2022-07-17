package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.GetSpaceStationModule;
import com.drifting.bureau.mvp.contract.GetSpaceStationContract;
import com.drifting.bureau.mvp.ui.activity.index.SpaceCapsuleActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = GetSpaceStationModule.class,dependencies = AppComponent.class)
public interface SpaceCapsuleComponent {
    void inject(SpaceCapsuleActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(GetSpaceStationContract.View view);

        Builder appComponent(AppComponent appComponent);

        SpaceCapsuleComponent build();
    }
}
