package com.czh.snail.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.czh.snail.model.SingData;
import com.czh.snail.utils.MaterialTheme;

import org.simple.eventbus.EventBus;

/**
 * Created by chenzhihui on 16/7/22.
 */
public abstract class BaseActivity<T extends ViewDataBinding, E extends BasePresenter> extends
        AppCompatActivity {
    protected T mBinding;
    protected E mPresenter;
    public static final String KEY_ARG_CURRENT_THEME = "KEY_ARG_CURRENT_THEME";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getIntent().getExtras();
        //用于切换主题,这一段必须要在super.onCreate(savedInstanceState);之前
        if (args != null) {
            MaterialTheme mTheme = (MaterialTheme) args.getSerializable(KEY_ARG_CURRENT_THEME);
            if (mTheme != null) {
                SingData.getInstance().setCurrentTheme(mTheme);
            }
        }
        if (!setOtherTheme()) {
            setTheme(SingData.getInstance().getCurrentTheme().getThemeResId());
        }
        super.onCreate(savedInstanceState);
        //若要使用EventBus则注册EventBus
        if (isUseEventBus()) {
            EventBus.getDefault().register(this);
        }
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            mPresenter = setPresenter();
            mBinding = DataBindingUtil.setContentView(this, getContentViewLayoutID());
            initView(savedInstanceState);
            if (mPresenter != null) {
                mPresenter.start();
            }
        } else {
            throw new RuntimeException("you have to return the layout id!");
        }

    }

    /**
     * 获取Layout的id
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 视图初始化
     */
    protected abstract void initView(Bundle savedInstanceState);


    /**
     * 是否需要注册AndroidEventBus,默认不注册
     */
    protected boolean isUseEventBus() {
        return false;
    }

    /**
     * 是否需要设置其他主题,若要的话则需要重载此方法
     * 并setTheme然后返回true,如启动页需要全屏等
     */
    protected boolean setOtherTheme() {
        return false;
    }




    protected abstract E setPresenter();


    @Override
    protected void onDestroy() {
        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (mPresenter != null) {
            mPresenter.stop();
        }
        super.onDestroy();
    }


}
