package com.wh.wanandroid.bean

data class BannerBean(
    val desc: String?,
    val id: Int,
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
    val datas: MutableList<ListItemBean>?
)

data class ListItemBean(
    val niceDate: String?,
    val title: String?,
    val desc: String?,
    val author: String?,
    val shareUser: String?,
    val id: Int,
    val chapterName: String?,
    val link: String?,
    val tag: List<Tag>?
)

data class Tag(val name: String?, val url: String?)