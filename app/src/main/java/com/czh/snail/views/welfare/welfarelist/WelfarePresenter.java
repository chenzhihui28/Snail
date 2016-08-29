package com.czh.snail.views.welfare.welfarelist;

import com.czh.snail.R;
import com.czh.snail.base.BasePresenter;
import com.czh.snail.base.BaseSubscriber;
import com.czh.snail.model.Repository;
import com.czh.snail.model.beans.GankBeautyResult;
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
public class WelfarePresenter implements BasePresenter, WelfareContract.Presenter {
    private WelfareContract.View mView;
    private Subscription getImageListSubscription;
    public final static int PAGE_SIZE = 10;
    private static int pageIndex = 1;
    private static final String TAG = WelfarePresenter.class.getSimpleName();

    public WelfarePresenter(WelfareContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        getWelfareList(true);
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
        Observable getImageListObservable = Repository.getInstance().getWelfareList(PAGE_SIZE, pageIndex);
        getImageListSubscription = getImageListObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<GankBeautyResult>() {
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
                    public void onNext(GankBeautyResult gankBeautyResult) {
                        L.e(TAG, "onNext" + gankBeautyResult);
                        if (isFirstPage) {
                            mView.stopRefreshingOrLoading(true);
                        }
                        if (gankBeautyResult != null && gankBeautyResult.beauties != null) {
                            mView.refreshOrLoadMoreSucceed(gankBeautyResult.beauties, isFirstPage);
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
