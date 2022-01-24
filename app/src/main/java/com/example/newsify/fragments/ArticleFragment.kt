package com.example.newsify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.newsify.MainActivity
import com.example.newsify.R
import com.example.newsify.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment() {
private  val args by navArgs<ArticleFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//         Inflate the layout for this fragment
        val inflatedLayout = inflater.inflate(R.layout.fragment_article, container, false)
        val m_webView = inflatedLayout.findViewById<WebView>(R.id.webView)
        m_webView.apply {
            webViewClient = WebViewClient()
            args.argsArticle.url?.let { loadUrl(it) }

        }
        // get the article by the args and open it in the web view


        return inflatedLayout
    }
}