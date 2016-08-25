package com.czh.snail.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.czh.snail.R;
import com.czh.snail.base.BaseActivity;
import com.czh.snail.base.BasePresenter;


/**
 * 引导页
 */
public class GuideActivity extends BaseActivity {


    public static Intent newIntent(Activity activity){
        return new Intent(activity, GuideActivity.class);
    }

    @Override
    protected boolean setOtherTheme() {
        setTheme(R.style.FullScreenTheme);
        return true;
    }


    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

}
