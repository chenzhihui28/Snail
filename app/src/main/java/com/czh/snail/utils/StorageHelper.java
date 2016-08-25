package com.czh.snail.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

public class StorageHelper {
    private static final String TAG = StorageHelper.class.getSimpleName();
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String IMAGE_CACHE_FLOAD_NAME = "Image";
    private static final String VOICE_CACHE_FLOAD_NAME = "Voice";
    private static final String FILE_CACHE_FLOAD_NAME = "File";


    /**
     * 获取缓存根目录
     *
     * @return
     */
    public static String getCacheDirectory(Context context) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) {
            externalStorageState = "";
        }
        if (MEDIA_MOUNTED.equals(externalStorageState)
                && hasExternalStoragePermission(context)) {//判断是否加载，或者是否有权限
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();// data/data/package-name/cache
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName()
                    + "/cache/";
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir.getAbsolutePath() + File.separator;
    }

    /**
     * 如果是4.0以前的机子，直接获取/创建外部存储下与包名相关的缓存路径
     * 如果是4.4以后的机子，
     * 从secondary external storage只能对应用包名相关的目录进行写操作，
     * android.permission.WRITE_EXTERNAL_STORAGE是对Primary storage读写的权限，不对secondary external storage读写生效
     *
     * @param context
     * @return
     */
    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(
                Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(
                new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                L.e(TAG,"Unable to create external cache directory");
                return null;
            }
            try {
                boolean created = new File(appCacheDir, ".nomediatest").createNewFile();
                if (!created) {
                    L.e(TAG,"Create File Failed");
                }
            } catch (IOException e) {
                L.e(TAG,"Can't create \".nomediatest\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    /**
     * 是否有权限
     *
     * @param context
     * @return
     */
    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context
                .checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }


    public static String getImageCachePath() {
        return new StringBuilder().append(getCacheDirectory(MyApplication.getContext()))
                .append(IMAGE_CACHE_FLOAD_NAME).append(File.separator)
                .toString();
    }

    public static String getVoiceCachePath() {
        return new StringBuilder().append(getCacheDirectory(MyApplication.getContext()))
                .append(VOICE_CACHE_FLOAD_NAME).append(File.separator)
                .toString();
    }

    public static String getFileCachePath() {
        return new StringBuilder().append(getCacheDirectory(MyApplication.getContext()))
                .append(FILE_CACHE_FLOAD_NAME).append(File.separator)
                .toString();
    }
}
