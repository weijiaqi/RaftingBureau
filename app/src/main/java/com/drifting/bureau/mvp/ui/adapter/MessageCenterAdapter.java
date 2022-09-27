package com.drifting.bureau.mvp.ui.adapter;

import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.drifting.bureau.R;
import com.drifting.bureau.app.application.RBureauApplication;
import com.drifting.bureau.data.entity.MessageCenterEntity;
import com.drifting.bureau.mvp.ui.fragment.MessageFragment;
import com.drifting.bureau.mvp.ui.fragment.RaftingBureaufriendFragment;
import com.drifting.bureau.mvp.ui.fragment.imkit.SessionListFragment;
import com.rb.core.tab.view.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

public class MessageCenterAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private List<MessageCenterEntity> mChannelDatas = new ArrayList<>();

    private ImageView iv_message;

    public MessageCenterAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return mChannelDatas.size();
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        if (mChannelDatas.get(position).getType() == 1) {
            return SessionListFragment.newInstance();
        } else if (mChannelDatas.get(position).getType() == 2) {
            return MessageFragment.newInstance(2);
        } else {
            return RaftingBureaufriendFragment.newInstance();
        }
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(RBureauApplication.getContext()).inflate(R.layout.layout_tab_top, container, false);
        }
        TextView tv_tab_top_title = convertView.findViewById(R.id.tv_tab_top_title);
        iv_message = convertView.findViewById(R.id.iv_message);
        tv_tab_top_title.setText(mChannelDatas.get(position).getTitle());
        iv_message.setVisibility(mChannelDatas.get(position).isUnread() ? View.VISIBLE : View.INVISIBLE);
//        int witdh = getTextWidth(tv_tab_top_title);
//        tv_tab_top_title.setWidth((int) (witdh * 1.06f));
        return convertView;
    }


    @Override
    public PagerAdapter getPagerAdapter() {
        return super.getPagerAdapter();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    private int getTextWidth(TextView textView) {
        if (textView == null) {
            return 0;
        }
        Rect bounds = new Rect();
        String text = textView.getText().toString();
        Paint paint = textView.getPaint();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int width = bounds.left + bounds.width();
        return width;
    }

    public void setData(List<MessageCenterEntity> mTabTitle) {
        this.mChannelDatas = mTabTitle;
        notifyDataSetChanged();
    }
}

