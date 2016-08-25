package com.czh.snail.views.splash;

import android.Manifest;

import com.czh.snail.base.BasePresenter;
import com.czh.snail.base.BaseSubscriber;
import com.czh.snail.model.Repository;
import com.czh.snail.model.beans.GankBeautyResult;
import com.czh.snail.utils.Constants;
import com.czh.snail.utils.MyApplication;
import com.czh.snail.utils.PermissionsChecker;
import com.czh.snail.utils.SharepreferenceUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by chenzhihui on 16/8/5.
 * 启动页Presenter
 * 先检查权限是否都授予了, 假如权限没有全部授予则重新请求相关权限, 不授予则退出程序
 * 然后开始请求今日的启动图,默认显示本地的一张,请求成功则显示新的那一张,总时间为5秒
 */
public class SplashPresenter implements BasePresenter, SplashContract.Presenter{
    private SplashContract.View mView;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Observable timerObservable;
    private Subscription timerObservableSubscription;
    private Subscription getImageSubscription;
    private static final int SPLASH_TIME = 2;// 单位:秒
    private static final int REQUEST_CODE = 0;


    public SplashPresenter(SplashContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        timerObservable = Observable.timer(SPLASH_TIME, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
        PermissionsChecker mChecker = new PermissionsChecker(MyApplication.getContext());
        if (mChecker.lacksPermissions(PERMISSIONS)) {
            mView.toPermissionActivityForResult(REQUEST_CODE,PERMISSIONS);
        } else {
            getTodayImage();
            startDefaultImageSubscription();
        }
    }



    @Override
    public void permissionsGranted() {
        getTodayImage();
        startDefaultImageSubscription();
    }

    //获取今天的图
    private void getTodayImage(){
        Observable getImageObservable = Repository.getInstance().getSplashPic();
        getImageSubscription = getImageObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<GankBeautyResult>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
//                mView.toMainActivityThenFinish();
            }

            @Override
            public void onNext(GankBeautyResult gankBeautyResult) {
                if (gankBeautyResult != null && gankBeautyResult.beauties != null && gankBeautyResult.beauties.size() >= 1) {
                    mView.showNewSplashImage(gankBeautyResult);
                }
            }
        });
    }

    private Subscription startDefaultImageSubscription() {
        return timerObservableSubscription = timerObservable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                if (SharepreferenceUtils.getBoolean(Constants.SharePreferenceAttr.FIRSTSTARTAFTERINSTALL, true)) {
                    SharepreferenceUtils.saveBoolean(Constants.SharePreferenceAttr.FIRSTSTARTAFTERINSTALL, false);
                    //TODO 首次运行要跳转引导页
//                    startActivity(GuideActivity.newIntent(SplashActivity.this));
                    mView.toMainActivityThenFinish();
                } else {
                    mView.toMainActivityThenFinish();
                }
            }
        });
    }




    @Override
    public void stop() {
        //presenter stop的时候取消监听所有Observable
        if (timerObservableSubscription != null) {
            timerObservableSubscription.unsubscribe();
        }

        if (getImageSubscription != null) {
            getImageSubscription.unsubscribe();
        }
    }

}
