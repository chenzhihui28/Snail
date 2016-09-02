package com.czh.snail.views.knowledge;

import com.czh.snail.base.BasePresenter;
import com.czh.snail.model.beans.GankResult;

/**
 * Created by chenzhihui on 2016/8/8.
 * 福利页契约类
 */
public interface KnowledgeContract {
    interface View {
        void startRefresh();
        void stopRefreshing();
        void refreshSucceed(GankResult result);
        void refreshError(String err);
    }

    interface Presenter extends BasePresenter {
        void getTodayKnowledge(int year, int month, int day);
    }
}
