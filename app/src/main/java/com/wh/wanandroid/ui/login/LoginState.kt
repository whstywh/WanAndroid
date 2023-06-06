package com.wh.wanandroid.ui.login

import com.wh.wanandroid.bean.LoginBean

data class LoginIntent(var request: Boolean = false, var username: String?, var password: String?)

sealed class LoginState {
    object Init : LoginState()
    object Ready : LoginState()
    object Loading : LoginState()
    data class Success(val data: LoginBean?) : LoginState()
    data class Error(val errorMsg: String) : LoginState()
}