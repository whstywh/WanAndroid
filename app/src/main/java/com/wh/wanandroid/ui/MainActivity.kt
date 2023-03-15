package com.wh.wanandroid.ui

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.wh.wanandroid.base.BaseActivity
import com.wh.wanandroid.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
        binding.navView.run {
            val navHostFragment = binding.navHostFragment.getFragment<NavHostFragment>()
            setupWithNavController(navHostFragment.navController)
        }
    }

    override fun initData() {
    }
}