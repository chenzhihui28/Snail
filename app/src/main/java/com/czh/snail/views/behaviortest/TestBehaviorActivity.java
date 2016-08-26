package com.czh.snail.views.behaviortest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.czh.snail.R;
import com.czh.snail.base.BaseActivity;
import com.czh.snail.databinding.ActivityTestBinding;
import com.czh.snail.views.splash.SplashPresenter;


/**
 * 引导页
 */
public class TestBehaviorActivity extends BaseActivity<ActivityTestBinding,SplashPresenter> {


    public static Intent newIntent(Activity activity){
        return new Intent(activity, TestBehaviorActivity.class);
    }



    @Override
    protected SplashPresenter setPresenter() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        mBinding.btnCurrent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ViewCompat.offsetTopAndBottom(view,-20);
//            }
//        });
    }

}
