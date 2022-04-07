package com.yazao.lib.xbase;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.yazao.lib.util.activity.ActivityManager;
import com.yazao.lib.xnet.observer.NetChangeObserver;
import com.yazao.lib.xnet.observer.NetChangeReceiver;
import com.yazao.lib.xnet.observer.NetChangeReceiverUtil;
import com.yazao.lib.xnet.observer.NetUtil;
import com.yazao.lib.util.HideSoftInputUtil;

/**
 * 类描述：Activity基类
 *
 * @author zhaishaoping
 * @data 10/04/2017 10:40 AM
 */

public abstract class WBaseActivity<DB extends ViewDataBinding> extends AppCompatActivity {

    /**
     * network status
     */
    protected NetChangeObserver mNetChangeObserver = null;

    protected DB mDataBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isNoTitle()) {
            /*set it to be no title*/
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        }
        super.onCreate(savedInstanceState);
        if (isNoStateBar()) {
//			View doctorView =findViewById(android.R.id.content);
            View doctorView = getWindow().getDecorView();
            doctorView.setSystemUiVisibility(View.INVISIBLE);//隐藏状态栏同时Activity会伸展全屏显示
        } else {
            if (isTransparentStatusBar()) {//透明状态栏
                transparentStatusBar();
            }
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
        int layoutID = getLayoutID();
        if (layoutID != 0) {
            if (isFitDataBinding()) {
                mDataBinding = DataBindingUtil.setContentView(this, layoutID);
                mDataBinding.setLifecycleOwner(this);
            } else {
                setContentView(layoutID);
            }

        } else {
            throw new IllegalArgumentException("You must return a right ContentView Layout Id.");
        }

        // activityManager
        ActivityManager.getInstance().addActivity(this);
    }

    /**
     * 透明状态栏
     */
    private void transparentStatusBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initData();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //是否需要暗黑模式
        if (isFitDarkMode()) {
            int result = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (result) {
                case Configuration.UI_MODE_NIGHT_YES:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                case Configuration.UI_MODE_NIGHT_NO:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;

            }
        }
    }


    //判断网络是否可用
    protected boolean isNetworkAvailable() {
        return NetUtil.getInstance().isNetworkconnected(this);
    }

    /**
     * 是否需要透明状态栏
     *
     * @return
     */
    protected abstract boolean isTransparentStatusBar();

    /**
     * 是否需要数据绑定
     *
     * @return true: Activity不需要手动 setContentView(getLayoutID()), DataBind来完成。false:默认实现，即setContentView(getLayoutID())
     */
    protected abstract boolean isFitDataBinding();

    /**
     * 是否需要暗黑模式
     *
     * @return
     */
    protected abstract boolean isFitDarkMode();

    protected abstract boolean isNoStateBar();

    protected abstract boolean isFullScreen();

    protected abstract boolean isNoTitle();

    protected abstract void onNetWorkConnected(NetUtil.NetType type);

    protected abstract void onNetWorkDisConnected();

    protected abstract void getBundleExtras(Bundle extras);


    /**
     * Layout Id
     */
    protected abstract int getLayoutID();

    /**
     * init data
     */
    protected abstract void initData();


    @Override
    protected void onDestroy() {
        if (mNetChangeObserver != null) {
            NetChangeReceiver.unRegisterObserver(mNetChangeObserver);
        }
        NetChangeReceiverUtil.getInstance().unRegisterNetworkStateReceiver(this);

        if (mDataBinding != null) {
            mDataBinding = null;
        }

        // activityManager
        ActivityManager.getInstance().removeActivity(this);

        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (HideSoftInputUtil.getInstance().isShouldHideInput(this, ev)) {
                HideSoftInputUtil.getInstance().hideSoftInput(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
