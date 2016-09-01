package com.czh.snail.views.welfare.welfaredetail;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.detail);
        setSupportActionBar(toolbar);
        getWindow().getEnterTransition().setDuration(500);
        mBinding.imgDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.welfare_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download:
                mPresenter.download();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void showImg(String url) {
        Glide.with(getApplicationContext()).load(url).crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mBinding.imgDetail);
    }

    @Override
    public void showSnack(String message) {
        Snackbar.make(mBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
