package com.wh.wanandroid.ui.login

import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.wh.wanandroid.R
import com.wh.wanandroid.base.BaseActivity
import com.wh.wanandroid.databinding.ActivityLoginBinding
import com.wh.wanandroid.utils.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity(), View.OnClickListener {

    private val viewBinding by viewBinding(ActivityLoginBinding::bind)

    private val viewModel by viewModels<LoginViewModel>()

    private val intent by lazy {
        LoginIntent(false, "", "")
    }

    override fun getLayoutID(): Int = R.layout.activity_login

    override fun initView() {
        viewBinding.loginLayout.setOnClickListener(this)

        viewBinding.loginUsername.addTextChangedListener {
            lifecycleScope.launch {
                intent.username = it.toString()
                viewModel.channel.send(intent)
            }
        }
        viewBinding.loginPassword.addTextChangedListener {
            lifecycleScope.launch {
                intent.password = it.toString()
                viewModel.channel.send(intent)
            }
        }
    }

    override fun initData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.distinctUntilChanged().collectLatest {
                    showResult(it)
                }
            }
        }
    }

    private fun showResult(state: LoginState) {
        when (state) {
            LoginState.Init -> {
                viewBinding.loginLayout.isSelected = false
            }
            LoginState.Ready -> {
                viewBinding.loginLayout.isSelected = true
            }
            LoginState.Loading -> {
                viewBinding.loginLayout.isSelected = true
                viewBinding.loginLayoutIcon.apply {
                    loadImageGif(this@LoginActivity, R.drawable.login_loading)
                    visibility = View.VISIBLE
                }
            }
            is LoginState.Success -> {
                viewBinding.loginLayout.isSelected = true
                viewBinding.loginLayoutIcon.apply {
                    setImageDrawable(
                        ContextCompat.getDrawable(
                            this@LoginActivity,
                            R.drawable.login_success
                        )
                    )
                    visibility = View.VISIBLE
                }
                getString(R.string.login_btn_login_success).showToastKT()

                lifecycleScope.launch {
                    DataStoreUtils.login(state.data?.username)
                }
            }
            is LoginState.Error -> {
                viewBinding.loginLayout.isSelected = true
                viewBinding.loginLayoutIcon.apply {
                    setImageDrawable(
                        ContextCompat.getDrawable(
                            this@LoginActivity,
                            R.drawable.login_error
                        )
                    )
                    visibility = View.VISIBLE
                }
                state.errorMsg.showToastKT()
            }
        }
    }

    override fun onClick(v: View?) {
        if (v == viewBinding.loginLayout) {
            lifecycleScope.launch {
                viewModel.channel.send(intent.copy(request = true))
            }
        }
    }
}