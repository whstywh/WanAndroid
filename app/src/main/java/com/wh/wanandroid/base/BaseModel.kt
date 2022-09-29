package com.wh.wanandroid.base

data class BaseModel<out T>(var errorCode: Int, val errorMsg: String, val data: T)