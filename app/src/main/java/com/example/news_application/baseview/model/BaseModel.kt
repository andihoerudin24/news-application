package com.example.news_application.baseview.model

import com.google.gson.annotations.SerializedName

open class BaseModel {
    @SerializedName("code")
    val code: Int? = null

    @SerializedName("error")
    var error: Any? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("success")
    val success: Boolean = false
}
