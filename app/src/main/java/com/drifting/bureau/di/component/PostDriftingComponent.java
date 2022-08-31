package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.PostDriftingModule;
import com.drifting.bureau.mvp.contract.PostDriftingContract;
import com.drifting.bureau.mvp.ui.fragment.PostDriftingFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


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
@FragmentScope
@Component(modules = PostDriftingModule.class, dependencies = AppComponent.class)
public interface PostDriftingComponent {

    void inject(PostDriftingFragment fragment);


    @Component.Builder
    interface Builder {
        @BindsInstance
       Builder view(PostDriftingContract.View view);

       Builder appComponent(AppComponent appComponent);

        PostDriftingComponent build();
    }
}