package com.czh.snail.views;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.czh.snail.R;
import com.czh.snail.adapters.DemoViewPagerAdapter;
import com.czh.snail.databinding.ActivityMainBinding;
import com.czh.snail.utils.DialogUtils;
import com.czh.snail.utils.MaterialTheme;
import com.czh.snail.utils.SingData;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    /**
     * 由于MainActivity的launchMode是SingleTask,所以想要退出程序
     * ,先打开MainActivity清除栈顶Activity然后再将自身finish达到退出程序的目的
     */
    public static final String FINISHAPP = "finishapp";
    private ActivityMainBinding mBinding;
    private DemoFragment currentFragment;
    private DemoViewPagerAdapter adapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();

    public static Intent newIntent(Activity activity) {
        return new Intent(activity, MainActivity.class);
    }

    //用于先跳转到MainActivity清除栈然后finish达到退出程序的目的
    public static Intent newIntent(Activity activity, boolean isFinishApp) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(FINISHAPP, isFinishApp);
        return intent;
    }

    //用于切换主题颜色
    public static Intent newIntent(Activity activity, MaterialTheme materialTheme){
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
    protected void initView(Bundle savedInstanceState) {
        if(getIntent().getBooleanExtra(FINISHAPP,false)){
            finish();
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab1, R.drawable.ic_maps_local_attraction, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab2, R.drawable.ic_maps_local_bar, R.color.color_tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab3, R.drawable.ic_maps_local_restaurant, R.color.color_tab_3);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        mBinding.bottomNavigation.addItems(bottomNavigationItems);

        int backgroundColor = getResources().getColor(R.color.colorPrimary);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TypedArray array = getTheme().obtainStyledAttributes(new int[]{
                        android.R.attr.colorPrimary
                });
                backgroundColor = array.getColor(0, getResources().getColor(R.color.colorPrimary));
                array.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mBinding.bottomNavigation.setAccentColor(backgroundColor);
        mBinding.bottomNavigation.setInactiveColor(getResources().getColor(R.color.material_gray_400));

        mBinding.bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                currentFragment = adapter.getCurrentFragment();
                if (wasSelected) {
                    currentFragment.refresh();
                    return true;
                }

                if (currentFragment != null) {
                    currentFragment.willBeHidden();
                }

                mBinding.viewPager.setCurrentItem(position, false);
                currentFragment.willBeDisplayed();

                if (position == 1) {
                    mBinding.bottomNavigation.setNotification("", 1);
                    mBinding.floatingActionButton.setVisibility(View.VISIBLE);
                    mBinding.floatingActionButton.setAlpha(0f);
                    mBinding.floatingActionButton.setScaleX(0f);
                    mBinding.floatingActionButton.setScaleY(0f);
                    mBinding.floatingActionButton.animate()
                            .alpha(1)
                            .scaleX(1)
                            .scaleY(1)
                            .setDuration(300)
                            .setInterpolator(new OvershootInterpolator())
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    mBinding.floatingActionButton.animate()
                                            .setInterpolator(new LinearOutSlowInInterpolator())
                                            .start();
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            })
                            .start();

                } else {
                    if (mBinding.floatingActionButton.getVisibility() == View.VISIBLE) {
                        mBinding.floatingActionButton.animate()
                                .alpha(0)
                                .scaleX(0)
                                .scaleY(0)
                                .setDuration(300)
                                .setInterpolator(new LinearOutSlowInInterpolator())
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        mBinding.floatingActionButton.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                        mBinding.floatingActionButton.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                })
                                .start();
                    }
                }

                return true;
            }
        });

        mBinding.bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                Log.d("DemoActivity", "BottomNavigation Position: " + y);
            }
        });

        mBinding.viewPager.setOffscreenPageLimit(4);
        adapter = new DemoViewPagerAdapter(getSupportFragmentManager());
        mBinding.viewPager.setAdapter(adapter);

        currentFragment = adapter.getCurrentFragment();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.bottomNavigation.setNotification("16", 1);
                Snackbar.make(mBinding.bottomNavigation, "Snackbar with bottom navigation",
                        Snackbar.LENGTH_SHORT).show();
            }
        }, 3000);

        mBinding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = SetThemeDialogFragment.newInstance(SingData.getInstance().getCurrentTheme());
                DialogUtils.showDialogFragment(getSupportFragmentManager(), dialogFragment);
            }
        });

    }


    /**
     * Update the bottom navigation colored param
     */
    public void updateBottomNavigationColor(boolean isColored) {
        mBinding.bottomNavigation.setColored(isColored);
    }

    /**
     * Return if the bottom navigation is colored
     */
    public boolean isBottomNavigationColored() {
        return mBinding.bottomNavigation.isColored();
    }

    /**
     * Add or remove items of the bottom navigation
     */
    public void updateBottomNavigationItems(boolean addItems) {

        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getString(R.string.tab4),
                ContextCompat.getDrawable(this, R.drawable.ic_maps_local_bar),
                ContextCompat.getColor(this, R.color.color_tab_4));
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(getString(R.string.tab5),
                ContextCompat.getDrawable(this, R.drawable.ic_maps_place),
                ContextCompat.getColor(this, R.color.color_tab_5));

        if (addItems) {
            mBinding.bottomNavigation.addItem(item4);
            mBinding.bottomNavigation.addItem(item5);
            mBinding.bottomNavigation.setNotification("100+", 3);
        } else {
            mBinding.bottomNavigation.removeAllItems();
            mBinding.bottomNavigation.addItems(bottomNavigationItems);
        }
    }

    /**
     * Show or hide the bottom navigation with animation
     */
    public void showOrHideBottomNavigation(boolean show) {
        if (show) {
            mBinding.bottomNavigation.restoreBottomNavigation(true);
        } else {
            mBinding.bottomNavigation.hideBottomNavigation(true);
        }
    }

    /**
     * Return the number of items in the bottom navigation
     */
    public int getBottomNavigationNbItems() {
        return mBinding.bottomNavigation.getItemsCount();
    }
    
    


}
