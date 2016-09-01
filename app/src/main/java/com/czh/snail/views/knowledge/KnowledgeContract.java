package com.czh.snail.views.knowledge;

import com.czh.snail.base.BasePresenter;
import com.czh.snail.model.beans.Gank;

import java.util.List;

/**
 * Created by chenzhihui on 2016/8/8.
 * 福利页契约类
 */
public interface KnowledgeContract {
    interface View {
        void startRefresh();
        void stopRefreshingOrLoading(boolean isFirstPage);
        void refreshOrLoadMoreSucceed(List<Gank> gankList, boolean isFirstPage);
        void refreshOrLoadMoreError(String err, boolean isFirstPage);
    }

    interface Presenter extends BasePresenter {
        void getWelfareList(boolean firstPage);
    }
}
