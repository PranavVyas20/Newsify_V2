package com.example.newsify.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
lateinit var pgBarSearch:ProgressBar
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
        pgBarSearch = inflatedView.findViewById(R.id.paginationProgressBar)

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
                pgBarSearch.visibility = View.VISIBLE
                delay(1500L)
                if(m_searchView.text.isNotEmpty()){
                    m_viewModel.searchNews(m_searchView.text.toString(),1)
                }
                pgBarSearch.visibility = View.GONE
            }
        }

        //Observe the Live data object and set data to the adapter
        m_viewModel.searchedNews.observe(viewLifecycleOwner, Observer {
            pgBarSearch.visibility = View.GONE
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
                        pgBarSearch.visibility = View.GONE
                        m_viewModel.currentSearchedNewsPage++
                        m_viewModel.searchNews(m_searchView.text.toString(),m_viewModel.currentSearchedNewsPage)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        // Handling article save btn click on news articles
        m_newsAdapter.setOnItemClikListener_saveArticle {
            m_viewModel.saveArticle(it)
        }
        // Handling the open artile btn
        m_newsAdapter.setOnItemClickListener_openArticle {
            val action = SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(it)
            findNavController().navigate(action)
        }
        // handling the click on news articles for sharing article
        m_newsAdapter.setOnItemClikListener_shareArticle {
            try{
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type="text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, it.title+"\n here's the link: ${it.url}")
                startActivity(Intent.createChooser(shareIntent,"share with:"))
            }
            catch (e:Exception){
                Toast.makeText(activity,"Error while sharing", Toast.LENGTH_SHORT).show()
            }
        }
        return inflatedView
    }
    private fun setUpRecyclerView(){
        m_newsAdapter = NewsAdapter("srchnf")
        m_rv.adapter = m_newsAdapter
        m_rv.layoutManager = m_layoutManger
    }

}