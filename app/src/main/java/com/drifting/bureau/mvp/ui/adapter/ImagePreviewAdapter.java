package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.SpaceAboutEntity;
import com.drifting.bureau.mvp.ui.holder.HowToPlayHolder;
import com.drifting.bureau.mvp.ui.holder.ImagePreviewHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;


public class ImagePreviewAdapter  extends BaseRecyclerAdapter<String> {

    public ImagePreviewAdapter(List<String> infos) { super(infos);}


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        ImagePreviewHolder imagePreviewHolder=(ImagePreviewHolder) holder;
        imagePreviewHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_image_preview;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new ImagePreviewHolder(view);
    }
}
