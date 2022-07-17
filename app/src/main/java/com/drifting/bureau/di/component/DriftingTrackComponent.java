package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.DriftingTrackModule;
import com.drifting.bureau.mvp.contract.DriftingTrackContract;
import com.drifting.bureau.mvp.ui.activity.user.DriftingTrackActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/27 11:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DriftingTrackModule.class,dependencies = AppComponent.class)
public interface DriftingTrackComponent {

    void inject(DriftingTrackActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(DriftingTrackContract.View view);

        Builder appComponent(AppComponent appComponent);

        DriftingTrackComponent build();
    }
}