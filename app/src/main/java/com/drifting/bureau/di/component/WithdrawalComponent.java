package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.RaftingDetailsContract;
import com.drifting.bureau.mvp.contract.WithdrawalContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.WithdrawalModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.user.WithdrawalActivity;

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
@Component(modules = WithdrawalModule.class,dependencies = AppComponent.class)
public interface WithdrawalComponent {

    void inject(WithdrawalActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(WithdrawalContract.View view);

        Builder appComponent(AppComponent appComponent);

        WithdrawalComponent build();
    }
}