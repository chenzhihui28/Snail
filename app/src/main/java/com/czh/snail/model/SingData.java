package com.czh.snail.model;

import android.text.TextUtils;

import com.czh.snail.utils.Constants;
import com.czh.snail.utils.MaterialTheme;
import com.czh.snail.utils.SharepreferenceUtils;
import com.google.gson.Gson;

/**
 * Created by chenzhihui on 16/7/22.
 * 用于存储一些常用信息,用于简单的内存缓存
 */
public class SingData {
    private SingData() {
    }

    private static final class SingDataHelper {
        public static final SingData INSTANCE = new SingData();
    }

    private static SingData mInstance = SingDataHelper.INSTANCE;

    public static SingData getInstance() {
        return mInstance;
    }

    private MaterialTheme currentTheme;

    public MaterialTheme getCurrentTheme() {
        if (currentTheme == null) {
            //内存中保存的主题为空,则从Sharepreference文件中读取,若还是没有则为蓝色主题
            String savedThemeString = SharepreferenceUtils.getString(Constants.SharePreferenceAttr.CURRENTTHEME, "");
            if (TextUtils.isEmpty(savedThemeString)) {
                //默认主题为蓝色
                currentTheme = MaterialTheme.THEME_BLUE;
            } else {
                try {
                    MaterialTheme theme = new Gson().fromJson(savedThemeString, MaterialTheme.class);
                    if (theme != null) {
                        currentTheme = theme;
                    } else {
                        currentTheme = MaterialTheme.THEME_BLUE;
                    }
                } catch (Exception e) {
                    currentTheme = MaterialTheme.THEME_BLUE;
                }
            }

        }
        return currentTheme;
    }

    public void setCurrentTheme(MaterialTheme currentTheme) {
        this.currentTheme = currentTheme;
        //将主题设置保存到配置文件中,之后配置文件要改成按照账号划分文件的形式
        SharepreferenceUtils.saveString(Constants.SharePreferenceAttr.CURRENTTHEME
                , new Gson().toJson(currentTheme));
    }


}
