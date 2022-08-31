package com.drifting.bureau.mvp.ui.activity.imkit;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.drifting.bureau.R;
<<<<<<< HEAD
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.util.ClickUtil;
=======
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
>>>>>>> origin/dev
import com.jess.arms.di.component.AppComponent;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.conversation.ConversationFragment;
import io.rong.imkit.userinfo.RongUserInfoManager;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

<<<<<<< HEAD
public class MyConversationActivity extends BaseManagerActivity {
=======
public class MyConversationActivity extends BaseActivity {
>>>>>>> origin/dev

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.ll_content)
    LinearLayout mRlContent;
    protected String mTargetId, title;
    protected Conversation.ConversationType mConversationType;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_conversation;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mRlContent.setBackgroundResource(R.color.color_01);

        if (this.getIntent() != null) {
            this.mTargetId = this.getIntent().getStringExtra("targetId");
            String type = this.getIntent().getStringExtra("ConversationType");
            this.title = this.getIntent().getStringExtra(RouteUtils.TITLE);
            if (TextUtils.isEmpty(type)) {
                return;
            }

            this.mConversationType = Conversation.ConversationType.valueOf(type.toUpperCase(Locale.US));
        }
        setTitle();

        // 添加会话界面
        ConversationFragment conversationFragment = new ConversationFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, conversationFragment);
        transaction.commit();
    }


    private void setTitle() {
        if (!TextUtils.isEmpty(this.mTargetId) && this.mConversationType.equals(Conversation.ConversationType.GROUP)) {
            Group group = RongUserInfoManager.getInstance().getGroupInfo(this.mTargetId);
            mToolbarTitle.setText(group == null ? this.mTargetId : group.getName());
        } else {
            UserInfo userInfo = RongUserInfoManager.getInstance().getUserInfo(this.mTargetId);
            mToolbarTitle.setText(userInfo == null ? title : userInfo.getName());
        }
    }


    @OnClick({R.id.toolbar_back})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
            }
        }
    }
}
