package com.czh.snail.views.welfare.welfaredetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.czh.snail.R;
import com.czh.snail.base.BaseActivity;
import com.czh.snail.databinding.ActivityWelfareDetailBinding;


public class WelFareDetailActivity extends BaseActivity<ActivityWelfareDetailBinding
        , WelfareDetailPresenter> implements WelfareDetailContract.View {
    public static final String INTENT_URL = "intent_detail_url";

    public static Intent newIntent(Activity activity, String url) {
        Intent intent = new Intent(activity, WelFareDetailActivity.class);
        intent.putExtra(INTENT_URL, url);
        return intent;
    }


    @Override
    protected WelfareDetailPresenter setPresenter() {
        return new WelfareDetailPresenter(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_welfare_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra(INTENT_URL);
        if (!TextUtils.isEmpty(url)) {
            Glide.with(this).load(url).crossFade().placeholder(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.mipmap.ic_launcher)
                    .into(mBinding.imgDetail);
        }
    }

}
