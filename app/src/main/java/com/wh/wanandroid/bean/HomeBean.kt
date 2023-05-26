package com.wh.wanandroid.bean

data class BannerBean(
    val id: Int,
    val desc: String?,
    val imagePath: String?,
    val isVisible: Int,
    val order: Int,
    val title: String?,
    val type: Int,
    val url: String?
)

data class HomeListBean(
    val curPage: Int,
    val offset: Int,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val over: Boolean,
    val datas: List<ListItemBean>?
)

data class ListItemBean(
    val id: Int,
    val niceDate: String?,
    val title: String?,
    val desc: String?,
    val author: String?,
    val shareUser: String?,
    val chapterName: String?,
    val link: String?,
    val tags: List<Tag>?
)

data class Tag(val name: String?, val url: String?)