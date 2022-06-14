package com.drifting.bureau.mvp.contract;
import android.app.Activity;

import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;
import com.drifting.bureau.mvp.model.entity.TeaShopEntity;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;

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
public interface TeaShopContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void onloadStart();

        void onBusinessSuccess(TeaShopEntity entity, boolean isNotData);

        void loadFinish(boolean loadType, boolean isNotData);

        void loadState(int dataState);

        Activity getActivity();



    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel{
        Observable<BaseEntity<TeaShopEntity>> nearby(String name,int page, int limit);
    }
}