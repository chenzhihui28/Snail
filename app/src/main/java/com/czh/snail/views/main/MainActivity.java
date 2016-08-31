package com.czh.snail.views.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.czh.snail.R;
import com.czh.snail.adapters.MainPagerAdapter;
import com.czh.snail.base.BaseActivity;
import com.czh.snail.databinding.ActivityMainBinding;
import com.czh.snail.model.SingData;
import com.czh.snail.utils.MaterialTheme;
import com.czh.snail.views.DemoFragment;
import com.czh.snail.views.SetThemeActivity;
import com.czh.snail.views.knowledge.KnowledgeFragment;
import com.czh.snail.views.welfare.welfarelist.WelfareFragment;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainPresenter> {

    /**
     * 由于MainActivity的launchMode是SingleTask,所以想要退出程序
     * ,先打开MainActivity清除栈顶Activity然后再将自身finish达到退出程序的目的
     */
    public static final String FINISHAPP = "finishapp";
    public static final String FINISHMAINACTIVITY = "finishmainactivity";//用于切换主题时为了重新创建MainActivity
    private MainPagerAdapter adapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private boolean backPressTwice = false;//用于点击两次返回键退出程序
    public static final int[] TABS = {R.string.tab1, R.string.tab2, R.string.tab3};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public static Intent newIntent(Activity activity) {
        return new Intent(activity, MainActivity.class);
    }

    //MainActivity为singletask,所以先跳转到MainActivity清除栈顶然后finish达到彻底退出程序的目的
    public static Intent newIntent(Activity activity, boolean finishApp) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(FINISHAPP, finishApp);
        return intent;
    }

    //用于切换主题颜色 TODO 可优化,使用recreate方法方便一点
    public static Intent newIntent(Activity activity, MaterialTheme materialTheme) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(BaseActivity.KEY_ARG_CURRENT_THEME, materialTheme);
        return intent;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra(FINISHAPP, false)) {
            finish();
        }
        initToolbar();
        initBottomNavigation();
        initViewPager();
        mBinding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SetThemeActivity.newIntent(MainActivity.this));
            }
        });

    }

    private void initViewPager() {
        fragments.clear();
        fragments.add(KnowledgeFragment.newInstance());
        fragments.add(WelfareFragment.newInstance());
        fragments.add(DemoFragment.newInstance(1));
        mBinding.viewPager.setOffscreenPageLimit(4);
        adapter = new MainPagerAdapter(getSupportFragmentManager(),fragments);
        mBinding.viewPager.setAdapter(adapter);
    }

    private void initBottomNavigation() {
        bottomNavigationItems.add(new AHBottomNavigationItem(getString(TABS[0]), R.drawable.ic_maps_local_attraction));
        bottomNavigationItems.add(new AHBottomNavigationItem(getString(TABS[1]), R.drawable.ic_maps_local_bar));
        bottomNavigationItems.add(new AHBottomNavigationItem(getString(TABS[2]), R.drawable.ic_maps_local_restaurant));
        mBinding.bottomNavigation.addItems(bottomNavigationItems);
        mBinding.bottomNavigation.setColored(false);
        mBinding.bottomNavigation.setAccentColor(ContextCompat.getColor(this
                , SingData.getInstance().getCurrentTheme().getColorResId()));
        mBinding.bottomNavigation.setInactiveColor(ContextCompat.getColor(this, R.color.material_gray_400));
        mBinding.bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                mBinding.viewPager.setCurrentItem(position, false);
                mBinding.toolbar.setTitle(TABS[position]);
                return true;
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mBinding.toolbar.setTitle(TABS[0]);
        setSupportActionBar(toolbar);

    }

    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    @Override
    protected MainPresenter setPresenter() {
        return null;
    }

    @Subscriber(tag = FINISHMAINACTIVITY)
    private void finishmainactivity(String msg) {
        finish();
    }


    @Override
    public void onBackPressed() {
        if (backPressTwice) {
            finish();
        } else {
            backPressTwice = true;
            Snackbar.make(mBinding.bottomNavigation, R.string.click_one_more_time_to_exit, 2000).show();
            Observable.timer(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    backPressTwice = false;
                }
            });
        }

    }
}
