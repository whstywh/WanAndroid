package com.wh.wanandroid.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.wh.wanandroid.databinding.ActivityMainBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        if (this::binding.isInitialized) {
            setContentView(binding.root)
        }
        initView()
        initData()
    }

    abstract fun getViewBinding(): VB
    abstract fun initView()
    abstract fun initData()
}