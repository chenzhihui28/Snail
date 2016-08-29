package com.czh.snail.views.welfare.welfarelist;

import com.czh.snail.base.BasePresenter;
import com.czh.snail.model.beans.GankBeauty;

import java.util.List;

/**
 * Created by chenzhihui on 2016/8/8.
 * 福利页契约类
 */
public interface WelfareContract {
    interface View {
        void startRefresh();
        void stopRefreshingOrLoading(boolean isFirstPage);
        void refreshOrLoadMoreSucceed(List<GankBeauty> gankBeautyList,boolean isFirstPage);
        void refreshOrLoadMoreError(String err, boolean isFirstPage);
    }

    interface Presenter extends BasePresenter {
        void getWelfareList(boolean firstPage);
    }
}
