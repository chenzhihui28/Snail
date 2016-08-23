package com.czh.snail.views.welfare;

import com.czh.snail.R;
import com.czh.snail.base.BaseFragment;
import com.czh.snail.databinding.FragmentWelfareBinding;

/**
 * Created by chenzhihui on 16/8/23.
 */

public class WelfareFragment extends BaseFragment<FragmentWelfareBinding,WelfarePresenter> implements WelfareContract.View{


    @Override
    protected void initView() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_welfare;
    }


    @Override
    protected WelfarePresenter setPresenter() {
        return new WelfarePresenter(this);
    }




}
