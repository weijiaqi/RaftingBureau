package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.DriftingBottleModule;
import com.drifting.bureau.mvp.contract.DriftingBottleContract;
import com.drifting.bureau.mvp.ui.activity.index.DriftingBottleActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/12 12:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DriftingBottleModule.class,dependencies = AppComponent.class)
public interface DriftingBottleComponent {

    void inject(DriftingBottleActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(DriftingBottleContract.View view);

        Builder appComponent(AppComponent appComponent);

        DriftingBottleComponent build();
    }
}