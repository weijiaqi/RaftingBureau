package com.drifting.bureau.di.component;

import com.drifting.bureau.di.module.TopicDetailModule;
import com.drifting.bureau.mvp.contract.TopicDetailContract;
import com.drifting.bureau.mvp.ui.activity.index.TopicDetailActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/07/12 17:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TopicDetailModule.class,dependencies = AppComponent.class)
public interface TopicDetailComponent {

    void inject(TopicDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(TopicDetailContract.View view);

        Builder appComponent(AppComponent appComponent);

        TopicDetailComponent build();
    }
}