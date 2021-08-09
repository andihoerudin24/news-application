package com.example.news_application.database.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news_application.database.news.NewsDao
import com.example.news_application.home.model.NewsModel
import com.example.news_application.utils.SourceConverter


@Database(
    entities = [NewsModel.ArticleModel::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(SourceConverter::class)
abstract class DatabaseClient : RoomDatabase() {
    abstract val newsDao : NewsDao
}