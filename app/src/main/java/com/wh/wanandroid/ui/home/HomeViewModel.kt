package com.wh.wanandroid.ui.home

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wh.wanandroid.base.App
import com.wh.wanandroid.base.BaseViewModel
import com.wh.wanandroid.model.home.BannerFeed
import com.wh.wanandroid.net.NetResult
import kotlinx.coroutines.launch

class HomeViewModel() : BaseViewModel() {

    private val bannerLiveDataM = MutableLiveData<List<BannerFeed>>()
    val bannerLiveData: LiveData<List<BannerFeed>>
        get() = bannerLiveDataM

    private val repository: HomeRepository by lazy { HomeRepository() }

    fun getBanner() {
        viewModelScope.launch {
            val banner = repository.getBanner()
            if (banner is NetResult.Success) {
                bannerLiveDataM.postValue(banner.data)
            } else if (banner is NetResult.Error) {
                Toast.makeText(App.instance(), banner.exception.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

}