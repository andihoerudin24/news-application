package com.example.news_application.network.retrofit

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.news_application.BuildConfig
import com.example.news_application.GlobalApp
import com.example.news_application.network.interceptor.NetworkConnectionInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun invoke(baseUrl: String, interceptor: NetworkConnectionInterceptor): Retrofit {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        val httpClient = OkHttpClient.Builder()
        httpClient.dispatcher(dispatcher)
        httpClient.addInterceptor(interceptor)
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            httpClient.addInterceptor(ChuckerInterceptor(GlobalApp.getAppContext()))
        }

        //httpClient.authenticator(TokenAuthenticator(baseUrl))

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
}
