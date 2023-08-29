package com.wh.wanandroid.bean

data class VideoListBean(
    val code: Int = 0,
    val message: String?,
    val result: Result?
)

data class Result(
    val list: List<VideoInfoBean>?,
    val total: Int = 0
)

data class VideoInfoBean(
    val coverUrl: String?,
    val duration: String?,
    val id: Int = 0,
    val playUrl: String?,
    val title: String?,
    val userName: String?,
    val userPic: String?
)