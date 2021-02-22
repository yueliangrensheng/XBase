package com.yazao.lib.xbase;

import android.os.Bundle;

import androidx.databinding.ViewDataBinding;

import com.yazao.lib.net.NetUtil;

public abstract class BaseActivity<DB extends ViewDataBinding> extends WBaseActivity<DB> {
    @Override
    protected boolean isNoStateBar() {
        return false;
    }

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected boolean isNoTitle() {
        return false;
    }

    @Override
    protected void onNetWorkConnected(NetUtil.NetType type) {

    }

    @Override
    protected void onNetWorkDisConnected() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected boolean isFitDarkMode() {
        return false;
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }
}
