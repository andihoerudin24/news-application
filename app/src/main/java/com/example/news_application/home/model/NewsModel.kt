package com.example.news_application.home.model

//import androidx.room.Entity
//import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.news_application.baseview.model.BaseModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsModel (
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val `articles` : List<ArticleModel>
): BaseModel(),Serializable{
    @Entity(tableName = "tableArticle")
    data class ArticleModel (
        @SerializedName("source")
        val source: SourceModel?,
        @SerializedName("author")
        val author: String? = "",
        @SerializedName("title")
        val title: String? = "",
        @SerializedName("description")
        val description: String? = "",
        @SerializedName("url")
        val url: String? = "",
        @SerializedName("urlToImage")
        val urlToImage: String? = "",
        @PrimaryKey(autoGenerate = false)
        @SerializedName("publishedAt")
        val publishedAt: String,
        @SerializedName("content")
        val content: String? = "",
    ) : Serializable
    {
        data class SourceModel (
            @SerializedName("id")
            val id: String? = "",
            @SerializedName("name")
            val name: String? = ""
        ) : Serializable
    }
}
