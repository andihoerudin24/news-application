package com.example.news_application.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.news_application.R
import com.example.news_application.baseview.BaseActivity
import com.example.news_application.databinding.ActivityDetailBinding
import com.example.news_application.databinding.CustomToolbarBinding
import com.example.news_application.detail.viewmodel.DeteilViewModel
import com.example.news_application.home.model.NewsModel
import com.example.news_application.home.viewmodel.HomeModelFactory
import com.example.news_application.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module



class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    private lateinit var bindingToolbar : CustomToolbarBinding
    private lateinit var homeviewmodel: HomeViewModel

    private val detial by lazy {
        intent.getSerializableExtra("intent_detail") as NewsModel.ArticleModel
    }
    override val bindingInflater: (LayoutInflater) -> ActivityDetailBinding
        get() = ActivityDetailBinding::inflate

    override fun setup(savedInstanceState: Bundle?) {
        setupViewModel()
        initView()
    }

    private fun setupViewModel(){
        homeviewmodel =
            ViewModelProvider(this, HomeModelFactory())[HomeViewModel::class.java]
    }

    private fun initView(){
        bindingToolbar = binding.toolbar
        setSupportActionBar(bindingToolbar.container)
        supportActionBar!!.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }
        detial.let {
            homeviewmodel.find(it)
            val web = binding.webView
            web.loadUrl(it.url!!)
            web.webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.progressTop.visibility = View.GONE
                }
            }
            val settings = binding.webView.settings
            settings.javaScriptCanOpenWindowsAutomatically = false

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bookmark,menu)
        val menubookmark =  menu!!.findItem(R.id.action_bookmark)
        menubookmark.setOnMenuItemClickListener {
            homeviewmodel.bookmark(detial)
            menubookmark.setIcon(R.drawable.ic_check)
            true
        }
        homeviewmodel.isBookmark.observe(this,{
            if (it == 0) menubookmark.setIcon(R.drawable.ic_add)
            else menubookmark.setIcon(R.drawable.ic_check)
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


    override fun statusBarColor(): Int = 0

}