package com.wh.wanandroid.ui.my

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.wh.wanandroid.R
import com.wh.wanandroid.base.BaseFragment
import com.wh.wanandroid.databinding.FragmentMyBinding
import com.wh.wanandroid.ui.login.LoginActivity
import com.wh.wanandroid.utils.DataStoreUtils
import com.wh.wanandroid.utils.viewBinding
import kotlinx.coroutines.launch

/**
 * 2023/5/30
 * wh
 * desc：个人中心
 */
class MyFragment : BaseFragment(), View.OnClickListener {

    private val viewBinding by viewBinding(FragmentMyBinding::bind)

    private val viewModel by viewModels<MyViewModel>()

    override fun getLayoutID(): Int = R.layout.fragment_my

    override fun initView() {
        viewBinding.myLoginName.setOnClickListener(this)
    }

    override fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewBinding.myLoginName.text = DataStoreUtils.getUsername(context)
        }
    }

    override fun onClick(v: View?) {
        if (v == viewBinding.myLoginName) {
            viewLifecycleOwner.lifecycleScope.launch {
                if (DataStoreUtils.isNoLogin(context)) {
                    startActivity(Intent(context, LoginActivity::class.java))
                }
            }
        }
    }

}