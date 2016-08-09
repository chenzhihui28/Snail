package com.czh.snail.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.szltech.yuexiuxuncha.R;
import com.szltech.yuexiuxuncha.application.AppApplication;

/**
 * Created by HelloCsl(cslgogogo@gmail.com) on 2016/1/16 0016.
 */
public class SnackUtils {

    public static Snackbar longInfoStyled(View v, int resId) {
        Snackbar snackbar = Snackbar.make(v, resId,
                Snackbar.LENGTH_LONG)
                .setActionTextColor(AppApplication.getContext().getResources().getColor(R.color.snack_info))
                .setActionTextColor(AppApplication.getContext().getResources().getColor(R.color.app_theme_main));
        return snackbar;
    }

    public static Snackbar shortInfoStyled(View v, int resId) {
        Snackbar snackbar = Snackbar.make(v, resId,
                Snackbar.LENGTH_SHORT)
                .setActionTextColor(AppApplication.getContext().getResources().getColor(R.color.snack_info))
                .setActionTextColor(AppApplication.getContext().getResources().getColor(R.color.app_theme_main));
        return snackbar;
    }

    public static Snackbar shortWarnStyled(View v, int resId) {
        Snackbar snackbar = Snackbar.make(v, resId,
                Snackbar.LENGTH_SHORT)
                .setActionTextColor(AppApplication.getContext().getResources().getColor(R.color.snack_warn))
                .setActionTextColor(AppApplication.getContext().getResources().getColor(R.color.app_theme_main));
        return snackbar;
    }

    public static Snackbar longWarnStyled(View v, int resId) {
        Snackbar snackbar = Snackbar.make(v, resId,
                Snackbar.LENGTH_LONG)
                .setActionTextColor(AppApplication.getContext().getResources().getColor(R.color.snack_warn))
                .setActionTextColor(AppApplication.getContext().getResources().getColor(R.color.app_theme_main));
        return snackbar;
    }

    public static Snackbar longErrorStyled(View v, int resId) {
        Snackbar snackbar = Snackbar.make(v, resId,
                Snackbar.LENGTH_LONG)
                .setActionTextColor(AppApplication.getContext().getResources().getColor(R.color.snack_err))
                .setActionTextColor(AppApplication.getContext().getResources().getColor(R.color.app_theme_main));
        return snackbar;
    }

    public static Snackbar shortErrorStyled(View v, int resId) {
        Snackbar snackbar = Snackbar.make(v, resId,
                Snackbar.LENGTH_SHORT)
                .setActionTextColor(AppApplication.getContext().getResources().getColor(R.color.snack_err))
                .setActionTextColor(AppApplication.getContext().getResources().getColor(R.color.app_theme_main));
        return snackbar;
    }

}
