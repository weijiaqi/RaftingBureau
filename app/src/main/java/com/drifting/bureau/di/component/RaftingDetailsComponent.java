package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.RaftingDetailsContract;
import com.drifting.bureau.mvp.contract.VideoRecordingContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.RaftingDetailsModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.index.RaftingDetailsActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/19 09:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RaftingDetailsModule.class,dependencies = AppComponent.class)
public interface RaftingDetailsComponent {

    void inject(RaftingDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(RaftingDetailsContract.View view);

        Builder appComponent(AppComponent appComponent);

        RaftingDetailsComponent build();
    }
}