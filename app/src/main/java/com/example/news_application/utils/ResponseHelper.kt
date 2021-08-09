package com.example.news_application.utils

data class ResponseHelper(val code: Int, val response: Any) {
    companion object {
        const val ERROR = 0
        const val LOADING = -1

        const val OK = 200
        const val Created = 201
        const val BadRequest = 400
        const val Unauthorized = 401
        const val Forbidden = 403
        const val NotFound = 404
        const val Conflict = 409
    }
}
