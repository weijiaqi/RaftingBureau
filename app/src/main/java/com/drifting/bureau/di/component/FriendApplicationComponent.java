package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.FriendApplicationModule;
import com.drifting.bureau.mvp.contract.FriendApplicationContract;
import com.drifting.bureau.mvp.ui.activity.user.FriendApplicationActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/06/18 11:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FriendApplicationModule.class, dependencies = AppComponent.class)
public interface FriendApplicationComponent {

    void inject(FriendApplicationActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(FriendApplicationContract.View view);

        Builder appComponent(AppComponent appComponent);

        FriendApplicationComponent build();
    }
}