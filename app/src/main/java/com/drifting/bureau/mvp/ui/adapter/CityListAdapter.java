package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.data.entity.CityEntity;
import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;
import com.drifting.bureau.mvp.ui.holder.CityListHolder;
import com.drifting.bureau.mvp.ui.holder.DriftingTrackHolder;
import com.drifting.bureau.util.DigitUtil;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityListAdapter  extends BaseRecyclerAdapter<CityEntity> {



    private Map<String, Integer> alphaIndexer;
    private String[] sections;


    public CityListAdapter(List<CityEntity> infos) { super(infos);}



    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        CityListHolder cityListHolder=(CityListHolder) holder;
        cityListHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_city_list;
    }


    public void setData(List<CityEntity> infos){
        this.mDatas=infos;
        alphaIndexer = new HashMap<String, Integer>();
        sections = new String[mDatas.size()];
        //把相邻的相同的首字母放到一起,同时首个字母显示
        for (int i = 0; i < mDatas.size(); i++) {
            String currentStr = DigitUtil.getPinYinFirst(mDatas.get(i).getName());
            String previewStr = (i - 1) >= 0 ? DigitUtil.getPinYinFirst(mDatas.get(i - 1).getName())
                    : " ";
            if (!previewStr.equals(currentStr)) {//前一个首字母与当前首字母不同时加入HashMap中同时显示该字母
                String name = DigitUtil.getPinYinFirst(mDatas.get(i).getName());
                alphaIndexer.put(name, i);
                sections[i] = name;
            }
        }
         notifyDataSetChanged();
    }


    public Map<String, Integer> getCityMap() {
        return alphaIndexer;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new CityListHolder(view);
    }
}
