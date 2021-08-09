package com.example.news_application.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news_application.home.view.HomeActivity
import com.example.news_application.network.ApiRepository

class HomeModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return HomeViewModel(ApiRepository()) as T
    }
}
