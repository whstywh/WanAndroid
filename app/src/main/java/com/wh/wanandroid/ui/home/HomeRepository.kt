package com.wh.wanandroid.ui.home

import com.wh.wanandroid.base.BaseRepository
import com.wh.wanandroid.model.home.BannerFeed
import com.wh.wanandroid.model.home.HomeDataFeed
import com.wh.wanandroid.net.NetResult
import com.wh.wanandroid.net.RetrofitClient

class HomeRepository(private val client: RetrofitClient) : BaseRepository() {


    suspend fun getBanner(): NetResult<List<BannerFeed>> = requestTryCatch {
        handleResponse(client.create().getBanner())
    }

    suspend fun getHomeList(count: Int): NetResult<HomeDataFeed> = requestTryCatch {
        handleResponse(client.create().getHomeList(count))
    }

}