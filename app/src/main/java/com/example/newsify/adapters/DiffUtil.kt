package com.example.newsify.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.newsify.Article

class DiffUtil(private  val oldNewsList:List<Article>,private val newNewsList:List<Article>)
    :DiffUtil.Callback()
{
    override fun getOldListSize(): Int {
        return oldNewsList.size
    }

    override fun getNewListSize(): Int {
        return newNewsList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNewsList[oldItemPosition].id == newNewsList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNewsList[oldItemPosition] == newNewsList[newItemPosition]

    }
}