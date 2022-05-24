package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.DeliveryDetailsContract;
import com.drifting.bureau.mvp.contract.GetSpaceStationContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.GetSpaceStationModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.index.GetSpaceStationActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/24 12:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GetSpaceStationModule.class,dependencies = AppComponent.class)
public interface GetSpaceStationComponent {

    void inject(GetSpaceStationActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(GetSpaceStationContract.View view);

        Builder appComponent(AppComponent appComponent);

        GetSpaceStationComponent build();
    }
}