package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.LoginContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.LoginModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.user.LoginActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/09 13:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LoginModule.class,dependencies = AppComponent.class)
public interface LoginComponent {

    void inject(LoginActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(LoginContract.View view);

        Builder appComponent(AppComponent appComponent);

        LoginComponent build();
    }
}