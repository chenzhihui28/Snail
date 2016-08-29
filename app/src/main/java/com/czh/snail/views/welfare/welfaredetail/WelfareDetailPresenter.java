package com.czh.snail.views.welfare.welfaredetail;

import com.czh.snail.base.BasePresenter;

/**
 * Created by chenzhihui on 16/8/5.
 * 福利Presenter
 */
public class WelfareDetailPresenter implements BasePresenter, WelfareDetailContract.Presenter {
    private WelfareDetailContract.View mView;
    private static final String TAG = WelfareDetailPresenter.class.getSimpleName();

    public WelfareDetailPresenter(WelfareDetailContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
    }





    @Override
    public void stop() {

    }

}
