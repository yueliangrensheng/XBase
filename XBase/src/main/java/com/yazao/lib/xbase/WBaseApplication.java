package com.yazao.lib.xbase;

import android.app.Application;
import android.content.Context;

import com.yazao.lib.toast.XToast;
import com.yazao.lib.xlog.BuildConfig;
import com.yazao.lib.xlog.Log;

/**
 * Created by zhaishaoping on 06/04/2017.
 */

public class WBaseApplication extends Application {

    protected static Application instance;

    protected static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = this.getApplicationContext();
        init();
    }

    private void init() {
        //Log日志
        Log.init().setLogLevel(BuildConfig.DEBUG ? Log.LogLevel.FULL : Log.LogLevel.NONE).setMethodCount(2).hideThreadInfo();
        //init xtoast
        XToast.init(this);
    }

}
