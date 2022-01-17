package com.example.newsify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsify.Article
import com.example.newsify.R
import com.example.newsify.adapters.NewsAdapter
import com.example.newsify.viewModel.NewsViewModel
import kotlinx.coroutines.delay

class SavedNewsFragment : Fragment() {
//    lateinit var m_newsViewModel:NewsViewModel
//    lateinit var m_newsAdapter:NewsAdapter
//    lateinit var breakingNewsRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflatedView = inflater.inflate(R.layout.fragment_saved_news, container, false)
//        breakingNewsRv= inflatedView.findViewById(R.id.rvSavedNews)
//        setUpRecyclerView()
//
//        m_newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
//
//
//        m_newsViewModel.breakingNews.observe(viewLifecycleOwner, Observer {
//            m_newsAdapter.setData(it.articles)
//        })

    return inflatedView
    }

//    fun setUpRecyclerView(){
//        m_newsAdapter = NewsAdapter()
//        breakingNewsRv.adapter = m_newsAdapter
//        breakingNewsRv.layoutManager = LinearLayoutManager(activity)
//    }

}