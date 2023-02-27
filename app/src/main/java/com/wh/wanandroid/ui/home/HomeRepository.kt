package com.wh.wanandroid.ui.home

import com.wh.wanandroid.base.BaseRepository
import com.wh.wanandroid.bean.home.BannerBean
import com.wh.wanandroid.bean.list.ListBean
import com.wh.wanandroid.net.NetResult
import com.wh.wanandroid.net.RetrofitClient

class HomeRepository() : BaseRepository() {

    suspend fun getBanner(): NetResult<List<BannerBean>> = requestTryCatch {
        handleResponse(RetrofitClient.instance.create().getBanner())
    }

    suspend fun getHomeList(count: Int): NetResult<ListBean> = requestTryCatch {
        handleResponse(RetrofitClient.instance.create().getHomeList(count))
    }

}