package com.example.news_application.home.view

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_application.R
import com.example.news_application.baseview.BaseFragment
import com.example.news_application.databinding.CustomToolbarBinding
import com.example.news_application.databinding.FragmentHomeBinding
import com.example.news_application.detail.DetailActivity
import com.example.news_application.home.adapter.CategoryAdapter
import com.example.news_application.home.adapter.NewsAdapter
import com.example.news_application.home.model.CategoryModel
import com.example.news_application.home.model.NewsModel
import com.example.news_application.home.viewmodel.HomeModelFactory
import com.example.news_application.home.viewmodel.HomeViewModel
import com.example.news_application.utils.*
import kotlin.math.ceil

class HomeFragment : BaseFragment<FragmentHomeBinding>(),NewsOnClickListener {

    private var page = 1
    private var total = 1
    private var categoryid: String? = null
    private var query: String? = null
    private var loadmore : Boolean? = false

    private lateinit var bindingToolbar : CustomToolbarBinding
    private lateinit var homeviewmodel: HomeViewModel
    private lateinit var newsAdapter: NewsAdapter
    //private var newsModel: List<NewsModel.ArticleModel>?=null

    companion object {
        const val GET_NEWS = 2
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    @RequiresApi(Build.VERSION_CODES.M)
    override fun setup() {
        //initExtra()
        setupViewModel()
        setupRecyclerView()
        getCategoryList()
        getResponse()
    }

//    private fun initExtra(){
//        binding.imageAlert.visibility = if (it)
//    }

    override fun setToolbar() {
        bindingToolbar = binding.toolbar
        bindingToolbar.title = GlobalVariable.HOMES
        bindingToolbar.container.inflateMenu(R.menu.menu_search)
        val menu = binding.toolbar.container.menu
        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                 getCategoryList()
                 return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                 newText?.let {
                    query = it
                 }
                return true
            }

        })
    }

    private fun setupViewModel(){
        homeviewmodel =
            ViewModelProvider(this, HomeModelFactory())[HomeViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.listCategory.adapter = categoryAdapterr
        homeviewmodel.category.observe(viewLifecycleOwner,{
            categoryid = it
            binding.scroll.scrollTo(0,0)
            getCategoryList()
        })
        //binding.listNews.adapter = newsAdapterr

        binding.scroll.setOnScrollChangeListener {
                v: NestedScrollView, _, scrollY, _, _ ->
            if (scrollY == v?.getChildAt(0)!!.measuredHeight - v?.measuredHeight){
                  Log.d("page",page.toString())
            }
        }

        binding.listNews.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = newsAdapter
        }
        newsAdapter.onClicklistener = this


//        homeviewmodel.news.observe(viewLifecycleOwner,{
//            newsAdapterr.populateData(it.articles)
//        })

    }

    private val categoryAdapterr by lazy {
        CategoryAdapter(homeviewmodel.categories, object : CategoryAdapter.OnAdapterListener {
            override fun onClick(category: CategoryModel) {
                 homeviewmodel.category.postValue( category.id )
            }
        })
    }

    private fun getCategoryList() {
        homeviewmodel.fetchCategori(GET_NEWS, categoryid, query, page)
    }

    private fun getResponse() {
        homeviewmodel.response.observe(this, {
            Log.d("progressTop",it.code.toString())
            when (it.code) {
                ResponseHelper.ERROR -> {
                    loadingDone()
                }
                ResponseHelper.LOADING -> {
                    val loading = it.response as Boolean
                    if (loading) loading() else loadingDone()
                }
                GET_NEWS -> {
                   val resp = it.response as NewsModel
//                   val totalResults: Double = resp.totalResults / 20.0
//                   total = ceil(totalResults).toInt()
//                   page ++
                   //newsModel = resp.articles
                   binding.imageAlert.visibility = if (resp.articles.isEmpty()) View.VISIBLE else View.GONE
                   binding.textAlert.visibility = if (resp.articles.isEmpty()) View.VISIBLE else View.GONE
                   newsAdapter.populateData(resp.articles)

                }
            }
        })
    }

    private fun loading() {
        binding.progressTop.visibility = View.INVISIBLE
        binding.progressTop.visibility = View.VISIBLE
    }

    private fun loadingDone() {
        binding.progressTop.visibility = View.VISIBLE
        binding.progressTop.visibility = View.GONE
    }

    override fun onClick(position: Int, buttonType: Int) {
        val item = newsAdapter.getItem(position)
        when (buttonType) {
            NewsAdapter.DETAIL -> {
                  startActivity(
                      Intent(requireActivity(),DetailActivity::class.java)
                          .putExtra("intent_detail",item)
                  )
            }
        }
    }

}