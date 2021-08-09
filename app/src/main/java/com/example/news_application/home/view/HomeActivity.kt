package com.example.news_application.home.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.news_application.R
import com.example.news_application.baseview.BaseActivity
import com.example.news_application.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding>() {


    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun setup(savedInstanceState: Bundle?) {
        val navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
    }

    override fun statusBarColor(): Int = 0





}
