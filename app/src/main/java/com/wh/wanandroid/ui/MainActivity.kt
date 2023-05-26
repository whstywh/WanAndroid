package com.wh.wanandroid.ui

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.wh.wanandroid.R
import com.wh.wanandroid.base.BaseActivity
import com.wh.wanandroid.databinding.ActivityMainBinding
import com.wh.wanandroid.utils.viewBinding

class MainActivity : BaseActivity() {

    private val viewBinding by viewBinding(ActivityMainBinding::bind)
    override fun getLayoutID() = R.layout.activity_main

    override fun initView() {
        viewBinding.navView.run {
            setupWithNavController(viewBinding.navHostFragment.getFragment<NavHostFragment>().navController)
        }
    }

    override fun initData() {
    }
}