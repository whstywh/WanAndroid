package com.wh.wanandroid.base

import com.wh.wanandroid.bean.BaseFeedBean
import com.wh.wanandroid.net.NetResult
import com.wh.wanandroid.net.DealException
import com.wh.wanandroid.net.ResultException
import kotlinx.coroutines.coroutineScope

open class BaseRepository {

    suspend fun <T : Any> requestTryCatch(
        call: suspend () -> NetResult<T>
    ): NetResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            e.printStackTrace()
            NetResult.Error(DealException.handlerException(e))
        }
    }

    suspend fun <T : Any> handleResponse(
        response: BaseFeedBean<T>
    ): NetResult<T> {
        return coroutineScope {
            if (response.errorCode == 0) {
                NetResult.Success(response.data)
            } else {
                NetResult.Error(ResultException(response.errorCode.toString(), response.errorMsg))
            }
        }
    }
}