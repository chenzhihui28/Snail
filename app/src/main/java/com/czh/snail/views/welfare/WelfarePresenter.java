package com.czh.snail.views.welfare;

import com.czh.snail.base.BasePresenter;
import com.czh.snail.base.BaseSubscriber;
import com.czh.snail.model.Repository;
import com.czh.snail.model.beans.GankBeautyResult;

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

    public WelfarePresenter(WelfareContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        getWelfarelist(true);
    }


    //获取图片列表
    @Override
    public void getWelfarelist(boolean firstPage) {
        if (firstPage) {
            mView.setRefreshing(true);
            pageIndex = 1;
            mView.clearList();
        } else {
            pageIndex += 1;
        }
        Observable getImageListObservable = Repository.getInstance().getWelfareList(PAGE_SIZE,pageIndex);
        getImageListSubscription = getImageListObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<GankBeautyResult>() {
                    @Override
                    public void onCompleted() {
                        mView.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.setRefreshing(false);
                        mView.showErr(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(GankBeautyResult gankBeautyResult) {
                        mView.setRefreshing(false);
                        if (gankBeautyResult != null && gankBeautyResult.beauties != null) {
                            if (gankBeautyResult.beauties.size() == 0) {
                                mView.showEmpty();
                            } else {
                                mView.addList(gankBeautyResult.beauties);
                            }
                        } else {
                            onError(new Throwable());
                        }
                    }
                });
    }


    @Override
    public void stop() {
        getImageListSubscription.unsubscribe();

    }

}
