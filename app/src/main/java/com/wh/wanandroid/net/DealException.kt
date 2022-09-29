package com.wh.wanandroid.net

import androidx.core.net.ParseException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException
import javax.net.ssl.SSLHandshakeException

object DealException {

    fun handlerException(t: Throwable) = when (t) {
        is ResultException -> {
            t
        }
        is HttpException -> {
            when (t.code()) {
                401,
                403,
                404 ->
                    ResultException(t.code().toString(), "网络错误")
                408,
                504 ->
                    ResultException(t.code().toString(), "网络连接超时")
                500,
                502,
                503 ->
                    ResultException(t.code().toString(), "服务器错误")
                else -> ResultException(t.code().toString(), "网络错误")
            }
        }
        is JsonParseException, is JSONException, is ParseException -> {
            ResultException("1001", "解析错误")
        }
        is SocketException -> {
            ResultException("408", "网络连接错误，请重试")
        }
        is SocketTimeoutException -> {
            ResultException("408", "网络连接超时")
        }
        is SSLHandshakeException -> {
            ResultException("3001", "证书验证失败")
        }
        is UnknownHostException -> {
            ResultException("1003", "网络错误，请切换网络重试")
        }
        is UnknownServiceException -> {
            ResultException("1003", "网络错误，请切换网络重试")
        }
        is NumberFormatException -> {
            ResultException("1003", "数字格式化异常")
        }
        else -> {
            ResultException("1000", "未知错误")
        }
    }
}