package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.OrderRecordContract;
import com.drifting.bureau.mvp.contract.SpaceMarinesContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.SpaceMarinesModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.user.SpaceMarinesActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/28 11:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SpaceMarinesModule.class,dependencies = AppComponent.class)
public interface SpaceMarinesComponent {

    void inject(SpaceMarinesActivity activity);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(SpaceMarinesContract.View view);

        Builder appComponent(AppComponent appComponent);

        SpaceMarinesComponent build();
    }
}