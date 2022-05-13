package com.drifting.bureau.mvp.contract;


import com.drifting.bureau.mvp.model.entity.LoginEntity;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;


public interface SignLoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loginSuccess(LoginEntity loginEntity);
        void onNetError();
    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseEntity<LoginEntity>> passwordlogin(String mobile, String password);
    }
}
