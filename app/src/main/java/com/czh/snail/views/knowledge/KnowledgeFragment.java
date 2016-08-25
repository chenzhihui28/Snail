package com.czh.snail.views.knowledge;

import com.czh.snail.R;
import com.czh.snail.base.LazyLoadFragment;
import com.czh.snail.databinding.FragmentKnowledgeBinding;

/**
 *
 */
public class KnowledgeFragment extends LazyLoadFragment<FragmentKnowledgeBinding,KnowledgePresenter> implements KnowledgeContract.View{
    public static KnowledgeFragment newInstance() {
        return new KnowledgeFragment();
    }


    @Override
    protected void initView() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected KnowledgePresenter setPresenter() {
        return new KnowledgePresenter(this);
    }
}
