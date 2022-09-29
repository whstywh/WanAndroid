package com.wh.wanandroid.ui

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.wh.wanandroid.R
import com.wh.wanandroid.base.BaseActivity
import com.wh.wanandroid.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {

        binding.navView.run {
            getOrCreateBadge(R.id.menu_fragment_home).apply {
                isVisible = true
                number = 99
            }

            setOnItemReselectedListener { item ->
                when (item.itemId) {
                    R.id.menu_fragment_home -> {
                        this.getBadge(R.id.menu_fragment_home)?.run {
                            isVisible = false
                            clearNumber()
                        }
                    }
                }
            }

            val navHostFragment = binding.navHostFragment.getFragment<NavHostFragment>()
            setupWithNavController(navHostFragment.navController)
        }
    }

    override fun initData() {
    }
}