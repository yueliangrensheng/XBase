package com.yazao.lib.xbase;

import android.app.Application;
import android.content.Context;

import com.yazao.lib.xlog.BuildConfig;
import com.yazao.lib.xlog.XLog;

/**
 * Created by zhaishaoping on 06/04/2017.
 */

public class WBaseApplication extends Application {

    protected static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        init();
    }

    private void init() {
        //Log日志
        XLog.init().setLogLevel(BuildConfig.DEBUG ? XLog.LogLevel.FULL : XLog.LogLevel.NONE).setMethodCount(2).hideThreadInfo();
    }

}
