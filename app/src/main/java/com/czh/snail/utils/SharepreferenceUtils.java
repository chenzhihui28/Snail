package com.czh.snail.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.lang.reflect.Method;

/**
 * Created by chenzhihui on 16/7/26.
 */
public class SharepreferenceUtils {
    private static final String ATTR = Constants.SharePreferenceName;
    private static final Context mContext = MyApplication.getContext();


    /**
     * 判断是否第一次启动应用
     * @return boolean
     */
    public static boolean isFirstStartApp() {
        SharedPreferences sp = mContext.getSharedPreferences(ATTR, mContext.MODE_PRIVATE);
        return sp.getBoolean(Constants.SharePreferenceAttr.FIRSTSTARTAFTERINSTALL, true);
    }

    public static void setIsFirestStart(boolean flag) {
        SharedPreferences sp = mContext.getSharedPreferences(ATTR, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Constants.SharePreferenceAttr.FIRSTSTARTAFTERINSTALL, false);
        SharedPreferencesCompat.apply(editor);
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         * @return Method
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
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
