package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.DeliveryDetailsModule;
import com.drifting.bureau.mvp.contract.DeliveryDetailsContract;
import com.drifting.bureau.mvp.ui.activity.index.DeliveryDetailsActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

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
@ActivityScope
@Component(modules = DeliveryDetailsModule.class,dependencies = AppComponent.class)
public interface DeliveryDetailsComponent {

    void inject(DeliveryDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(DeliveryDetailsContract.View view);

        Builder appComponent(AppComponent appComponent);

        DeliveryDetailsComponent build();
    }
}