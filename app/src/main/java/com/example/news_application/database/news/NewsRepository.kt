//package com.example.news_application.database.news
//
//import com.example.news_application.home.model.NewsModel
//import org.koin.dsl.module
//import timber.log.Timber
//import kotlin.math.ceil
//
//
//val repositoryModule = module {
//    factory { NewsRepository(get(), get()) }
//}
//
//class NewsRepository(
//    val db: NewsDao,
//) {
//
//    suspend fun page(
//        category: String? = "",
//        query: String,
//        page: Int
//    ): NewsModel {
//        return api.fetchPage( BuildConfig.API_KEY, "id", category!!, query, page)
//    }
//
//    suspend fun find(articleModel: ArticleModel) = db.find(articleModel.publishedAt)
//
//    suspend fun save(articleModel: ArticleModel) {
//        db.save( articleModel )
//    }
//
//    suspend fun remove(articleModel: ArticleModel) {
//        db.remove( articleModel )
//    }
//
//}