package com.example.news_application.database.news

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.news_application.home.model.NewsModel


@Dao
interface NewsDao {

     @Query("SELECT * FROM tableArticle")
     fun findAll(): LiveData<List<NewsModel.ArticleModel>>


     @Query("SELECT COUNT(*)  FROM tableArticle WHERE publishedAt=:publish")
     suspend fun find(publish : String) : Int


     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun save(articleModel: NewsModel.ArticleModel)

    @Delete
    suspend fun remove(articleModel: NewsModel.ArticleModel)


}