package com.yazao.lib.xbase;

import android.os.Bundle;

import androidx.databinding.ViewDataBinding;

public abstract class BaseFragment<DB extends ViewDataBinding> extends WBaseFragment<DB> {
    @Override
    protected void getBundleArguments(Bundle arguments) {

    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected boolean isFitDarkMode() {
        return false;
    }

    @Override
    protected boolean isFitDataBinding() {
        return false;
    }
}
