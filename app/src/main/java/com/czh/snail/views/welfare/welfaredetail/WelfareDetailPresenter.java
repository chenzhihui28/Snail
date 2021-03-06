package com.czh.snail.views.welfare.welfaredetail;

import android.app.Activity;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.czh.snail.R;
import com.czh.snail.base.BasePresenter;
import com.czh.snail.base.BaseSubscriber;
import com.czh.snail.model.beans.GankBeauty;
import com.czh.snail.utils.FileUtils;
import com.czh.snail.utils.MyApplication;
import com.czh.snail.utils.StorageHelper;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.czh.snail.views.welfare.welfaredetail.WelFareDetailActivity.INTENT_ENTITY;

/**
 * Created by chenzhihui on 16/8/5.
 * 图片详情Presenter
 */
public class WelfareDetailPresenter implements BasePresenter, WelfareDetailContract.Presenter {
    private WelfareDetailContract.View mView;
    private static final String TAG = WelfareDetailPresenter.class.getSimpleName();
    private Subscription downloadSubscription;
    private String url;
    private GankBeauty beauty;


    public WelfareDetailPresenter(WelfareDetailContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        beauty = ((Activity) mView).getIntent().getParcelableExtra(INTENT_ENTITY);
        url = beauty.url;
        if (!TextUtils.isEmpty(url)) {
            mView.showImg(url);
        }
    }

    private Observable<Boolean> getDownloadObservable(final String url, final String id) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                File temp = new File(StorageHelper.getImageCachePath() + id + ".jpg");
                if (temp.exists()) {
                    subscriber.onError(new Throwable(MyApplication.getContext().getString(R.string.img_already_succeed)));
                } else {
                    File file = null;
                    try {
                        file = Glide.with(MyApplication.getContext()).load(url)
                                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .get();
                        if (file != null) {
                            FileUtils.copyFile(file.getAbsolutePath(), StorageHelper.getImageCachePath(), id + ".jpg");
                            subscriber.onCompleted();
                        }
                    } catch (Exception e) {
                        subscriber.onError(new Throwable(MyApplication.getContext().getString(R.string.download_fail)));
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    @Override
    public void download() {
        if (!TextUtils.isEmpty(url)) {
            downloadSubscription = getDownloadObservable(url, beauty._id).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber() {
                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            mView.showSnack(e.getMessage());
                        }

                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                            mView.showSnack(MyApplication.getContext().getString(R.string.download_succeed));
                        }
                    });

        }

    }

    @Override
    public void stop() {
        if (downloadSubscription != null) {
            downloadSubscription.unsubscribe();
        }

    }

}
