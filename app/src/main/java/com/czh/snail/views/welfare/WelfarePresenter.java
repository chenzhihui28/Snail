package com.czh.snail.views.welfare;

import com.czh.snail.base.BasePresenter;

/**
 * Created by chenzhihui on 16/8/5.
 * 福利Presenter
 */
public class WelfarePresenter implements BasePresenter, WelfareContract.Presenter{
    private WelfareContract.View mView;

    public WelfarePresenter(WelfareContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }


    @Override
    public void stop() {

    }

}
