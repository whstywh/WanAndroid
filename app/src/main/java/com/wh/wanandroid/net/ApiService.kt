package com.wh.wanandroid.net

import com.wh.wanandroid.bean.BaseFeedBean
import com.wh.wanandroid.bean.BannerBean
import com.wh.wanandroid.bean.HomeListBean
import com.wh.wanandroid.bean.ListItemBean
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    companion object {
        const val base_url = "https://www.wanandroid.com"
    }

    @GET("/banner/json")
    suspend fun getBanner(): BaseFeedBean<List<BannerBean>>

    @GET("/article/list/{count}/json")
    suspend fun getHomeList(@Path("count") count: Int): BaseFeedBean<HomeListBean>

    @GET("/article/top/json")
    suspend fun getHomeTopList(): BaseFeedBean<List<ListItemBean>>
}