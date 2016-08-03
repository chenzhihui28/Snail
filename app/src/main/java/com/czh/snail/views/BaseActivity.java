package com.czh.snail.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.czh.snail.utils.MaterialTheme;
import com.czh.snail.utils.SingData;

import org.simple.eventbus.EventBus;

/**
 * Created by chenzhihui on 16/7/22.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String KEY_ARG_CURRENT_THEME = "KEY_ARG_CURRENT_THEME";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getIntent().getExtras();

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

        if (isUseEventBus()) {
            EventBus.getDefault().register(this);
        }
        initView(savedInstanceState);
    }


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

    @Override
    protected void onDestroy() {
        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}
