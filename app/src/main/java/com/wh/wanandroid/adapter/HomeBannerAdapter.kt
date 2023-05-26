package com.wh.wanandroid.adapter

import android.content.Context
import com.wh.wanandroid.bean.BannerBean
import com.wh.wanandroid.utils.loadImage
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

/**
 * 2023/2/22
 * wh
 * desc：
 */
class HomeBannerAdapter(private val context: Context?, mData: List<BannerBean> = emptyList()) :
    BannerImageAdapter<BannerBean>(mData) {

    override fun onBindView(
        holder: BannerImageHolder?,
        data: BannerBean?,
        position: Int,
        size: Int
    ) {
        holder?.imageView?.loadImage(context, data?.imagePath)
    }
}