package com.wh.wanandroid.ui.list

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wh.wanandroid.base.App
import com.wh.wanandroid.base.BaseViewModel
import com.wh.wanandroid.model.list.HomeDataFeed
import com.wh.wanandroid.net.NetResult
import com.wh.wanandroid.ui.home.HomeRepository
import kotlinx.coroutines.launch

class ListViewModel : BaseViewModel() {

    private val listLiveDataM = MutableLiveData<HomeDataFeed>()
    val listLiveData: LiveData<HomeDataFeed>
        get() = listLiveDataM


    private val homeRepository by lazy { HomeRepository() }

    fun getHomeList() {
        viewModelScope.launch {
            val homeDataFeed = homeRepository.getHomeList(1)
            if (homeDataFeed is NetResult.Success) {
                listLiveDataM.postValue(homeDataFeed.data)
            } else if (homeDataFeed is NetResult.Error) {
                Toast.makeText(App.instance(), homeDataFeed.exception.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}