package com.example.newsify.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsify.Article
import com.example.newsify.R

class NewsAdapter:RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    private var oldNewsList:List<Article> = emptyList()

    inner class ArticleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
       return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_article_preview,parent,false
        ))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentArticle = oldNewsList[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.tvSource).text = currentArticle.source.name
            findViewById<TextView>(R.id.tvTitle).text = currentArticle.title
            findViewById<TextView>(R.id.tvDescription).text = currentArticle.description
            setOnClickListener {
                Toast.makeText(it.context,currentArticle.source.name,Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return oldNewsList.size
    }

    fun setData(newNewsList:List<Article>){
        val m_diffUtil = DiffUtil(oldNewsList,newNewsList)
        val diffResults = DiffUtil.calculateDiff(m_diffUtil)
        oldNewsList = newNewsList
        diffResults.dispatchUpdatesTo(this)
    }


}