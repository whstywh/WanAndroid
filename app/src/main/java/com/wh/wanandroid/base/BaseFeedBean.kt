package com.wh.wanandroid.base

data class BaseFeedBean<out T>(var errorCode: Int, val errorMsg: String, val data: T)