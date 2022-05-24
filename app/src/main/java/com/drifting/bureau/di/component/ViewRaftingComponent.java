package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.VideoRecordingContract;
import com.drifting.bureau.mvp.contract.ViewRaftingContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.ViewRaftingModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.index.ViewRaftingActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/18 14:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ViewRaftingModule.class,dependencies = AppComponent.class)
public interface ViewRaftingComponent {

    void inject(ViewRaftingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(ViewRaftingContract.View view);

        Builder appComponent(AppComponent appComponent);

        ViewRaftingComponent build();
    }
}