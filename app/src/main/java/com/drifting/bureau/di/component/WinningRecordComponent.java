package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.WinningRecordContract;
import com.drifting.bureau.mvp.contract.WithdrawalContract;
import com.drifting.bureau.mvp.ui.fragment.WinningRecordFragment;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.WinningRecordModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.index.WinningRecordActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/10/12 14:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = WinningRecordModule.class,dependencies = AppComponent.class)
public interface WinningRecordComponent {

    void inject(WinningRecordFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(WinningRecordContract.View view);

        Builder appComponent(AppComponent appComponent);

        WinningRecordComponent build();
    }
}