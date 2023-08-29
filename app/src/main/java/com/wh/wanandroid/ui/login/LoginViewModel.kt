package com.wh.wanandroid.ui.login

import androidx.lifecycle.viewModelScope
import com.wh.wanandroid.base.BaseViewModel
import com.wh.wanandroid.net.NetResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    val channel = Channel<LoginIntent>(Channel.UNLIMITED)

    private val _loginState = MutableSharedFlow<LoginState>()
    val loginState: SharedFlow<LoginState> get() = _loginState

    init {
        viewModelScope.launch {
            channel.receiveAsFlow().collectLatest {
                if (it.request &&
                    it.username?.isNotEmpty() == true &&
                    it.password?.isNotEmpty() == true
                ) {
                    it.login()
                } else {
                    it.isReady()
                }
            }
        }
    }

    private fun LoginIntent.login() {
        viewModelScope.launch {
            _loginState.emit(LoginState.Loading)
            val result = LoginRepository.login(username, password)
            if (result is NetResult.Success) {
                _loginState.emit(LoginState.Success(result.data))
            } else {
                _loginState.emit(
                    LoginState.Error(
                        (result as NetResult.Error).exception.msg ?: ""
                    )
                )
            }
        }
    }

    private fun LoginIntent.isReady() {
        viewModelScope.launch {
            val state =
                if (username.isNullOrEmpty() || password.isNullOrEmpty())
                    LoginState.Init
                else
                    LoginState.Ready
            _loginState.emit(state)
        }
    }
}