package com.example.newsify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsify.api.RetrofitInstance
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
//    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContentView(R.layout.activity_news)


        // setting up the bottom nav bar
        val m_newsNavHostFragment: View? = findViewById(R.id.newsNavHostFragment)
        if (m_newsNavHostFragment != null) {
            findViewById<BottomNavigationView>(R.id.bottomNavigationView).
            setupWithNavController(m_newsNavHostFragment.findNavController())
        }
    }
}