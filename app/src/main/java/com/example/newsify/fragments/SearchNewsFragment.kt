package com.example.newsify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsify.Article
import com.example.newsify.R
import com.example.newsify.adapters.NewsAdapter
import com.example.newsify.viewModel.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {
lateinit var m_viewModel: NewsViewModel
lateinit var m_newsAdapter:NewsAdapter
lateinit var m_layoutManger:LinearLayoutManager
lateinit var m_rv:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflatedView = inflater.inflate(R.layout.fragment_search_news, container, false)
        var searchNewsList:List<Article> = emptyList()

        // Initialise the searchView
        val m_searchView = inflatedView.findViewById<EditText>(R.id.etSearch)
        m_layoutManger = LinearLayoutManager(activity)
        // RecyclerView
        m_rv = inflatedView.findViewById(R.id.rvSearchNews)
        // Initialse the viewModel
        m_viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        setUpRecyclerView()

        // Handling the news search
        var m_job:Job? = null
        m_searchView.addTextChangedListener {
            searchNewsList = emptyList()
            m_job?.cancel()
            m_job = MainScope().launch {
                delay(1500L)
                if(m_searchView.text.isNotEmpty()){
                        m_viewModel.searchNews(m_searchView.text.toString(),1)
                }
            }
        }

        //Observe the Live data object and set data to the adapter
        m_viewModel.searchedNews.observe(viewLifecycleOwner, Observer {
                searchNewsList+=it.articles
            m_newsAdapter.setData(searchNewsList)
        })

        //Pagination stuff!
        m_rv.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = m_layoutManger.childCount
                val pastVisibleItem = m_layoutManger.findFirstVisibleItemPosition()
                val total  = m_newsAdapter.itemCount

                if (!m_viewModel.isLoading && m_viewModel.currentSearchedNewsPage < m_viewModel.maxSearchedNewsPage){
                    if (visibleItemCount + pastVisibleItem>= total){
                        m_viewModel.currentSearchedNewsPage++
                        m_viewModel.searchNews(m_searchView.text.toString(),m_viewModel.currentSearchedNewsPage)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        return inflatedView
    }
    private fun setUpRecyclerView(){
        m_newsAdapter = NewsAdapter()
        m_rv.adapter = m_newsAdapter
        m_rv.layoutManager = m_layoutManger
    }

}