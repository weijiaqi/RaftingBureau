package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.MoveAwayPlanetaryModule;
import com.drifting.bureau.mvp.contract.MoveAwayPlanetaryContract;
import com.drifting.bureau.mvp.ui.activity.index.AnswerResultActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = MoveAwayPlanetaryModule.class, dependencies = AppComponent.class)
public interface AnswerResultComponent {
    void inject(AnswerResultActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(MoveAwayPlanetaryContract.View view);

        Builder appComponent(AppComponent appComponent);

        AnswerResultComponent build();
    }
}
