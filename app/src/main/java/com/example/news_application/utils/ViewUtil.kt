package com.example.news_application.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.room.TypeConverter
import com.bumptech.glide.Glide
import com.example.news_application.R
import com.example.news_application.baseview.model.ErrorModel
import com.example.news_application.home.model.NewsModel
import com.example.news_application.network.ApiException
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

fun setResponse(liveData: MutableLiveData<ResponseHelper>, code: Int, value: Any) {
    liveData.value = ResponseHelper(code, value)
}



fun setErrorBody(errorBody: ResponseBody): ErrorModel {
    return GsonBuilder().registerTypeAdapter(
        ErrorModel::class.java,
        ErrorDeserializer()
    ).create().fromJson(errorBody.string(), ErrorModel::class.java)
}

class ErrorDeserializer : JsonDeserializer<ErrorModel?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ErrorModel {
        return Gson().fromJson(json, ErrorModel::class.java)
    }
}

fun <T> setRequest(
    liveData: MutableLiveData<ResponseHelper>,
    request: Response<T>,
    code_request: Int
) {
    when (request.code()) {
        ResponseHelper.OK, ResponseHelper.Created -> {
            setResponse(liveData, code_request, request.body()!!)
        }
        ResponseHelper.BadRequest -> {
            val error = setErrorBody(request.errorBody()!!)
            setResponse(liveData, ResponseHelper.BadRequest, error)
        }
        else -> {
            throw ApiException(request.code().toString())
        }
    }
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.GONE
}


@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView,urlString: String?){
      urlString?.let {
          Glide.with(imageView)
              .load(urlString)
              .placeholder(R.drawable.placeholder)
              .error(R.drawable.placeholder)
              .into(imageView)
      }
}



class DateUtl{
    fun converterDate(date: String?) : String{
        return if (date.isNullOrEmpty()) ""
        else   {
            val currentFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val datepare   = currentFormat.parse(date)
            val toFormat = SimpleDateFormat("MMM, dd, yyyy", Locale.getDefault())
            toFormat.format(datepare)
        }
    }
}


object SourceConverter{
    @TypeConverter
    @JvmStatic
    fun toSource(value: String?) :  NewsModel.ArticleModel.SourceModel{
        val modelType = object : TypeToken<NewsModel.ArticleModel.SourceModel>() {}.type
        return  Gson().fromJson(value,modelType)
    }


    @TypeConverter
    @JvmStatic
    fun fromSource(source: NewsModel.ArticleModel.SourceModel) : String{
        val gson = Gson()
        return gson.toJson(source)
    }
}


