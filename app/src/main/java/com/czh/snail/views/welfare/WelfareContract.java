package com.czh.snail.views.welfare;

import com.czh.snail.base.BasePresenter;
import com.czh.snail.model.beans.GankBeauty;

import java.util.List;

/**
 * Created by chenzhihui on 2016/8/8.
 * 福利页契约类
 */
public interface WelfareContract {
    interface View {
        void setRefreshing(boolean refreshing);
        void clearList();
        void addList(List<GankBeauty> gankBeautyList);
        void showErr(String err);
        void showEmpty();

    }

    interface Presenter extends BasePresenter {
        void getWelfarelist(boolean firstPage);
    }
}
