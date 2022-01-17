package com.example.newsify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsify.R

class SearchNewsFragment : Fragment() {
//lateinit var viewmodel:NewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        viewmodel = (activity as MainActivity).viewModel

        return inflater.inflate(R.layout.fragment_search_news, container, false)
    }

}