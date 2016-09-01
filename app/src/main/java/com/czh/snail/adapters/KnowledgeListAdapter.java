package com.czh.snail.adapters;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.czh.snail.R;
import com.czh.snail.model.beans.Gank;

import java.util.List;

public class KnowledgeListAdapter extends BaseQuickAdapter<Gank> {
    public KnowledgeListAdapter(List<Gank> list) {
        super(R.layout.item_knowledge_list, list);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Gank item) {

    }


}
