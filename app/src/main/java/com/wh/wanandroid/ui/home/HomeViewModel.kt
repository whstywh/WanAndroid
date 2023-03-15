package com.wh.wanandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wh.wanandroid.base.BaseViewModel
import com.wh.wanandroid.bean.BannerBean
import com.wh.wanandroid.bean.ListItemBean
import com.wh.wanandroid.net.NetResult
import com.wh.wanandroid.utils.showToastKT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    private val bannerLiveDataM = MutableLiveData<List<BannerBean>>()
    val bannerLiveData: LiveData<List<BannerBean>> = bannerLiveDataM

    private val repository: HomeRepository by lazy { HomeRepository() }

    init {
        getBanner()
    }

    fun getBanner() {
        viewModelScope.launch {
            val banner = repository.getBanner()
            if (banner is NetResult.Success) {
                bannerLiveDataM.postValue(banner.data)
            } else if (banner is NetResult.Error) {
                banner.exception.msg?.showToastKT()
            }
        }
    }

    fun getPagingData(): Flow<PagingData<ListItemBean>> {
        return repository.getPagingData().cachedIn(viewModelScope)
    }
}