package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.ui.contract.BlindBoxRecordContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.BlindBoxRecordModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.user.BlindBoxRecordActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/07/19 10:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BlindBoxRecordModule.class,dependencies = AppComponent.class)
public interface BlindBoxRecordComponent {

    void inject(BlindBoxRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(BlindBoxRecordContract.View view);

        Builder appComponent(AppComponent appComponent);

        BlindBoxRecordComponent build();
    }
}