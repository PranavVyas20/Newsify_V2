package com.example.newsify.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsify.Article
import com.example.newsify.R
import com.example.newsify.adapters.NewsAdapter
import com.example.newsify.api.RetrofitInstance
import com.example.newsify.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class BreakingNewsFragment : Fragment() {
    lateinit var m_newsViewModel:NewsViewModel
    lateinit var m_newsAdapter:NewsAdapter
    lateinit var m_LayoutManger:LinearLayoutManager
    lateinit var pgBar:ProgressBar
    lateinit var breakingNewsRv: RecyclerView
    var currentCategory:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflatedView = inflater.inflate(R.layout.fragment_breaking_news, container, false)
        val tabLayout:TabLayout = inflatedView.findViewById(R.id.categoryTabLayout)
        var newsList:List<Article> = emptyList()

        breakingNewsRv= inflatedView.findViewById(R.id.rvBreakingNews)
        m_LayoutManger = LinearLayoutManager(context)
        pgBar = inflatedView.findViewById(R.id.progressBar)

        // Setup the recycler view
        setUpRecyclerView()

        //Initialise the view model:
        m_newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        m_newsViewModel.getBreakingNews("in","")

        //Pagination stuff!
        breakingNewsRv.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                m_newsViewModel.catChanged = false
                handlePagination()
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        // Observing the news list
        m_newsViewModel.breakingNews.observe(viewLifecycleOwner, Observer {
            pgBar.visibility = View.GONE
            if(m_newsViewModel.catChanged){
                newsList = it.articles.toList()
            }
            else{
                newsList+=it.articles.toList()
            }
            m_newsAdapter.setData(newsList)
        })

        // handling the category change here
        tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                pgBar.visibility = View.VISIBLE
                m_newsViewModel.currentBreakingNewsPageNo = 1
                m_newsViewModel.catChanged = true
                Toast.makeText(context, tab!!.id.toString(),Toast.LENGTH_SHORT).show()
                if(tab.text == "Latest"){
                    m_newsViewModel.getBreakingNews("in","")
                }
                else{
                    currentCategory = tab.text as String?
                    m_newsViewModel.getBreakingNews("in", currentCategory!!)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Handling article save btn click on news articles
        m_newsAdapter.setOnItemClikListener_saveArticle {
            activity?.let { it1 -> Snackbar.make(it1.findViewById(R.id.llyt),"Article saved",Snackbar.LENGTH_SHORT).show() }
            m_newsViewModel.saveArticle(it)
        }
        // Handling the open artile btn
        m_newsAdapter.setOnItemClickListener_openArticle {
            val action = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(it)
            findNavController().navigate(action)
            Toast.makeText(activity,"Open article in web view",Toast.LENGTH_SHORT).show()
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
                Toast.makeText(activity,"Error while sharing",Toast.LENGTH_SHORT).show()
            }
        }
        return inflatedView
    }

    fun setUpRecyclerView(){
        m_newsAdapter = NewsAdapter("bnf")
        breakingNewsRv.adapter = m_newsAdapter
        breakingNewsRv.layoutManager = m_LayoutManger
    }
    fun handlePagination(){
        val visibleItemCount = m_LayoutManger.childCount
        val pastVisibleItem = m_LayoutManger.findFirstVisibleItemPosition()
        val total  = m_newsAdapter.itemCount

        if (!m_newsViewModel.isLoading && m_newsViewModel.currentBreakingNewsPageNo
            < m_newsViewModel.maxBreakingNewsPageNo){
            if (visibleItemCount + pastVisibleItem>= total){
                pgBar.visibility = View.VISIBLE
                m_newsViewModel.currentBreakingNewsPageNo++
                m_newsViewModel.getBreakingNews("in", currentCategory!!)
            }
        }
    }


}