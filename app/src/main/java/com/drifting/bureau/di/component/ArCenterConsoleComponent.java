package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.ArCenterConsoleContract;
import com.drifting.bureau.mvp.contract.BlindBoxRecordContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.ArCenterConsoleModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.home.ArCenterConsoleActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/08/20 10:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ArCenterConsoleModule.class,dependencies = AppComponent.class)
public interface ArCenterConsoleComponent {

    void inject(ArCenterConsoleActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(ArCenterConsoleContract.View view);

        Builder appComponent(AppComponent appComponent);

        ArCenterConsoleComponent build();
    }
}