package com.wh.wanandroid

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.wh.wanandroid.base.BaseActivity
import com.wh.wanandroid.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {

        binding.navView.getOrCreateBadge(R.id.page_1).apply {
            isVisible = true
            number = 99
        }
        binding.navView.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    binding.navView.getBadge(R.id.page_1)?.run {
                        isVisible = false
                        clearNumber()
                    }
                }
            }
        }
        val navHostFragment = binding.navHostFragment.getFragment<NavHostFragment>()
        binding.navView.setupWithNavController(navHostFragment.navController)
    }

    override fun initData() {
    }
}