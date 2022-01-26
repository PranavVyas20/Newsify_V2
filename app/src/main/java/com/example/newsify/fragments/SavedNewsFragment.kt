package com.example.newsify.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsify.Article
import com.example.newsify.R
import com.example.newsify.adapters.NewsAdapter
import com.example.newsify.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay

class SavedNewsFragment : Fragment() {
    lateinit var m_newsViewModel:NewsViewModel
    lateinit var m_newsAdapter:NewsAdapter
    lateinit var savedNewsRv: RecyclerView
    lateinit var m_layoutManager:LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflatedView = inflater.inflate(R.layout.fragment_saved_news, container, false)
        savedNewsRv= inflatedView.findViewById(R.id.rvSavedNews)
        m_layoutManager = LinearLayoutManager(activity)

       setUpRecyclerView()

        m_newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        m_newsViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            m_newsAdapter.setData(it.toList())
        })


        // Handling the open artile btn
        m_newsAdapter.setOnItemClickListener_openArticle {
            val action = SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(it)
            findNavController().navigate(action)
        }
        // handling the click on news articles for sharing article
        m_newsAdapter.setOnItemClikListener_shareArticle {
            try{
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type="text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, it.url)
                startActivity(Intent.createChooser(shareIntent,"share with:"))
            }
            catch (e:Exception){
                Toast.makeText(activity,"Error while sharing", Toast.LENGTH_SHORT).show()
            }
        }
        // Handling the delte article from db btn
        m_newsAdapter.setOnItemClickListener_deleteArticle {
            m_newsViewModel.deleteArticle(it)
            Snackbar.make(inflatedView.findViewById(R.id.savedNewsFragment),"Article deleted",Snackbar.LENGTH_SHORT).show()
        }

    return inflatedView
    }

    fun setUpRecyclerView(){
        m_newsAdapter = NewsAdapter("snf")
        savedNewsRv.adapter = m_newsAdapter
        savedNewsRv.layoutManager = m_layoutManager
    }

}