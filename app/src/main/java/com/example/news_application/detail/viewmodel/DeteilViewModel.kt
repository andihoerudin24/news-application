package com.example.news_application.detail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_application.home.model.NewsModel
import com.example.news_application.network.ApiRepository
import kotlinx.coroutines.launch

class DeteilViewModel(
    private val apiRepository: ApiRepository
) : ViewModel() {


    val isBookmark by lazy {
        MutableLiveData<Int>(0)
    }

    fun find(articleModel: NewsModel.ArticleModel){
        viewModelScope.launch {
            isBookmark.value = apiRepository.find(articleModel)
        }
    }

    fun bookmark(articleModel: NewsModel.ArticleModel){
       viewModelScope.launch {
           if (isBookmark.value == 0){
               apiRepository.save(articleModel)
           }else{
               apiRepository.remove(articleModel)

           }
           find(articleModel)
       }
    }
}