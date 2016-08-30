package com.czh.snail.views.welfare.welfaredetail;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.czh.snail.R;
import com.czh.snail.base.BaseActivity;
import com.czh.snail.databinding.ActivityWelfareDetailBinding;


public class WelFareDetailActivity extends BaseActivity<ActivityWelfareDetailBinding
        , WelfareDetailPresenter> implements WelfareDetailContract.View {
    public static final String INTENT_ENTITY = "intent_entity";


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
        getWindow().getEnterTransition().setDuration(500);

        mBinding.imgDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBinding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.download();
            }
        });
    }


    @Override
    public void showImg(String url) {
        Glide.with(this).load(url).crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mBinding.imgDetail);
    }

    @Override
    public void showSnack(int message) {
        Snackbar.make(mBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }
}
