package com.example.news_application.network

import com.example.news_application.GlobalApp
import com.example.news_application.database.news.NewsDao
import com.example.news_application.home.model.NewsModel
import com.example.news_application.network.interceptor.NetworkConnectionInterceptor
import com.example.news_application.network.retrofit.RetrofitHelper

class ApiRepository{
    val db: NewsDao? = null
    private var apiServices: ApiServices =
        RetrofitHelper.invoke(Api.BASE_URL, NetworkConnectionInterceptor(GlobalApp.getAppContext()))
            .create(ApiServices::class.java)

    suspend fun fetch(category:String?,query:String?,page:Int) = apiServices.fetchNews(
        Api.API_KEY,
        "id",
        category,
        query,
        page
    )



    suspend fun save(articleModel: NewsModel.ArticleModel) {
        db?.save( articleModel )
    }

    suspend fun remove(articleModel: NewsModel.ArticleModel) {
        db?.remove( articleModel )
    }

    suspend fun find(articleModel: NewsModel.ArticleModel) = db?.find(articleModel.publishedAt)




}