package com.wh.wanandroid.adapter

import coil.load
import com.wh.wanandroid.model.home.BannerFeed
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

/**
 * 2023/2/22
 * wh
 * desc：
 */
class HomeBannerAdapter(mData :List<BannerFeed>) :BannerImageAdapter<BannerFeed>(mData) {
    override fun onBindView(
        holder: BannerImageHolder?,
        data: BannerFeed?,
        position: Int,
        size: Int
    ) {
       holder?.imageView?.load(data?.imagePath)
    }
}