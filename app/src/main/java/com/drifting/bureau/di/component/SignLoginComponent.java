package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.SignLoginModule;
import com.drifting.bureau.mvp.contract.SignLoginContract;
import com.drifting.bureau.mvp.ui.activity.user.SignLoginHintActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = SignLoginModule.class,dependencies = AppComponent.class)
public interface SignLoginComponent {
    void inject(SignLoginHintActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(SignLoginContract.View view);

        Builder appComponent(AppComponent appComponent);

        SignLoginComponent build();
    }
}
