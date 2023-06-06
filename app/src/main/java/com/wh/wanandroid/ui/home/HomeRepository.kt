package com.wh.wanandroid.ui.home

import com.wh.wanandroid.base.BaseRepository
import com.wh.wanandroid.bean.BannerBean
import com.wh.wanandroid.bean.HomeListBean
import com.wh.wanandroid.bean.ListItemBean
import com.wh.wanandroid.net.NetResult
import com.wh.wanandroid.net.RetrofitClient

object HomeRepository : BaseRepository() {

    suspend fun getBanner(): NetResult<List<BannerBean>> =
        requestTryCatch { handleResponse(RetrofitClient.instance.create().getBanner()) }

    suspend fun getHomeTopList(): NetResult<List<ListItemBean>> =
        requestTryCatch { handleResponse(RetrofitClient.instance.create().getHomeTopList()) }

    suspend fun getHomeList(count: Int): NetResult<HomeListBean> =
        requestTryCatch { handleResponse(RetrofitClient.instance.create().getHomeList(count)) }

}