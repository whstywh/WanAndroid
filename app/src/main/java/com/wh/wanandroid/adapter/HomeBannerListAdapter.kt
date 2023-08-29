package com.wh.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wh.wanandroid.bean.BannerBean
import com.wh.wanandroid.databinding.LayoutHomeBannerItemBinding

class HomeBannerListAdapter(context: Context?) :
    ListAdapter<List<BannerBean>, HomeBannerViewHolder>(object :
        DiffUtil.ItemCallback<List<BannerBean>>() {
        override fun areItemsTheSame(
            oldItem: List<BannerBean>,
            newItem: List<BannerBean>
        ): Boolean {
            return oldItem.size == newItem.size
        }

        override fun areContentsTheSame(
            oldItem: List<BannerBean>,
            newItem: List<BannerBean>
        ): Boolean {
            return oldItem == newItem
        }
    }) {

    private val inflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerViewHolder {
        val binding = LayoutHomeBannerItemBinding.inflate(inflater, parent, false)
        return HomeBannerViewHolder(binding)
    }


    override fun getItemViewType(position: Int): Int {
        return 0
    }
}

class HomeBannerViewHolder(private val binging: LayoutHomeBannerItemBinding) :
    RecyclerView.ViewHolder(binging.root) {

    fun bindTo(item: List<BannerBean>?) {
        binging.banner.setAdapter(HomeBannerAdapter(binging.root.context))
            .setDatas(item)
            .start()
    }
}