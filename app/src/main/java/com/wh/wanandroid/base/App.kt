package com.wh.wanandroid.base

import android.app.Application
/**
 * 2023/2/18
 * wh
 * desc：
 */
class App : Application() {

    companion object {
        private var instance: Application? = null
        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}