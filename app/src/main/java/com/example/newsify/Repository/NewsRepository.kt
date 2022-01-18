package com.example.newsify.Repository

import com.example.newsify.api.RetrofitInstance

class NewsRepository() {
    suspend fun getBreakingNews(countryCode:String,pageNo:Int) =
        RetrofitInstance.api.getBreakingNews(countryCode,pageNo)

    suspend fun searchNews(query:String,pageNo: Int) =
        RetrofitInstance.api.searchNews(query,pageNo)
}