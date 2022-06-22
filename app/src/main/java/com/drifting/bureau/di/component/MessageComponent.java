package com.drifting.bureau.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.MessageContract;
import com.drifting.bureau.mvp.ui.fragment.MessageFragment;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.MessageModule;

import com.jess.arms.di.scope.FragmentScope;


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
@FragmentScope
@Component(modules = MessageModule.class, dependencies = AppComponent.class)
public interface MessageComponent {

    void inject(MessageFragment fragment);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(MessageContract.View view);

        Builder appComponent(AppComponent appComponent);

        MessageComponent build();
    }
}