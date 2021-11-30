package com.yazao.lib.xbase;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

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

    protected View rootView;

    protected WBaseActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.mContext = this;
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
//                rootView = LayoutInflater.from(this).inflate(isFitDataBinding() ? R.layout.yz_activity_root_layout2 : R.layout.yz_activity_root_layout, null, false);

                /**
                 * 在  R.layout.yz_activity_root_layout2 的 xml布局下，重新动态整合 layoutID 的布局到 yz_activity_root_layout2下面
                 *
                 * 然后再通过DataBindingUtil
                 */
//                int newLayoutId = R.layout.yz_activity_root_layout2;
//                mDataBinding = DataBindingUtil.bind(rootView);

//                WBaseUtil.ReIntegrateLayout();


                mDataBinding = DataBindingUtil.setContentView(this, layoutID);
                if (mDataBinding != null) {
                    mDataBinding.setLifecycleOwner(this);
                } else {
                    throw new RuntimeException("Layout must be converted to data binding");
                }
            } else {
                setContentView(layoutID);
            }

        } else {
            throw new IllegalArgumentException("You must return a right ContentView Layout Id.");
        }

        initViewsAndEvents();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setContentView(int layoutResID) {
        View contentView = LayoutInflater.from(this).inflate(layoutResID, null, false);
        setContentView(contentView);
    }

    @Override
    public void setContentView(View view) {
        setContentViewData(view);
        super.setContentView(rootView);
    }

    private void setContentViewData(View view) {

        // 处理view 进行包裹
        rootView = LayoutInflater.from(this).inflate(/* isFitDataBinding() ? R.layout.yz_activity_root_layout2 :*/ R.layout.yz_activity_root_layout, null, false);
        FrameLayout contentParent = rootView.findViewById(R.id.yz_content_parent);
        if (isFitDataBinding()) {
            contentParent.setId(android.R.id.content);
            if (view != null) {
                String tag = "layout/" + getLayoutIDString() + "_0";
                view.setTag(tag);
                rootView.setTag(tag);
            }
        }
        contentParent.removeAllViews();
        if (view != null) {
            contentParent.addView(view);
        }

        //设置Toolbar
        Toolbar toolbar = rootView.findViewById(R.id.yz_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }

    /**
     * 前提是 isFitDataBinding() = true, 然后这个方法返回 Activity的布局的String形式。
     * 比如： R.layout.activity_main_data_binding;   辣么这个方法将返回"activity_main_data_binding"
     * 还有一个重要的前提是：
     *
     * 【自己约定】
     * 由于 R.layout.yz_activity_root_layout 的布局的根View是 androidx.constraintlayout.widget.ConstraintLayout ，
     * 所以 所有使用DataBinding时的布局根view必须也是 androidx.constraintlayout.widget.ConstraintLayout
     * @return
     */
    protected abstract String getLayoutIDString();


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
     * init data [Called when activity start-up is complete (after onStart and onRestoreInstanceState have been called)]
     */
    protected abstract void initData();

    /**
     * init data [Called in activity's onCreate()]
     */
    protected abstract void initViewsAndEvents();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNetChangeObserver != null) {
            NetChangeReceiver.unRegisterObserver(mNetChangeObserver);
        }
        NetChangeReceiverUtil.getInstance().unRegisterNetworkStateReceiver(this);

        if (mDataBinding != null) {
            mDataBinding = null;
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

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
