package com.example.newsify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val m_newsNavHostFragment: View? = findViewById(R.id.newsNavHostFragment)
        if (m_newsNavHostFragment != null) {
            findViewById<BottomNavigationView>(R.id.bottomNavigationView).
            setupWithNavController(m_newsNavHostFragment.findNavController())
        }
    }
}