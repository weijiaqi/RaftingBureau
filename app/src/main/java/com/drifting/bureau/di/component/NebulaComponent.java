package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.NebulaModule;
import com.drifting.bureau.mvp.contract.NebulaContract;
import com.drifting.bureau.mvp.ui.activity.index.NebulaActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/07/14 12:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = NebulaModule.class, dependencies = AppComponent.class)
public interface NebulaComponent {

    void inject(NebulaActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(NebulaContract.View view);

        Builder appComponent(AppComponent appComponent);

        NebulaComponent build();
    }
}