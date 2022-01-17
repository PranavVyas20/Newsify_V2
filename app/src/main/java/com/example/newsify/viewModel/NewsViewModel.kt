package com.example.newsify.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.newsify.Article
import com.example.newsify.NewsResponse
import com.example.newsify.Repository.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.ArithmeticException

class NewsViewModel(application: Application): AndroidViewModel(application) {
    var currentBreakingNewsPageNo = 1
    var maxBreakingNewsPageNo = 2

    private val newsRepo:NewsRepository = NewsRepository()
    val breakingNews:MutableLiveData<NewsResponse> = MutableLiveData()
    var isLoading = false

    init {
        getBreakingNews("in")
    }

    fun getBreakingNews(countryCode:String){
        viewModelScope.launch {
            isLoading = true
            val newsResponse = try {
                newsRepo.getBreakingNews("in",currentBreakingNewsPageNo)
            }
            catch (e: IOException) {
                return@launch
            } catch (e: HttpException) {
                return@launch
            }
            if(newsResponse.isSuccessful && newsResponse.body() != null){
                isLoading = false
                println(newsResponse.body()!!.articles)
                 breakingNews.postValue(newsResponse.body())
            }
            else{
                println("no succes vinniiiiiiiiiiiiiiiiiiii")
            }
        }
    }
}