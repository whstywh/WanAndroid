package com.wh.wanandroid.ui.home

import androidx.paging.*
import com.wh.wanandroid.base.BaseRepository
import com.wh.wanandroid.bean.BannerBean
import com.wh.wanandroid.bean.HomeListBean
import com.wh.wanandroid.bean.ListItemBean
import com.wh.wanandroid.net.NetResult
import com.wh.wanandroid.net.RetrofitClient
import kotlinx.coroutines.flow.Flow

class HomeRepository : BaseRepository() {

    suspend fun getBanner(): NetResult<List<BannerBean>> =
        requestTryCatch { handleResponse(RetrofitClient.instance.create().getBanner()) }

    fun getPagingData(): Flow<PagingData<ListItemBean>> {
        return Pager(config = PagingConfig(20), pagingSourceFactory = {
            object : PagingSource<Int, ListItemBean>() {
                override fun getRefreshKey(state: PagingState<Int, ListItemBean>): Int? {
                    return null
                }

                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItemBean> {
                    val page = params.key ?: 1
                    val result =  getHomeList(page)
                    return if (result is NetResult.Success) {
                        val prevKey = if (page > 1) page - 1 else null
                        val nextKey =
                            if (result.data.datas?.isNotEmpty() == true) page + 1 else null
                        LoadResult.Page(data = result.data.datas ?: emptyList(), prevKey, nextKey)
                    } else {
                        LoadResult.Error((result as NetResult.Error).exception)
                    }
                }
            }
        }).flow
    }

     suspend fun getHomeTopList(): NetResult<List<ListItemBean>> =
        requestTryCatch { handleResponse(RetrofitClient.instance.create().getHomeTopList()) }


    private suspend fun getHomeList(count: Int): NetResult<HomeListBean> =
        requestTryCatch { handleResponse(RetrofitClient.instance.create().getHomeList(count)) }


}