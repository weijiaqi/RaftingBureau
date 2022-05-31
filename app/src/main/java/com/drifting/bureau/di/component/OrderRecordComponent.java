package com.drifting.bureau.di.component;
import dagger.BindsInstance;
import dagger.Component;

import com.drifting.bureau.mvp.contract.OrderRecordContract;
import com.drifting.bureau.mvp.contract.RaftingDetailsContract;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.di.module.OrderRecordModule;

import com.jess.arms.di.scope.ActivityScope;
import com.drifting.bureau.mvp.ui.activity.user.OrderRecordActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/28 11:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = OrderRecordModule.class,dependencies = AppComponent.class)
public interface OrderRecordComponent {

    void inject(OrderRecordActivity activity);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(OrderRecordContract.View view);

        Builder appComponent(AppComponent appComponent);

        OrderRecordComponent build();
    }
}