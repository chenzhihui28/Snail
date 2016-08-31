package com.czh.snail.adapters;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.czh.snail.R;
import com.czh.snail.model.beans.Knowledge;

import java.util.List;

public class KnowledgeListAdapter extends BaseQuickAdapter<Knowledge> {
    public KnowledgeListAdapter(List<Knowledge> list) {
        super(R.layout.item_knowledge_list, list);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Knowledge item) {

    }


}
