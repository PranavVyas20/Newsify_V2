package com.example.newsify.Repository

import androidx.lifecycle.LiveData
import com.example.newsify.Article
import com.example.newsify.api.RetrofitInstance
import com.example.newsify.roomDB.ArticleDao

class NewsRepository(private val articleDao:ArticleDao) {
    suspend fun getBreakingNews(countryCode:String,category:String,pageNo:Int) =
        RetrofitInstance.api.getBreakingNews(countryCode,category,pageNo)

    suspend fun searchNews(query:String,pageNo: Int) =
        RetrofitInstance.api.searchNews(query,pageNo)

    val getAllArticles:LiveData<List<Article>> = articleDao.getAllArticles()

    suspend fun saveArticle(article:Article){
        articleDao.insertArticle(article)
    }



}