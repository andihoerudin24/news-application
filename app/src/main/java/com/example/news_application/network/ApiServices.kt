package com.example.news_application.network

import com.example.news_application.home.model.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    /** TOP HEADLINE **/
    @GET(Api.TOP_HEADLINE)
    suspend fun fetchNews(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
        @Query("category") category: String?,
        @Query("q") query: String?,
        @Query("page") page: Int /// max page from totalSize = 20 /page
    ): Response<NewsModel>
}