package com.czh.snail.views.knowledge;

import com.czh.snail.base.BasePresenter;

/**
 * Created by chenzhihui on 16/8/5.
 * 福利Presenter
 */
public class KnowledgePresenter implements BasePresenter, KnowledgeContract.Presenter {
    private KnowledgeContract.View mView;
    private static final String TAG = KnowledgePresenter.class.getSimpleName();

    public KnowledgePresenter(KnowledgeContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
    }


    //获取图片列表
//    @Override
//    public void getWelfareList(final boolean isFirstPage) {
//        if (isFirstPage) {
//            pageIndex = 1;
//            mView.startRefresh();
//        } else {
//            pageIndex += 1;
//        }
//        Observable getImageListObservable = Repository.getInstance().getWelfareList(PAGE_SIZE, pageIndex);
//        getImageListSubscription = getImageListObservable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<GankBeautyResult>() {
//                    @Override
//                    public void onCompleted() {
//                        super.onCompleted();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        L.e(TAG, "onError" + e);
//                        String errString = null;
//                        if (e != null) {
//                            errString = e.getMessage();
//                        }
//                        mView.stopRefreshingOrLoading(isFirstPage);
//                        mView.refreshOrLoadMoreError(errString, isFirstPage);
//                    }
//
//                    @Override
//                    public void onNext(GankBeautyResult gankBeautyResult) {
//                        L.e(TAG, "onNext" + gankBeautyResult);
//                        if (isFirstPage) {
//                            mView.stopRefreshingOrLoading(true);
//                        }
//                        if (gankBeautyResult != null && gankBeautyResult.beauties != null) {
//                            mView.refreshOrLoadMoreSucceed(gankBeautyResult.beauties, isFirstPage);
//                        } else {
//                            mView.refreshOrLoadMoreError(MyApplication.getContext().getString(R.string.network_err), isFirstPage);
//                        }
//                    }
//                });
//    }


    @Override
    public void stop() {
//        getImageListSubscription.unsubscribe();

    }

}
