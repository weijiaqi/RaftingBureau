package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.IncomeRecordContract;
import com.drifting.bureau.mvp.contract.MyBlindBoxContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.IncomeRecordModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.user.IncomeRecordActivity;

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
@Component(modules = IncomeRecordModule.class,dependencies = AppComponent.class)
public interface IncomeRecordComponent {

    void inject(IncomeRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(IncomeRecordContract.View view);

        Builder appComponent(AppComponent appComponent);

        IncomeRecordComponent build();
    }
}