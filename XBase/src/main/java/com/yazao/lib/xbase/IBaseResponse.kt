package com.yazao.lib.xbase

interface IBaseResponse<T> {

    fun code(): Int
    fun msg(): String
    fun data(): T
    fun isSuccess(): Boolean
}
