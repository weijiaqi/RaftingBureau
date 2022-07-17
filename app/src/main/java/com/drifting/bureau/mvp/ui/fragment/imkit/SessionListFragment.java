package com.drifting.bureau.mvp.ui.fragment.imkit;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.util.request.RequestUtil;

import io.rong.imkit.RongIM;
import io.rong.imkit.conversationlist.ConversationListFragment;
import io.rong.imkit.userinfo.RongUserInfoManager;
import io.rong.imkit.userinfo.UserDataProvider;
import io.rong.imlib.model.UserInfo;

public class SessionListFragment extends ConversationListFragment {
    public static SessionListFragment newInstance() {
        SessionListFragment fragment = new SessionListFragment();
        return fragment;
    }

    public SessionListFragment() {
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //连接融云成功后设置当前用户的信息 缓存到本地，
        // 如果没有会getUserInfo回调进行访问接口， 从App服务端获取列表某个userId的用户信息
        RongUserInfoManager.getInstance().setUserInfoProvider(new UserDataProvider.UserInfoProvider() {

            /**
             * 获取设置用户信息. 通过返回的 userId 来封装生产用户信息.
             * @param userId 用户 ID
             */
            @Override
            public UserInfo getUserInfo(String userId) {
                //异步请求
                findUserByIdFromServer(userId);
                return null;
            }
        }, true);
    }


    public void findUserByIdFromServer(String userId) {
        RequestUtil.create().friendinfo(userId, entity -> {
            if (entity!=null &&entity.getCode()==200){
                UserInfo myUserInfo = new UserInfo(userId,entity.getData().getName(), Uri.parse(entity.getData().getProfile_photo()));
                //这个方法是融云设置缓存到本地
                RongIM.getInstance().refreshUserInfoCache(myUserInfo);
            }
        });
    }


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conversationlist, (ViewGroup) null, false);
    }

}
