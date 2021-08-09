package com.example.news_application.baseview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toolbar
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<viewBinding : ViewBinding> : AppCompatActivity() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> viewBinding

    @Suppress("UNCHECKED_CAST")
    protected val binding: viewBinding
        get() = _binding as viewBinding

    abstract fun setup(savedInstanceState: Bundle?)

    protected abstract fun statusBarColor(): Int

    private var toolbar: Toolbar? = null

    private var subtitles: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        setup(savedInstanceState)

        if (statusBarColor() != 0) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (statusBarColor() == android.R.color.white && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = ContextCompat.getColor(this, statusBarColor())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.decorView.windowInsetsController?.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                } else {
                    @Suppress("DEPRECATION")
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        } else if (toolbar != null) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val toolbarColor = (toolbar?.background as ColorDrawable).color
            if (toolbarColor == -1 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.decorView.windowInsetsController?.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                } else {
                    @Suppress("DEPRECATION")
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                window.statusBarColor = toolbarColor
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setupToolbar(
        toolbarId: Int,
        isBackButtonEnable: Boolean,
        title: String,
        @ColorRes color: Int,
        elevation: Float
    ) {
        toolbar = findViewById<Toolbar>(toolbarId)?.apply {
            setNavigationOnClickListener { onBackPressed() }
            this.title = title
            if (subtitles != null) {
                this.subtitle = subtitles
            }
            this.elevation = elevation
            setBackgroundColor(ContextCompat.getColor(applicationContext, color))
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(isBackButtonEnable)
        }
    }

    private fun setSupportActionBar(toolbar: Toolbar) {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

