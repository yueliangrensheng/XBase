package com.yazao.lib.xxbase

import com.yazao.lib.xbase.BaseActivityKt
import com.yazao.lib.xbase.demo.R
import com.yazao.lib.xbase.demo.databinding.ActivityMainOneBinding

class MainActivityKt : BaseActivityKt<ActivityMainOneBinding>() {
    override fun getLayoutID(): Int = R.layout.activity_main_one

    override fun initData() {

    }
}