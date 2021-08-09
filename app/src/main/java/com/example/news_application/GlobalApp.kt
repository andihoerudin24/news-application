package com.example.news_application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.news_application.utils.DateUtl

class GlobalApp : Application() {
    companion object {
        private lateinit var mAppContext: Context

        @get:Synchronized
        lateinit var instance: GlobalApp

        private fun setAppContext(ctx: Context) {
            mAppContext = ctx
        }

        fun getAppContext(): Context {
            return mAppContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        setAppContext(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        instance = this
    }

    fun log(message: String) {
        DateUtl()
        Log.d(GlobalApp::class.java.name, message)
    }


}
