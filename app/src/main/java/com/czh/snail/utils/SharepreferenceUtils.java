package com.czh.snail.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Method;

/**
 * Created by chenzhihui on 16/7/26.
 */
public class SharepreferenceUtils {
    private static final String ATTR = getSharepreferenceName();
    private static final Context mContext = MyApplication.getContext();

    /**
     * 之后可以扩展为根据不同账号使用不同的配置文件
     *
     * @return
     */
    private static String getSharepreferenceName() {
        return Constants.SharePreferenceAttr.SharePreferenceName;
    }


    public static void saveString(String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(ATTR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static String getString(String key, String defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(ATTR, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static void saveInt(String key, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(ATTR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static int getInt(String key, int defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(ATTR, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static void saveBoolean(String key, boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(ATTR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(ATTR, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return Method
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }
}
