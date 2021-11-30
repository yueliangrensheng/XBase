package com.yazao.lib.xbase

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.yazao.lib.xnet.observer.NetUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

abstract class BaseActivityKt<DB : ViewDataBinding> : WBaseActivity<DB>(),
    CoroutineScope by MainScope() {

    override fun isNoStateBar(): Boolean = false

    override fun isFullScreen(): Boolean = false

    override fun isNoTitle(): Boolean = true

    override fun onNetWorkConnected(type: NetUtil.NetType?) {}

    override fun onNetWorkDisConnected() {}

    override fun getBundleExtras(extras: Bundle?) {}

    override fun isFitDarkMode(): Boolean = false

    override fun isTransparentStatusBar(): Boolean = false

    override fun isFitDataBinding(): Boolean = true

    override fun initViewsAndEvents() {
    }

    override fun getLayoutIDString(): String = ""
}