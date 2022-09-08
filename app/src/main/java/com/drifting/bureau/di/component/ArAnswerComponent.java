package com.drifting.bureau.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.drifting.bureau.di.module.MoveAwayPlanetaryModule;
import com.drifting.bureau.mvp.contract.MoveAwayPlanetaryContract;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.home.ArAnswerActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/09/07 10:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MoveAwayPlanetaryModule.class, dependencies = AppComponent.class)
public interface ArAnswerComponent {

    void inject(ArAnswerActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(MoveAwayPlanetaryContract.View view);

        Builder appComponent(AppComponent appComponent);

        ArAnswerComponent build();
    }
}