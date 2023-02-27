package com.wh.wanandroid.bean.list

data class ListBean(
    val curPage: Int,
    val offset: Int,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val over: Boolean,
    val datas: MutableList<ListItemBean>
)