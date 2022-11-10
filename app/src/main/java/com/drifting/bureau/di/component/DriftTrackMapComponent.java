package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.DriftTrackMapModule;
import com.drifting.bureau.mvp.contract.DriftTrackMapContract;
import com.drifting.bureau.mvp.ui.activity.index.DriftTrackMapActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/08/26 20:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DriftTrackMapModule.class,dependencies = AppComponent.class)
public interface DriftTrackMapComponent {

    void inject(DriftTrackMapActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(DriftTrackMapContract.View view);

        Builder appComponent(AppComponent appComponent);

        DriftTrackMapComponent build();
    }
}