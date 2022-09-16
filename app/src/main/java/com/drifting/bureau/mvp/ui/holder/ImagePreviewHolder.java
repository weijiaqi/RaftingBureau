package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.drifting.bureau.util.GlideUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import io.rong.imkit.picture.photoview.PhotoView;

public class ImagePreviewHolder extends BaseRecyclerHolder {

    private PhotoView mPhotoView;
    private Context context;

    public ImagePreviewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        mPhotoView = (PhotoView) itemView;
    }

    public void setData(@NonNull List<String> listBeanList, int position) {
        GlideUtil.create().loadLongMap(context, listBeanList.get(position), mPhotoView);
    }
}
