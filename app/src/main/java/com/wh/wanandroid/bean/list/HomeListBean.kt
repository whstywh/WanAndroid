package com.wh.wanandroid.bean.list

data class HomeListBean(
    val curPage: Int,
    val offset: Int,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val over: Boolean,
    val datas: MutableList<ListItemBean>
)

data class ListItemBean(
    val niceDate: String,
    val title: String,
    val desc: String,
    val author: String?,
    val shareUser: String,
    val id: Int,
    val chapterName: String,
    val link: String
)