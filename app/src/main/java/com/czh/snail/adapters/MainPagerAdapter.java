package com.czh.snail.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.czh.snail.views.DemoFragment;
import com.czh.snail.views.knowledge.KnowledgeFragment;
import com.czh.snail.views.welfare.welfarelist.WelfareFragment;

import java.util.ArrayList;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Fragment currentFragment;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.clear();
        fragments.add(DemoFragment.newInstance(1));
        fragments.add(WelfareFragment.newInstance());
        fragments.add(KnowledgeFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }
}