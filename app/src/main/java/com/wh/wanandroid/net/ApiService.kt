package com.wh.wanandroid.net

import com.wh.wanandroid.base.BaseFeedBean
import com.wh.wanandroid.bean.BannerBean
import com.wh.wanandroid.bean.HomeListBean
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    companion object {
        const val base_url = "https://www.wanandroid.com"
    }

    @GET("/banner/json")
    suspend fun getBanner(): BaseFeedBean<MutableList<BannerBean>>

    @GET("/article/list/{count}/json")
    suspend fun getHomeList(@Path("count") count: Int): BaseFeedBean<HomeListBean>


    @GET("/article/top/json")
    suspend fun getHomeTopList(): BaseFeedBean<HomeListBean>
}