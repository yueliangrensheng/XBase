package com.yazao.lib.xbase;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;

/**
 * 类描述：Fragment基类
 *
 * @author zhaishaoping
 * @data 10/04/2017 5:18 PM
 */

public abstract class WBaseFragment<DB extends ViewDataBinding> extends Fragment {


    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isPrepared;
    protected View view;
    protected DB mDataBinding = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bundle
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            getBundleExtras(extras);
        }
        Bundle arguments = getArguments();
        if (arguments != null) {
            getBundleArguments(arguments);
        }

        requestNetData();
    }

    protected abstract void getBundleArguments(Bundle arguments);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        //设置Fragment状态栏透明
//        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
//            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        }else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
//            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }

        int layoutID = getLayoutID();
        view = null;
        if (layoutID != 0) {
            if (isFitDataBinding()) {
                mDataBinding = DataBindingUtil.inflate(inflater, layoutID, container, false);
                mDataBinding.setLifecycleOwner(getViewLifecycleOwner());
                view = mDataBinding.getRoot();

            } else {
                view = inflater.inflate(layoutID, null);
            }
        } else {
            throw new IllegalArgumentException("You must return a right ContentView Layout Id.");
        }

        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewsAndEvents();
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

    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        // for bug ---> java.lang.IllegalStateException: Activity has been destroyed
//        try {
//            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
//            childFragmentManager.setAccessible(true);
//            childFragmentManager.set(this, null);
//
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
        if (mDataBinding != null) {
            mDataBinding = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        onVisible(false);
    }

    @Override
    public void onResume() {
        super.onResume();

        onVisible(true);
    }

    private void onVisible(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    /**
     * when fragment is visible for the first time, here we can do some initialized work or refresh data only once
     */
    protected abstract void onFirstUserVisible();

    /**
     * this method like the fragment's lifecycle method onResume()
     */
    protected abstract void onUserVisible();

    /**
     * when fragment is invisible for the first time
     */
    protected void onFirstUserInvisible() {
        // here we do not recommend do something
    }

    /**
     * this method like the fragment's lifecycle method onPause()
     */
    protected abstract void onUserInvisible();


    protected abstract void getBundleExtras(Bundle extras);


    protected abstract int getLayoutID();

    protected abstract void initViewsAndEvents();

    /**
     * 是否需要暗黑模式
     *
     * @return
     */
    protected abstract boolean isFitDarkMode();

    /**
     * 是否需要数据绑定
     *
     * @return true: Fragment的 onCreateView() 返回默认值；false：返回 getLayoutID()的布局
     */
    protected abstract boolean isFitDataBinding();

    /**
     * Fragment生命周期中，该方法只会执行一次，可用于进入页面时的网络请求
     */
    protected abstract void requestNetData();
}
