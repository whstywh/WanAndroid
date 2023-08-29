package com.wh.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.wh.wanandroid.bean.ListItemBean
import com.wh.wanandroid.databinding.LayoutHomeListItemBinding

class HomeListAdapter(context: Context?) :
    PagingDataAdapter<ListItemBean, HomeListViewHolder>(object :
        DiffUtil.ItemCallback<ListItemBean>() {

        override fun areItemsTheSame(oldItem: ListItemBean, newItem: ListItemBean): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListItemBean, newItem: ListItemBean): Boolean {
            return oldItem == newItem
        }
    }) {

    private val inflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: HomeListViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListViewHolder {
        val binding = LayoutHomeListItemBinding.inflate(inflater, parent, false)
        return HomeListViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }
}