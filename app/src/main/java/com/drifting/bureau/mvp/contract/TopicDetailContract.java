package com.drifting.bureau.mvp.contract;

import android.app.Activity;

import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

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
public interface TopicDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

<<<<<<< HEAD
        void onMoreDetailsSuccess(MoreDetailsEntity entity, int type);
=======
        void onMoreDetailsSuccess(MoreDetailsEntity entity,int type);
>>>>>>> origin/dev

        void onCreatewithwordSuccess(CreatewithfileEntity entity);

        void onSkuListSuccess(SkuListEntity skuListEntity);

<<<<<<< HEAD
        void onCommentDetailsSuccess(CommentDetailsEntity entity);

        void onCreateOrderSuccess(CreateOrderEntity entity);
=======
        void  onCommentDetailsSuccess(CommentDetailsEntity entity);

        void  onCreateOrderSuccess(CreateOrderEntity entity);
>>>>>>> origin/dev

        void onMessageAttendingSuccess();

        void onNetError();

        Activity getActivity();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseEntity<MoreDetailsEntity>> moreDetails(int message_id);

        Observable<BaseEntity<CreatewithfileEntity>> createwithfileword(MultipartBody shortVoice);


<<<<<<< HEAD
        Observable<BaseEntity<SkuListEntity>> skulist(int explore_id, int message_id);

        Observable<BaseEntity<CreateOrderEntity>> createOrder(int type_id, String sku_codes);

        Observable<BaseEntity<CommentDetailsEntity>> details(int log_id, int level, int user_id);
=======
        Observable<BaseEntity<SkuListEntity>> skulist( int explore_id, int message_id);

        Observable<BaseEntity<CreateOrderEntity>> createOrder(int type_id, String sku_codes);

        Observable<BaseEntity<CommentDetailsEntity>> details(int log_id, int level,int user_id);
>>>>>>> origin/dev

        Observable<BaseEntity> messageattending(int message_id);
    }
}