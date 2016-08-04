package com.czh.snail.views;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.czh.snail.R;
import com.czh.snail.utils.Constants;
import com.czh.snail.utils.PermissionsChecker;
import com.czh.snail.utils.SharepreferenceUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {

    private static final int REQUEST_CODE = 0;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Observable timerObservable;
    private Subscription mSubscription;
    private static final int SPLASH_TIME = 2;// 单位:秒

    @Override
    protected boolean setOtherTheme() {
        setTheme(R.style.FullScreenTheme);
        return true;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        timerObservable = Observable.timer(SPLASH_TIME, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
        PermissionsChecker mChecker = new PermissionsChecker(this);
        if (mChecker.lacksPermissions(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
        } else {
            startSubscription();
        }
    }


    public Subscription startSubscription() {
        return mSubscription = timerObservable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                if (SharepreferenceUtils.getBoolean(Constants.SharePreferenceAttr.FIRSTSTARTAFTERINSTALL, true)) {
                    SharepreferenceUtils.saveBoolean(Constants.SharePreferenceAttr.FIRSTSTARTAFTERINSTALL, false);
                    //TODO 首次运行要跳转引导页
//                    startActivity(GuideActivity.newIntent(SplashActivity.this));
                    startActivity(MainActivity.newIntent(SplashActivity.this));
                } else {
                    startActivity(MainActivity.newIntent(SplashActivity.this));
                }
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PermissionsActivity.PERMISSIONS_DENIED == resultCode) {
            //权限未被授予，退出应用
            finish();
        } else if (PermissionsActivity.PERMISSIONS_GRANTED == resultCode) {
            //权限被授予，继续
            startSubscription();
        }
    }

    @Override
    protected void onDestroy() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        super.onDestroy();
    }
}
