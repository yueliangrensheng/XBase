package com.yazao.lib.xbase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import com.yazao.lib.xnet.exception.ExceptionHandler
import com.yazao.lib.xnet.exception.ResponseThrowable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class BaseViewModel(application: Application = WBaseApplication.instance
) : AndroidViewModel(application), LifecycleObserver {

//    val defUI: UIChange by lazy { UIChange() }


    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会
     * 自动调用ViewModel的 onCleared 方法取消所有协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }

    /**
     * 用流的方式进行网络请求
     */
    fun <T> launchFlow(block: suspend () -> T): Flow<T> {
        return flow {
            emit(block())
        }
    }


    /**
     * 不过滤请求结果
     * @param block 请求体
     * @param error 失败回调
     * @param complete 完成回调（无论成功失败都会回调）
     * @param isShowDialog 是否显示加载框
     */
    fun launchGo(
            block: suspend CoroutineScope.() -> Unit,
            error: suspend CoroutineScope.(ResponseThrowable) -> Unit = {

            },
            complete: suspend CoroutineScope.() -> Unit = {},
            isShowDialog: Boolean = true
    ) {
        if (isShowDialog) {
        }

        launchUI {

            handleException(
                    {

                        withContext(Dispatchers.IO) {
                            block
                        }
                    },
                    {
                        error(it)
                    },
                    {
                        complete()
                    }
            )
        }
    }


    private suspend fun handleException(
            block: suspend CoroutineScope.() -> Unit,
            error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
            complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                block()
            } catch (e: Throwable) {
                error(ExceptionHandler.handleException(e))
            } finally {
                complete()
            }
        }

    }
}