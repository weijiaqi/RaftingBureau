package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.MyCouponContract;
import com.drifting.bureau.mvp.contract.WinningRecordContract;
import com.drifting.bureau.mvp.ui.fragment.MyCouponFragment;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.MyCouponModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.index.MyCouponActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/10/14 11:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyCouponModule.class,dependencies = AppComponent.class)
public interface MyCouponComponent {

    void inject(MyCouponFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(MyCouponContract.View view);

        Builder appComponent(AppComponent appComponent);

        MyCouponComponent build();
    }
}