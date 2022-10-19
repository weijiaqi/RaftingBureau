package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.BlindBoxRecordModule;
import com.drifting.bureau.di.module.MyCouponModule;
import com.drifting.bureau.mvp.contract.MyCouponContract;
import com.drifting.bureau.mvp.ui.activity.index.CouponAvailableActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = MyCouponModule.class,dependencies = AppComponent.class)
public interface CouponAvailableComponent {

    void inject(CouponAvailableActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(MyCouponContract.View view);

        Builder appComponent(AppComponent appComponent);

        CouponAvailableComponent build();
    }
}
