package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.BlindBoxRecordModule;
import com.drifting.bureau.mvp.contract.BlindBoxRecordContract;
import com.drifting.bureau.mvp.ui.fragment.BoxRecordFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = BlindBoxRecordModule.class,dependencies = AppComponent.class)
public interface BoxRecordComponent {
    void inject(BoxRecordFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(BlindBoxRecordContract.View view);

        Builder appComponent(AppComponent appComponent);

        BoxRecordComponent build();
    }
}
