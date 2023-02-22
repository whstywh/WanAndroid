package com.wh.wanandroid.model.list

data class HomeDataFeed(
    val curPage: Int,
    val offset: Int,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val over: Boolean,
    val datas: MutableList<DatasBean>
)