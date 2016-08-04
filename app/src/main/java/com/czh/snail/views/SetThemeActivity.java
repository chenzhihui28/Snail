package com.czh.snail.views;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.czh.snail.R;
import com.czh.snail.adapters.ThemeListAdapter;
import com.czh.snail.base.BaseRecyclerAdapter;
import com.czh.snail.databinding.ActivitySetThemeBinding;
import com.czh.snail.utils.MaterialTheme;
import com.czh.snail.utils.SingData;

import org.simple.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

public class SetThemeActivity extends BaseActivity {

    private ActivitySetThemeBinding mBinding;
    private LinearLayoutManager mLinearLayoutManager;
    private ThemeListAdapter mThemeListAdapter;

    public static Intent newIntent(Activity activity){
        return new Intent(activity, SetThemeActivity.class);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_set_theme);
        mBinding.recyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setLayoutManager(mLinearLayoutManager);
        mThemeListAdapter = new ThemeListAdapter(this, R.layout.layout_item_theme);
        mThemeListAdapter.setDatas(MaterialTheme.getThemeList());
        mBinding.recyclerView.setAdapter(mThemeListAdapter);
        mThemeListAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                final MaterialTheme newTheme = MaterialTheme.getThemeList().get(position);
                if (!SingData.getInstance().getCurrentTheme().equals(newTheme)) {
                    EventBus.getDefault().post(getClass().getSimpleName(), MainActivity.FINISHMAINACTIVITY);
                    Observable.timer(100, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            Intent intent = MainActivity.newIntent(SetThemeActivity.this, newTheme);
                            startActivity(intent);
                            finish();
                        }
                    });
                }else {
                    Snackbar.make(mBinding.recyclerView, R.string.this_is_your_current_theme,
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
