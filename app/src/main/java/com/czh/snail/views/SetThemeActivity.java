package com.czh.snail.views;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.czh.snail.R;
import com.czh.snail.adapters.ThemeListAdapter;
import com.czh.snail.base.BaseRecyclerAdapter;
import com.czh.snail.databinding.ActivitySetThemeBinding;
import com.czh.snail.utils.MaterialTheme;
import com.czh.snail.utils.SingData;

import org.simple.eventbus.EventBus;

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
                EventBus.getDefault().post(getClass().getSimpleName(), MainActivity.FINISHMAINACTIVITY);
                MaterialTheme newTheme = MaterialTheme.getThemeList().get(position);
                if (!SingData.getInstance().getCurrentTheme().equals(newTheme)) {
                    Intent intent = MainActivity.newIntent(SetThemeActivity.this, newTheme);
                    //new task是因为切换主题必须要activity重新创建,所以要先将当前的mainactivity实例结束,然后new task重新创建一个
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}
