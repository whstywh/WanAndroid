package com.wh.wanandroid.ui.home

import androidx.paging.*
import com.wh.wanandroid.base.BaseRepository
import com.wh.wanandroid.bean.home.BannerBean
import com.wh.wanandroid.bean.list.HomeListBean
import com.wh.wanandroid.bean.list.ListItemBean
import com.wh.wanandroid.net.NetResult
import com.wh.wanandroid.net.RetrofitClient
import kotlinx.coroutines.flow.Flow

class HomeRepository : BaseRepository() {

    suspend fun getBanner(): NetResult<List<BannerBean>> = requestTryCatch {
        handleResponse(RetrofitClient.instance.create().getBanner())
    }

    fun getPagingData(): Flow<PagingData<ListItemBean>> {
        return Pager(config = PagingConfig(20), pagingSourceFactory = {
            object : PagingSource<Int, ListItemBean>() {
                override fun getRefreshKey(state: PagingState<Int, ListItemBean>): Int? {
                    return null
                }

                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItemBean> {
                    val page = params.key ?: 1
                    val pageSize = params.loadSize
                    val repository = getHomeList(page)
                    return if (repository is NetResult.Success) {
                        val datas = repository.data.datas
                        val prevKey = if (page > 1) page - 1 else null
                        val nextKey = if (datas.isNotEmpty()) page + 1 else null
                        LoadResult.Page(data = datas, prevKey, nextKey)
                    } else {
                        LoadResult.Error((repository as NetResult.Error).exception)
                    }
                }
            }
        }).flow
    }

    private suspend fun getHomeList(count: Int): NetResult<HomeListBean> = requestTryCatch {
        handleResponse(RetrofitClient.instance.create().getHomeList(count))
    }

}