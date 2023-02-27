package com.wh.wanandroid.adapter

import coil.load
import com.wh.wanandroid.bean.home.BannerBean
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

/**
 * 2023/2/22
 * wh
 * desc：
 */
class HomeBannerAdapter(mData :List<BannerBean>) :BannerImageAdapter<BannerBean>(mData) {
    override fun onBindView(
        holder: BannerImageHolder?,
        data: BannerBean?,
        position: Int,
        size: Int
    ) {
       holder?.imageView?.load(data?.imagePath)
    }
}