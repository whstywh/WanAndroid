package com.wh.wanandroid.net

import com.wh.wanandroid.base.BaseModel
import com.wh.wanandroid.model.home.BannerFeed
import com.wh.wanandroid.model.home.HomeDataFeed
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    companion object {
        const val base_url = "https://www.wanandroid.com"
    }

    @GET("/banner/json")
    suspend fun getBanner(): BaseModel<MutableList<BannerFeed>>

    @GET("/article/list/{count}/json")
    suspend fun getHomeList(@Path("count") count: Int): BaseModel<HomeDataFeed>
}