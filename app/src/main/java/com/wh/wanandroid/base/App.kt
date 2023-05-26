package com.wh.wanandroid.base

import android.app.Application
import com.facebook.stetho.Stetho
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.wh.wanandroid.R


/**
 * 2023/2/18
 * wh
 * desc：
 */
class App : Application() {

    companion object {
        private var instance: Application? = null
        fun instance() = instance!!

        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.black, R.color.white)
                ClassicsHeader(context)
            }
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
                ClassicsFooter(
                    context
                ).setDrawableSize(20f)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //Stetho
        Stetho.initializeWithDefaults(this)
    }
}