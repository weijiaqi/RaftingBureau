package com.drifting.bureau.mvp.contract;

import android.app.Activity;

import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
import com.drifting.bureau.mvp.model.entity.MakingRecordEntity;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsEntity;
import com.drifting.bureau.mvp.model.entity.OrderOneEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.model.entity.SpaceCheckEntity;
import com.drifting.bureau.mvp.model.entity.SpaceInfoEntity;
import com.drifting.bureau.mvp.model.entity.TeamStatisticEntity;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/08/20 10:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface ArCenterConsoleContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void onMessageReceiveSuccess(MessageReceiveEntity entity);

        void OnTeamSuccess(TeamStatisticEntity entity);

        void loadFinish(boolean loadType, boolean isNotData);

        void loadState(int dataState);

        void myBillLogsSuccess(IncomeRecordEntity entity, boolean isNotData);

        void onMoreDetailsSuccess(MoreDetailsEntity entity, int type);

        void onSpcaeInfoSuccess(SpaceInfoEntity entity);

        void onOrderOneSuccess(OrderOneEntity entity);

        void onCommentDetailsSuccess(CommentDetailsEntity entity);

        void onOrderThrowSuccess();

        void onOrderMakingSuccess();


        void myOrderMadeSuccess(MakingRecordEntity entity, boolean isNotData);

        void loadMakeFinish(boolean loadType, boolean isNotData);

        void loadMakeState(int dataState);

        void myIncomeSuccess(IncomeRecordEntity entity, boolean isNotData);

        void loadIncomeFinish(boolean loadType, boolean isNotData);

        void loadIncomeState(int dataState);

        void onSpaceCheck(SpaceCheckEntity entity);


        void finishSuccess();

        void onNetError();

        Activity getActivity();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseEntity<MessageReceiveEntity>> messagereceive();

        Observable<BaseEntity<TeamStatisticEntity>> team();

        Observable<BaseEntity<IncomeRecordEntity>> withdrawnLogs(int page, int limit);

        Observable<BaseEntity<SpaceInfoEntity>> spaceinfo(String user_id);

        Observable<BaseEntity<MoreDetailsEntity>> moreDetails(int message_id);

        Observable<BaseEntity<OrderOneEntity>> orderone();

        Observable<BaseEntity<CommentDetailsEntity>> details(int log_id, int level, int user_id);

        Observable<BaseEntity> orderthrow(int space_order_id);

        Observable<BaseEntity> ordermaking(int space_order_id);

        Observable<BaseEntity<MakingRecordEntity>> ordermadelog(int page, int limit);

        Observable<BaseEntity<IncomeRecordEntity>> spacebillogs(int page, int limit);

        Observable<BaseEntity<SpaceCheckEntity>> spacecheck();


    }
}