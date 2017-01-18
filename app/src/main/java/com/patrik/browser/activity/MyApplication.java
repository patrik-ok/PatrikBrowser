package com.patrik.browser.activity;

import android.app.Application;
import android.content.Context;

import com.patrik.browser.BuildConfig;
import com.patrik.browser.tool.LogUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Application
 * Create by patrik on 2016/8/29.
 */
public class MyApplication extends Application {
    private static Context mAppContext = null;
    private static final Executor mIOThread = Executors.newSingleThreadExecutor();
    private static final Executor mTaskThread = Executors.newCachedThreadPool();
    public static Executor getIOThread() {
        return mIOThread;
    }

    public static Executor getTaskThread() {
        return mTaskThread;
    }
    public static Context getAppContext() {
        return mAppContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        if (mAppContext == null) {
            mAppContext = this.getBaseContext();
        }
        if(BuildConfig.APP_MODE != 1){
            LogUtils.setDebug(true);
        }else{
            LogUtils.setDebug(false);
        }
    }
}
