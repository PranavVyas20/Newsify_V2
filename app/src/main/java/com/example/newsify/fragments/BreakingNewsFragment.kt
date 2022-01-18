package com.example.newsify.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsify.Article
import com.example.newsify.R
import com.example.newsify.adapters.NewsAdapter
import com.example.newsify.api.RetrofitInstance
import com.example.newsify.viewModel.NewsViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class BreakingNewsFragment : Fragment() {
    lateinit var m_newsViewModel:NewsViewModel
    lateinit var m_newsAdapter:NewsAdapter
    lateinit var m_LayoutManger:LinearLayoutManager
    lateinit var breakingNewsRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflatedView = inflater.inflate(R.layout.fragment_breaking_news, container, false)
        breakingNewsRv= inflatedView.findViewById(R.id.rvBreakingNews)
        m_LayoutManger = LinearLayoutManager(context)
        var newsList:List<Article> = emptyList()

        // Setup the recycler view
        setUpRecyclerView()

        //Initialise the view model:
        m_newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        m_newsViewModel.getBreakingNews("in")


//         Pagination stuff!
        breakingNewsRv.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = m_LayoutManger.childCount
                val pastVisibleItem = m_LayoutManger.findFirstVisibleItemPosition()
                val total  = m_newsAdapter.itemCount

                if (!m_newsViewModel.isLoading && m_newsViewModel.currentBreakingNewsPageNo < m_newsViewModel.maxBreakingNewsPageNo){
                    if (visibleItemCount + pastVisibleItem>= total){
                        m_newsViewModel.currentBreakingNewsPageNo++
                        m_newsViewModel.getBreakingNews("in")
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        // Observing the news list
        m_newsViewModel.breakingNews.observe(viewLifecycleOwner, Observer {
            newsList+=it.articles.toList()
            m_newsAdapter.setData(newsList)
        })
        return inflatedView
    }
    fun setUpRecyclerView(){
        m_newsAdapter = NewsAdapter()
        breakingNewsRv.adapter = m_newsAdapter
        breakingNewsRv.layoutManager = m_LayoutManger
    }

}