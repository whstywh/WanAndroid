package com.wh.wanandroid.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wh.wanandroid.bean.ListItemBean
import com.wh.wanandroid.databinding.LayoutHomeListItemBinding


class HomeListViewHolder(private val binging: LayoutHomeListItemBinding) :
    RecyclerView.ViewHolder(binging.root) {
    fun bindTo(item: ListItemBean?) {
        item?.let {
            binging.apply {
                title.text = it.title
                date.text = it.niceDate
                chapterName.text = it.chapterName
                author.text = it.author ?: it.shareUser

                tag.visibility = if (it.tags?.isNotEmpty() == true) {
                    tag.text = it.tags[0].name
                    View.VISIBLE
                } else View.GONE
            }
        }
    }
}