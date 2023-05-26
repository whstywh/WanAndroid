package com.wh.wanandroid.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutID())
        initView()
        initData()
    }

    abstract fun getLayoutID(): Int

    abstract fun initView()
    abstract fun initData()
}