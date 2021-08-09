package com.example.news_application.network

object Api {
    const val BASE_URL = "https://newsapi.org/v2/"

    /** Api key */
    const val API_KEY = "2094e9cb485e4145b44c2b9fc7c82620"

    /** TOP HEADLINE */
    const val  TOP_HEADLINE = BASE_URL + "top-headlines"

    /** Header */
    /** content types */
    const val CONTENT_TYPE = "Content-Type: application/vnd.api+json"
    const val CONTENT_TYPE_IMAGE = "Content-Type: image/jpeg"
    const val ACCEPT = "Accept: application/vnd.api+json"


}