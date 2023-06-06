package com.wh.wanandroid.ui.login

import com.wh.wanandroid.base.BaseRepository
import com.wh.wanandroid.bean.LoginBean
import com.wh.wanandroid.net.NetResult
import com.wh.wanandroid.net.RetrofitClient
import retrofit2.http.Field

object LoginRepository : BaseRepository() {

    suspend fun login(
        username: String?,
        password: String?
    ): NetResult<LoginBean> =
        requestTryCatch {
            handleResponse(
                RetrofitClient.instance.create().login(username, password)
            )
        }

    suspend fun register(): NetResult<LoginBean> =
        requestTryCatch { handleResponse(RetrofitClient.instance.create().register()) }

}