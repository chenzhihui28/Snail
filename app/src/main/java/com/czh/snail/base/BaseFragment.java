package com.czh.snail.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.czh.snail.utils.L;

import org.simple.eventbus.EventBus;


/**
 * Created by chenzhihui on 2015/8/17 0017.
 */
public abstract class BaseFragment<T extends ViewDataBinding, P extends BasePresenter> extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();
    protected boolean isPrepared;//记录是否已经set Content View,否则不可以操作UI
    protected T mBinding;
    protected P mPresenter;
    protected Activity mParentActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (registerEventBus()) {
            EventBus.getDefault().register(this);
        }
        mBinding = DataBindingUtil.inflate(inflater, getContentViewLayoutID(), container, false);
        initView();
        isPrepared = true;
        mPresenter = setPresenter();
        mParentActivity = getActivity();
        if (mPresenter != null) {
            mPresenter.start();
        }
        return mBinding.getRoot();
    }

    /**
     * 用于设置监听等工作
     */
    protected abstract void initView();

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
