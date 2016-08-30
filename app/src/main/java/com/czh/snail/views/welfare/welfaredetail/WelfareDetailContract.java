package com.czh.snail.views.welfare.welfaredetail;

import com.czh.snail.base.BasePresenter;

/**
 * Created by chenzhihui on 2016/8/8.
 * 福利页契约类
 */
public interface WelfareDetailContract {
    interface View {
        void showImg(String url);
        void showSnack(int message);

    }

    interface Presenter extends BasePresenter {
        void download();
    }
}
