package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.RaftingBureaufriendModule;
import com.drifting.bureau.mvp.contract.RaftingBureaufriendContract;
import com.drifting.bureau.mvp.ui.fragment.RaftingBureaufriendFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;

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
@Component(modules = RaftingBureaufriendModule.class, dependencies = AppComponent.class)
public interface RaftingBureaufriendComponent {

    void inject(RaftingBureaufriendFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(RaftingBureaufriendContract.View view);

        Builder appComponent(AppComponent appComponent);

        RaftingBureaufriendComponent build();
    }
}