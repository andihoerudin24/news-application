package com.example.news_application.home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_application.home.model.CategoryModel
import com.example.news_application.home.model.NewsModel
import com.example.news_application.network.ApiException
import com.example.news_application.network.ApiRepository
import com.example.news_application.network.NoInternetException
import com.example.news_application.utils.ResponseHelper
import com.example.news_application.utils.setRequest
import com.example.news_application.utils.setResponse
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: ApiRepository) : ViewModel() {

    var response = MutableLiveData<ResponseHelper>()

    val category by lazy { MutableLiveData<String>()}
    val page by lazy { MutableLiveData<Double>()}
    val total by lazy { MutableLiveData<Double>()}

    init {
        category.value = ""
    }

    val categories = listOf<CategoryModel>(
        CategoryModel("", "Berita Utama"),
        CategoryModel("business", "Bisnis"),
        CategoryModel("entertainment", "Hiburan"),
        CategoryModel("general", "Umum"),
        CategoryModel("health", "Kesehatan"),
        CategoryModel("science", "Sains"),
        CategoryModel("sports", "Olah Raga"),
        CategoryModel("technology", "Teknologi")
    )


    fun fetchCategori(code_request: Int, category: String? , query: String? , page:Int){
         viewModelScope.launch {
             setResponse(response, ResponseHelper.LOADING, true)
             try {
                 val request =  repository.fetch(
                     category,
                     query,
                     page,
                 )
                 setRequest(response,request,code_request)
                 setResponse(response, ResponseHelper.LOADING, false)
             }catch (e:Exception){
                 Log.d("ERROR", e.message.toString())
                 if (e is ApiException || e is NoInternetException) {
                     setResponse(response, ResponseHelper.ERROR, e.message!!)
                     setResponse(response, ResponseHelper.LOADING, false)
                 }
             }
         }
    }

    val isBookmark by lazy {
        MutableLiveData<Int>(0)
    }

    fun find(articleModel: NewsModel.ArticleModel){
        viewModelScope.launch {
            isBookmark.value = repository.find(articleModel)
        }
    }

    fun bookmark(articleModel: NewsModel.ArticleModel){
        viewModelScope.launch {
            try {
                if (isBookmark.value == 0){
                     repository.save(articleModel)
                }else{
                     repository.remove(articleModel)
                }
                find(articleModel)
            }catch (e:Exception){
                Log.d("ERROR", e.message.toString())
                if (e is ApiException || e is NoInternetException) {
                    setResponse(response, ResponseHelper.ERROR, e.message!!)
                    setResponse(response, ResponseHelper.LOADING, false)
                }
            }

        }
    }

}