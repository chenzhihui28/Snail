package com.czh.snail.views.knowledge;

import com.czh.snail.R;
import com.czh.snail.base.BasePresenter;
import com.czh.snail.base.BaseSubscriber;
import com.czh.snail.model.Repository;
import com.czh.snail.model.beans.GankResult;
import com.czh.snail.utils.L;
import com.czh.snail.utils.MyApplication;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenzhihui on 16/8/5.
 * 福利Presenter
 */
public class KnowledgePresenter implements BasePresenter, KnowledgeContract.Presenter {
    private KnowledgeContract.View mView;
    private Subscription getImageListSubscription;
    public final static int PAGE_SIZE = 10;
    private static int pageIndex = 1;
    private static final String TAG = KnowledgePresenter.class.getSimpleName();

    public KnowledgePresenter(KnowledgeContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
//        getWelfareList(true);
    }


    //获取图片列表
    @Override
    public void getWelfareList(final boolean isFirstPage) {
        if (isFirstPage) {
            pageIndex = 1;
            mView.startRefresh();
        } else {
            pageIndex += 1;
        }
        Observable getImageListObservable = Repository.getInstance().getKnowledgeList(2016,8,11);
        getImageListSubscription = getImageListObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<GankResult>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e(TAG, "onError" + e);
                        String errString = null;
                        if (e != null) {
                            errString = e.getMessage();
                        }
                        mView.stopRefreshingOrLoading(isFirstPage);
                        mView.refreshOrLoadMoreError(errString, isFirstPage);
                    }

                    @Override
                    public void onNext(GankResult gankBeautyResult) {
                        L.e(TAG, "onNext" + gankBeautyResult);
                        if (isFirstPage) {
                            mView.stopRefreshingOrLoading(true);
                        }
                        if (gankBeautyResult != null && gankBeautyResult.results.androidList != null) {
                            mView.refreshOrLoadMoreSucceed(gankBeautyResult.results.androidList, isFirstPage);
                        } else {
                            mView.refreshOrLoadMoreError(MyApplication.getContext().getString(R.string.network_err), isFirstPage);
                        }
                    }
                });
    }


    @Override
    public void stop() {
        getImageListSubscription.unsubscribe();

    }

}
