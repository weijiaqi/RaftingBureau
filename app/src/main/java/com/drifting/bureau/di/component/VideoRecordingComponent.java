package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.SignLoginContract;
import com.drifting.bureau.mvp.contract.VideoRecordingContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.VideoRecordingModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.index.VideoRecordingActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/16 11:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = VideoRecordingModule.class,dependencies = AppComponent.class)
public interface VideoRecordingComponent {

    void inject(VideoRecordingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(VideoRecordingContract.View view);

        Builder appComponent(AppComponent appComponent);

        VideoRecordingComponent build();
    }
}