package com.example.news_application.network

import java.io.IOException


class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)
