package com.yazao.lib.xbase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yazao.lib.common.Config;
import com.yazao.lib.net.NetChangeObserver;
import com.yazao.lib.net.NetChangeReceiver;
import com.yazao.lib.net.NetChangeReceiverUtil;
import com.yazao.lib.net.NetUtil;
import com.yazao.lib.util.HideSoftInputUtil;
import com.yazao.lib.util.screen.ScreenUtil;

/**
 * 类描述：
 *
 * @author zhaishaoping
 * @data 10/04/2017 10:40 AM
 */

public abstract class WBaseActivity extends AppCompatActivity {

    /**
     * network status
     */
    protected NetChangeObserver mNetChangeObserver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isNoTitle()) {
            /*set it to be no title*/
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (getSupportActionBar()!=null){
//                getSupportActionBar().hide();
//            }
        }
        super.onCreate(savedInstanceState);
        if (isNoStateBar()) {
//			View doctorView =findViewById(android.R.id.content);
            View doctorView = getWindow().getDecorView();
            doctorView.setSystemUiVisibility(View.INVISIBLE);//隐藏状态栏同时Activity会伸展全屏显示
        }
        if (isFullScreen()) {
            /*set it to be full screen*/
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }

        //Bundle
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getBundleExtras(extras);
        }

        //Activity Manager
//        ActivityManager.getInstance().addActivity(this);

        //NetWork Change Listener
        if (mNetChangeObserver == null) {
            mNetChangeObserver = new NetChangeObserver() {

                @Override
                public void onNetConnected(NetUtil.NetType type) {
                    onNetWorkConnected(type);
                }

                @Override
                public void onNetDisConnect() {
                    onNetWorkDisConnected();
                }
            };
        }
        NetChangeReceiver.registerObserver(mNetChangeObserver);
        NetChangeReceiverUtil.getInstance().registerNetworkStateReceiver(this);


        //set layout
        int layoutID = getContentViewLayoutID();
        if (layoutID != 0) {
            setContentView(layoutID);
        } else {
            throw new IllegalArgumentException("You must return a right ContentView Layout Id.");
        }

        //调用今日头条的屏幕适配 //设计图以360dp为标准
        if (Config.IS_TOUTIAO_ADAPTATION) {
            int designWidth = Config.designWidth;
            ScreenUtil.getInstance().setCustomDensity(this, designWidth, getApplication());
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initData();
    }

    //判断网络是否可用
    protected boolean isNetworkAvailable() {
        return NetUtil.getInstance().isNetworkconnected(this);
    }

    protected abstract boolean isNoStateBar();

    protected abstract boolean isFullScreen();

    protected abstract boolean isNoTitle();

    protected abstract void onNetWorkConnected(NetUtil.NetType type);

    protected abstract void onNetWorkDisConnected();

    protected abstract void getBundleExtras(Bundle extras);


    /**
     * Layout Id
     */
    protected abstract int getContentViewLayoutID();

    /**
     * init data
     */
    protected abstract void initData();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNetChangeObserver != null) {
            NetChangeReceiver.unRegisterObserver(mNetChangeObserver);
        }
        NetChangeReceiverUtil.getInstance().unRegisterNetworkStateReceiver(this);
    }

    @Override
    public void finish() {
        super.finish();
//        ActivityManager.getInstance().removeActivity(this);
    }

    /**
     * 处理键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (HideSoftInputUtil.getInstance(this).isShouldHideInput(ev)) {
                HideSoftInputUtil.getInstance(this).hideSoftInput();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
