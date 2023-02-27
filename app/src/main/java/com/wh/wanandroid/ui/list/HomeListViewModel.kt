package com.wh.wanandroid.ui.list

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.wh.wanandroid.base.BaseViewModel
import com.wh.wanandroid.bean.list.ListItemBean
import com.wh.wanandroid.ui.home.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeListViewModel : BaseViewModel() {

    private val homeRepository by lazy { HomeRepository() }

    fun getPagingData(): Flow<PagingData<ListItemBean>> {
        return homeRepository.getPagingData().cachedIn(viewModelScope)
    }

}