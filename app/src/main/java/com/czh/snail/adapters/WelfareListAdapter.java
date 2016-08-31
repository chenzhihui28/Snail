package com.czh.snail.adapters;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.czh.snail.R;
import com.czh.snail.model.beans.GankBeauty;

import java.util.List;

public class WelfareListAdapter extends BaseQuickAdapter<GankBeauty> {
    public WelfareListAdapter(List<GankBeauty> list) {
        super(R.layout.item_welfare_list, list);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GankBeauty item) {
        Glide.with(mContext).load(item.url)
                .crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into((ImageView) helper.getView(R.id.imgWelfareItem));
    }


}
