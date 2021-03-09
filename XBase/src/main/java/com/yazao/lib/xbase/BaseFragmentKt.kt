package com.yazao.lib.xbase

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

abstract class BaseFragmentKt<DB : ViewDataBinding> : WBaseFragment<DB>(), CoroutineScope by MainScope() {
    override fun getBundleArguments(arguments: Bundle) {}
    override fun onFirstUserVisible() {}
    override fun onUserVisible() {}
    override fun onUserInvisible() {}
    override fun getBundleExtras(extras: Bundle) {}

    override fun isFitDarkMode(): Boolean = false
    override fun isFitDataBinding(): Boolean = true
}