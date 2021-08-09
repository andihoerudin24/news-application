package com.example.news_application.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news_application.R
import com.example.news_application.databinding.AdapterCategoryBinding
import com.example.news_application.home.model.CategoryModel
import com.example.news_application.utils.NewsOnClickListener

class CategoryAdapter(
    var categories: List<CategoryModel>,
    var listener: OnAdapterListener,
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val items = arrayListOf<TextView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        AdapterCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    class ViewHolder(val binding: AdapterCategoryBinding): RecyclerView.ViewHolder(binding.root)


    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        Log.d("adaptercategori",category.toString())
        holder.binding.category.text = category.name
        items.add( holder.binding.category )
        holder.itemView.setOnClickListener {
            listener.onClick( category )
            setColor( holder.binding.category )
        }
        setColor(items[0]) /// first load
    }

    interface OnAdapterListener {
        fun onClick(category: CategoryModel)
    }

    private fun setColor(textView: TextView){
        items.forEach {
            it.setBackgroundResource(R.color.white)
        }
        textView.setBackgroundResource(android.R.color.darker_gray)
    }

}