package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.MessageCenterContract;
import com.drifting.bureau.mvp.contract.MoveAwayPlanetaryContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.MessageCenterModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.user.MessageCenterActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/06/18 11:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MessageCenterModule.class,dependencies = AppComponent.class)
public interface MessageCenterComponent {

    void inject(MessageCenterActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(MessageCenterContract.View view);

        Builder appComponent(AppComponent appComponent);

        MessageCenterComponent build();
    }
}