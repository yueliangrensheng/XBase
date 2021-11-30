package com.yazao.demo.xbase

import com.yazao.lib.xbase.BaseActivityKt
import com.yazao.lib.xbase.demo.R
import com.yazao.lib.xbase.demo.databinding.ActivityMainDataBindingKotlinBinding

class MainDBActivityKt : BaseActivityKt<ActivityMainDataBindingKotlinBinding>() {
    override fun getLayoutID(): Int = R.layout.activity_main_data_binding_kotlin

    override fun getLayoutIDString(): String {
        return "activity_main_data_binding_kotlin"
    }

    override fun initData() {
        mDataBinding.apply {
            textContent = "DataBinding Kotlin"
        }
    }
}