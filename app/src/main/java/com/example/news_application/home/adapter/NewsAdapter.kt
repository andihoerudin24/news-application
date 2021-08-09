package com.example.news_application.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news_application.databinding.AdapterNewsBinding
//import com.example.news_application.home.model.ArticleModel
import com.example.news_application.home.model.NewsModel
import com.example.news_application.utils.DateUtl
import com.example.news_application.utils.NewsOnClickListener


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>()  {

    private var data = ArrayList<NewsModel.ArticleModel>()
    lateinit var onClicklistener: NewsOnClickListener
    companion object {
        const val DETAIL = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            AdapterNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(data[position])
        holder.onClicks(onClicklistener)

    }

    override fun getItemCount(): Int = data.size

    fun getItem(position: Int): NewsModel.ArticleModel = data[position]

    fun populateData(dataModel: List<NewsModel.ArticleModel>) {
        data.clear()
        data.addAll(dataModel)
        notifyDataSetChanged()
        notifyItemRangeInserted((data.size - data.size),data.size)
    }

    fun clear(){
        data.clear()
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(private val adapterNewsBinding: AdapterNewsBinding) :
        RecyclerView.ViewHolder(adapterNewsBinding.root) {
        fun bind(newsModel: NewsModel.ArticleModel) {
//               adapterNewsBinding.title.text = newsModel.title
//               adapterNewsBinding.publishedAt.text = newsModel.publishedAt
                 adapterNewsBinding.article = newsModel
                 adapterNewsBinding.format = DateUtl()
        }
        fun onClicks(onClickListener: NewsOnClickListener){
            adapterNewsBinding.title.setOnClickListener {
                onClickListener.onClick(adapterPosition, DETAIL)
            }
        }


    }


}

