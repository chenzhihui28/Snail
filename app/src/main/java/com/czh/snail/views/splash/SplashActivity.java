package com.czh.snail.views.splash;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.czh.snail.R;
import com.czh.snail.base.BaseActivity;
import com.czh.snail.databinding.ActivitySplashBinding;
import com.czh.snail.model.beans.GankBeautyResult;
import com.czh.snail.views.PermissionsActivity;
import com.czh.snail.views.main.MainActivity;

/**
 * 启动页
 * 先检查权限是否都授予了, 假如权限没有全部授予则重新请求相关权限, 不授予则退出程序
 * 然后开始请求今日的启动图,默认显示本地的一张,请求成功则显示新的那一张,总时间为5秒
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding,SplashPresenter> implements SplashContract.View{

    @Override
    protected boolean setOtherTheme() {
        setTheme(R.style.FullScreenTheme);
        return true;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }


    @Override
    protected SplashPresenter setPresenter() {
        return new SplashPresenter(SplashActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PermissionsActivity.PERMISSIONS_DENIED == resultCode) {
            //权限未被授予，退出应用
            finish();
        } else if (PermissionsActivity.PERMISSIONS_GRANTED == resultCode) {
            //权限被授予
            mPresenter.permissionsGranted();
        }
    }


    //请求得到了广告图的URL, 使用Glide展示
    @Override
    public void showNewSplashImage(GankBeautyResult gankResult) {
        Glide.with(SplashActivity.this).load(gankResult.beauties.get(0).url)
                .error(R.drawable.splashscreen)
                .placeholder(R.drawable.splashscreen)
                .crossFade()
                .into(mBinding.splashImg);
    }

    //回调图片剩余显示时间
    @Override
    public void showRemainingTime(int seconds) {

    }

    //跳转到权限管理Activity
    @Override
    public void toPermissionActivityForResult(int requestCode, String[] permissions) {
        PermissionsActivity.startActivityForResult(this, requestCode, permissions);
    }

    //跳转到MainActivity
    @Override
    public void toMainActivityThenFinish() {
        startActivity(MainActivity.newIntent(SplashActivity.this));
        finish();
    }
}
