package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.SpaceMarinesContract;
import com.drifting.bureau.mvp.contract.TeaShopContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.TeaShopModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.index.TeaShopActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/30 09:46
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TeaShopModule.class,dependencies = AppComponent.class)
public interface TeaShopComponent {

    void inject(TeaShopActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(TeaShopContract.View view);

        Builder appComponent(AppComponent appComponent);

        TeaShopComponent build();
    }
}