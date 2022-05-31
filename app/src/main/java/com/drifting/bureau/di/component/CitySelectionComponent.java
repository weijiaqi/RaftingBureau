package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.CitySelectionContract;
import com.drifting.bureau.mvp.contract.OrderRecordContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.CitySelectionModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.index.CitySelectionActivity;

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
@Component(modules = CitySelectionModule.class,dependencies = AppComponent.class)
public interface CitySelectionComponent {

    void inject(CitySelectionActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(CitySelectionContract.View view);

        Builder appComponent(AppComponent appComponent);

        CitySelectionComponent build();
    }
}