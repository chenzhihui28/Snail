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
    private static final String TAG = KnowledgePresenter.class.getSimpleName();

    public KnowledgePresenter(KnowledgeContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        getTodayKnowledge(2016,8,11);
    }


    //获取图片列表
    @Override
    public void getTodayKnowledge(final int year, final int month, final int day) {
        mView.startRefresh();
        Observable getImageListObservable = Repository.getInstance().getKnowledgeList(year,month,day);
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
                        mView.stopRefreshing();
                        mView.refreshError(errString);
                    }

                    @Override
                    public void onNext(GankResult gankBeautyResult) {
                        L.e(TAG, "onNext" + gankBeautyResult);
                        mView.stopRefreshing();
                        if (gankBeautyResult != null) {
                            mView.refreshSucceed(gankBeautyResult);
                        } else {
                            mView.refreshError(MyApplication.getContext().getString(R.string.network_err));
                        }
                    }
                });
    }


    @Override
    public void stop() {
        getImageListSubscription.unsubscribe();

    }

}
