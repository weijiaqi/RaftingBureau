package com.drifting.bureau.mvp.ui.adapter;

import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.drifting.bureau.R;
import com.drifting.bureau.app.application.RBureauApplication;
import com.drifting.bureau.data.entity.WinningRecordEntity;
import com.drifting.bureau.mvp.ui.fragment.BoxRecordFragment;
import com.drifting.bureau.mvp.ui.fragment.WinningRecordFragment;
import com.jess.arms.utils.ArmsUtils;
import com.rb.core.tab.view.indicator.IndicatorViewPager;

import java.util.List;

public class WinningAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private List<WinningRecordEntity> mChannelDatas;

    public WinningAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return mChannelDatas.size();
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        if (mChannelDatas.get(position).getType()==1){
            return WinningRecordFragment.newInstance();
        }else {
             return BoxRecordFragment.newInstance();
        }
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(RBureauApplication.getContext()).inflate(R.layout.layout_tab_winning_top, container, false);
        }
        TextView tv_tab_top_title = (TextView) convertView;
        tv_tab_top_title.setText(mChannelDatas.get(position).getTitle());


        int witdh = getTextWidth(tv_tab_top_title);
        int padding = ArmsUtils.dip2px(RBureauApplication.getContext(), 19);
        //因为wrap的布局 字体大小变化会导致textView大小变化产生抖动，这里通过设置textView宽度就避免抖动现象
        tv_tab_top_title.setWidth((int) (witdh * 1.06f) + padding);

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

    public void setData(List<WinningRecordEntity> mTabTitle) {
        this.mChannelDatas = mTabTitle;
        notifyDataSetChanged();
    }

}
