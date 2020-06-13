package com.yazao.lib.base;

import android.os.Bundle;

import com.yazao.lib.net.NetUtil;
import com.yazao.lib.xbase.WBaseActivity;

public abstract class BaseActivity extends WBaseActivity {
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
}
