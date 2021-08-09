package com.example.news_application.baseview

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news_application.baseview.model.BaseAdapterModel
import com.example.news_application.baseview.model.LoadingAdapterModel

abstract class BaseRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected var data: MutableList<BaseAdapterModel> = ArrayList()

    private var lastVisibleItem: Int? = null
    private var totalItemCount: Int? = null
    private var totalItemBeforeLoadMore: Int? = null

    private var isLoadingMore = false
    private var isDataEnd = false

    private var page = 1

    private var swipeRefresh = false

    fun addSupportLoadMore(
        recyclerView: RecyclerView,
        visibleTreshold: Int,
        onLoadMore: (page: Int) -> Unit
    ) {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager != null) {
            if (layoutManager is LinearLayoutManager) {
                totalItemBeforeLoadMore = layoutManager.itemCount
                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        totalItemCount = layoutManager.itemCount
                        lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                        if (!swipeRefresh && !isDataEnd && !isLoadingMore && totalItemCount!! <= (lastVisibleItem!! + visibleTreshold)) {
                            page++
                            onLoadMore(page)
                            isLoadingMore = true
                        }
                    }
                })
            } else {
                Log.d("BASE_ADAPTER", "Only support LinearLayoutManager")
            }
        } else {
            Log.d("BASE_ADAPTER", "No LayoutManager found")
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun get(position: Int): BaseAdapterModel {
        return data[position]
    }

    fun getPosition(item: BaseAdapterModel): Int {
        return data.indexOf(item)
    }

    open fun setDatas(items: List<BaseAdapterModel>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun addData(items: List<BaseAdapterModel>) {
        data.addAll(items)
        notifyItemRangeInserted(itemCount - 1, items.size)
    }

    open fun addItem(item: BaseAdapterModel) {
        data.add(item)
        notifyItemInserted(itemCount - 1)
    }

    fun addItem(item: BaseAdapterModel, position: Int) {
        data.add(position, item)
        notifyItemInserted(position)
    }

    fun changeItem(item: BaseAdapterModel, position: Int) {
        data.removeAt(position)
        data.add(position, item)
        notifyItemChanged(position)
    }

    fun deleteItem(position: Int) {
        if (position < itemCount) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun deleteItem(item: BaseAdapterModel) {
        data.remove(item)
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun setLoading() {
        isLoadingMore = true
        addItem(LoadingAdapterModel())
    }

    fun setLoaded() {
        if (isLoadingMore) {
            isLoadingMore = false
            if (itemCount > 0) {
                if (data[itemCount - 1] is LoadingAdapterModel) deleteItem(itemCount - 1)
            }
        }
    }

    fun setDataEnd(isDataEnd: Boolean) {
        if (!isDataEnd) {
            page = 1
        }
        this.isDataEnd = isDataEnd
    }

    fun isDataEnd(): Boolean {
        return isDataEnd
    }

    fun swipeRefresh(swipe: Boolean) {
        swipeRefresh = swipe
    }

    fun loadMore(loadMore: Boolean) {
        isLoadingMore = loadMore
    }

    fun getLoadMore(): Boolean {
        return isLoadingMore
    }

    fun getListData(): MutableList<BaseAdapterModel> {
        return data
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].getType()
    }
}