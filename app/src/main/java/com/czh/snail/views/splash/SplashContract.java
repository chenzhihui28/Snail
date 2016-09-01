package com.czh.snail.views.splash;

import com.czh.snail.base.BasePresenter;
import com.czh.snail.model.beans.GankResult;

/**
 * Created by chenzhihui on 2016/8/8.
 * 启动页契约类
 */
public interface SplashContract {
    interface View {
        void showNewSplashImage(GankResult gankResult);
        void showRemainingTime(int seconds);
        void toPermissionActivityForResult(int requestCode,String[] permissions);
        void toMainActivityThenFinish();

    }

    interface Presenter extends BasePresenter {
        void permissionsGranted();//权限被授予
    }
}
