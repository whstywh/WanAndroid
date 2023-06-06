package com.wh.wanandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.wh.wanandroid.base.BaseViewModel
import com.wh.wanandroid.bean.BannerBean
import com.wh.wanandroid.bean.ListItemBean
import com.wh.wanandroid.net.NetResult
import com.wh.wanandroid.utils.showToastKT
import kotlinx.coroutines.launch
import java.util.concurrent.Flow

class HomeViewModel : BaseViewModel() {

    private val _bannerLiveData = MutableLiveData<List<BannerBean>>()
    val bannerLiveData: LiveData<List<BannerBean>> = _bannerLiveData

    private val _topLiveData = MutableLiveData<List<ListItemBean>>()
    val topLiveData: LiveData<List<ListItemBean>> = _topLiveData

    init {
        getBanner()
        getHomeTopList()
    }

    fun getBanner() {
        viewModelScope.launch {
            val banner = HomeRepository.getBanner()
            if (banner is NetResult.Success) {
                _bannerLiveData.postValue(banner.data)
            } else if (banner is NetResult.Error) {
                banner.exception.msg?.showToastKT()
            }
        }
    }

    fun getHomeTopList() {
        viewModelScope.launch {
            val topList = HomeRepository.getHomeTopList()
            if (topList is NetResult.Success) {
                _topLiveData.postValue(topList.data)
            } else if (topList is NetResult.Error) {
                topList.exception.msg?.showToastKT()
            }
        }
    }


    val pager = Pager(config = PagingConfig(20), pagingSourceFactory = {
        object : PagingSource<Int, ListItemBean>() {
            override fun getRefreshKey(state: PagingState<Int, ListItemBean>): Int? {
                return null
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItemBean> {
                val page = params.key ?: 1
                val result = HomeRepository.getHomeList(page)
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
    }).flow.cachedIn(viewModelScope)

}