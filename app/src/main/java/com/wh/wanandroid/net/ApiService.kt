package com.wh.wanandroid.net

import com.wh.wanandroid.bean.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(): BaseFeedBean<LoginBean>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): BaseFeedBean<LoginBean>
}