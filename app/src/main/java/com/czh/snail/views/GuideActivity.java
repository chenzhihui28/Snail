package com.czh.snail.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.czh.snail.R;


/**
 * 引导页
 */
public class GuideActivity extends BaseActivity {


    public static Intent newIntent(Activity activity){
        return new Intent(activity, GuideActivity.class);
    };

    @Override
    protected boolean setOtherTheme() {
        setTheme(R.style.FullScreenTheme);
        return true;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_guide);
    }

}
