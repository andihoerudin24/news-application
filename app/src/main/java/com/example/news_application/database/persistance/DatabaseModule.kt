package com.example.news_application.database.persistance

import android.app.Application
import androidx.room.Room
import com.example.news_application.database.news.NewsDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val databaseModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideArticle(get()) }
}

fun provideDatabase(application: Application): DatabaseClient {
    return Room.databaseBuilder(application, DatabaseClient::class.java, "News.db")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideArticle(database: DatabaseClient): NewsDao {
    return  database.newsDao
}