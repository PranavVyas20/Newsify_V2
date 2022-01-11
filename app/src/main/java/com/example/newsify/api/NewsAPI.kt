package com.example.newsify.api

import com.example.newsify.NewsResponse
import com.example.newsify.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        // Parameters of this function are actually 'request parameter'
        // therefore they need to be annotated with @Query
        @Query("country")
        countryCode:String = "in",
        @Query("page")
        pageNumber:Int = 1,
        @Query("apiKey")
        apiKey:String = API_KEY
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNews(
        // Parameters of this function are actually 'request parameter'
        // therefore they need to be annotated with @Query
        @Query("q")
        searchQuery:String,
        @Query("page")
        pageNumber:Int = 1,
        @Query("apiKey")
        apiKey:String = API_KEY
    ):Response<NewsResponse>
}