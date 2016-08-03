package com.czh.snail.utils;

/**
 * Created by chenzhihui on 16/7/22.
 * 用于存储一些常用信息
 */
public class SingData {
    private SingData() {}
    private static SingData mInstance = new SingData();

    public static SingData getInstance() {
        return mInstance;
    }

    private MaterialTheme currentTheme;

    public MaterialTheme getCurrentTheme() {
        if (currentTheme == null) {
            currentTheme = MaterialTheme.THEME_BLUE;
        }
        return currentTheme;
    }

    public void setCurrentTheme(MaterialTheme currentTheme) {
        this.currentTheme = currentTheme;
    }
}
