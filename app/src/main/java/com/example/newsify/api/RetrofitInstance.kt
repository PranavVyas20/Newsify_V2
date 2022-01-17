package com.example.newsify.api

import com.example.newsify.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitInstance {
    // Companion objects are singleton objects whose properties and functions are
    // tied to a class but not to the instance of that class
    companion object{
        // Creating a singleton instance of Retrofit
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor() // for debugging the response
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val retrofitClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(retrofitClient)
                .build()
        }

        // api object - will be used to make network requests form everywhere !
        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}