package com.czh.snail.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.czh.snail.utils.L;

import org.simple.eventbus.EventBus;

/**
 * 懒加载Fragment，综合是否设置contentView和是否可见两种情况来考虑是否加载数据
 * Created by chenzhihui on 2015/8/31 0031.
 */
public abstract class LazyLoadFragment<T extends ViewDataBinding, P extends BasePresenter> extends Fragment {
    protected boolean isVisible;
    protected boolean isLoaded;

    protected final String TAG = this.getClass().getSimpleName();
    protected boolean isPrepared;//记录是否已经set Content View,否则不可以操作UI
    protected T mBinding;
    protected P mPresenter;
    protected Activity mParentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.i(getClass().getSimpleName()," onCreateView");
        if (registerEventBus()) {
            EventBus.getDefault().register(this);
        }
        mBinding = DataBindingUtil.inflate(inflater, getContentViewLayoutID(), container, false);
        isPrepared = true;
        mPresenter = setPresenter();
        mParentActivity = getActivity();
        initView();
        initData();
        return mBinding.getRoot();
    }

    /**
     * 注意：setUserVisibleHint方法会比onCreateView方法更早地调用，所以不能单纯在onVisiable的时候
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }


    /**
     * 用于设置监听等工作
     */
    protected abstract void initView();


    /**
     * 界面准备好的时候触发,综合是否设置contentView和是否可见两种情况来考虑是否加载数据
     */
    protected final void initData() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isLoaded = true;
        onLazyLoad();
    }

    /**
     * 出现在ViewPager当前页面的时候会触发，综合是否设置contentView和是否可见两种情况来考虑是否加载数据
     */
    protected final void lazyLoad() {
        if (!isPrepared || isLoaded) {
            return;
        }
        isLoaded = true;
        onLazyLoad();
    }

    /**
     * 首次显示的时候调用，用于懒加载数据
     */
    protected void onLazyLoad() {
        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    protected abstract int getContentViewLayoutID();


    protected abstract P setPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (registerEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected void log(String msg) {
        L.d(TAG, msg);
    }


    protected void show(String str) {
        if (getUserVisibleHint()) {
            Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        }
    }

    protected boolean registerEventBus() {
        return false;
    }

}
