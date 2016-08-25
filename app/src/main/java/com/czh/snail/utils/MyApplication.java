package com.czh.snail.utils;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.czh.snail.BuildConfig;
import com.czh.snail.views.CustomErrorActivity;
import com.nshmura.strictmodenotifier.StrictModeNotifier;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

/**
 * Created by chenzhihui on 16/7/22.
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;//全局上下文获取
//        LeakCanary.install(this);//内存泄漏检测
//        异常捕获
        CustomActivityOnCrash.setErrorActivityClass(CustomErrorActivity.class);
        CustomActivityOnCrash.install(this);


        //开启strictmode 废弃,用下面的开源库,增加了notification的提醒更加便利
//        if (BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
//        }


        //我们并不想它总是激活着，因此我们只在debug构建时使用。这种配置将报告每一种关于主线程用法的违规做法
        // ，并且这些做法都可能与内存泄露有关：Activities、BroadcastReceivers、Sqlite等对象。
        //选择了penaltyLog()，当违规做法发生时，StrictMode将会在logcat打印一条信息。
        // StrictMode帮助我们侦测敏感的活动，如我们无意的在主线程执行磁盘访问或者网络调用。正如你所知道的
        // ，在主线程执行繁重的或者长时的任务是不可取的。因为Android应用的主线程时UI线程，它被用来处理和UI相关的操作：这也是获得更平滑的动画体验和响应式App的唯一方法
        //开启StrictMode https://github.com/nshmura/strictmode-notifier
        if (BuildConfig.DEBUG) {
            //setup this library
            StrictModeNotifier.install(this);
            //setup StrictMode.
            // penaltyLog() should be called for strictmode-notifier
            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .permitDiskReads()
                    .permitDiskWrites()
                    .penaltyLog() // Must!
                    .build();
            StrictMode.setThreadPolicy(threadPolicy);

            StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog() // Must!
                    .build();
            StrictMode.setVmPolicy(vmPolicy);

        }


    }


    public static Context getContext() {
        return mContext;
    }
}
