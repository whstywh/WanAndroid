package com.wh.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wh.wanandroid.bean.list.ListItemBean
import com.wh.wanandroid.databinding.LayoutHomeListItemBinding

class HomeListAdapter(context: Context) :
    PagingDataAdapter<ListItemBean, HomeListAdapter.MViewHolder>(object :
        DiffUtil.ItemCallback<ListItemBean>() {

        override fun areItemsTheSame(oldItem: ListItemBean, newItem: ListItemBean): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ListItemBean, newItem: ListItemBean): Boolean {
            return oldItem.id == newItem.id
        }
    }) {

    private val inflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val binding = LayoutHomeListItemBinding.inflate(inflater, parent, false)
        return MViewHolder(binding)
    }

    class MViewHolder(private val binging: LayoutHomeListItemBinding) :
        RecyclerView.ViewHolder(binging.root) {

        fun bindTo(item: ListItemBean?) {
            item?.let {
                binging.apply {
                    title.text = it.title
                    date.text = it.niceDate
                    chapterName.text = it.chapterName
                    author.text = it.author ?: it.shareUser
                }
            }
        }
    }

}