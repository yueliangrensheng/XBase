package com.yazao.lib.xbase;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.yazao.lib.xlog.Log;

/**
 * Created by zhaishaoping on 06/04/2017.
 */

public class WBaseApplication extends Application {

    protected static Context mContext;
    protected static WBaseApplication sInstance;
    protected boolean isDebugModel;//是否是debug模式

    public static Context getContext() {
        return mContext;
    }

    public static WBaseApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        sInstance = this;
        init();
    }

    private void init() {
        //isDebug
        isDebugModel = sInstance.getApplicationInfo() != null && (sInstance.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        //Log日志
        Log.init().setLogLevel(/* BuildConfig.DEBUG */  isDebugModel ? Log.LogLevel.FULL : Log.LogLevel.NONE).setMethodCount(2).hideThreadInfo();
    }

}
