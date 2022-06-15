package com.drifting.bureau.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.PlanetEntity;
import com.drifting.bureau.mvp.ui.activity.index.DriftingBottleActivity;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.ToastUtil;

import java.util.List;

public class DiscoveryViewpagerAdapter extends PagerAdapter {

    private List<PlanetEntity> list;

    private Context context;

    public DiscoveryViewpagerAdapter(Context context, List<PlanetEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discovery, container, false);
        RelativeLayout mRlPlanet= view.findViewById(R.id.rl_planet);
        ImageView imageView = view.findViewById(R.id.iv_planet);
        TextView textView = view.findViewById(R.id.tv_planet);
        GlideUtil.create().loadLongImage(context, list.get(position % list.size()).getImageUrl(), imageView);
        textView.setText(list.get(position % list.size()).getName());
        mRlPlanet.setOnClickListener(v->{
            if (!textView.getText().toString().equals("传递漂")){
                ToastUtil.showToast("暂未开放");
            }else {
                DriftingBottleActivity.start(context,list.get(position % list.size()).getId(), textView.getText().toString(), false);
            }
        });
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
