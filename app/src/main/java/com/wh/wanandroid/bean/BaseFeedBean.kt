package com.wh.wanandroid.bean

data class BaseFeedBean<out T>(var errorCode: Int, val errorMsg: String, val data: T)