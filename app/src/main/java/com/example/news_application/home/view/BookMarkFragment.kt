package com.example.news_application.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.news_application.baseview.BaseFragment
import com.example.news_application.databinding.CustomToolbarBinding
import com.example.news_application.databinding.FragmentBookMarkBinding
import com.example.news_application.utils.GlobalVariable
import com.example.news_application.utils.NewsOnClickListener


class BookMarkFragment : BaseFragment<FragmentBookMarkBinding>(), NewsOnClickListener {

    private lateinit var bindingToolbar : CustomToolbarBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBookMarkBinding
        get() = FragmentBookMarkBinding::inflate

    override fun setup() {
    }

    override fun onClick(position: Int, buttonType: Int) {

    }

    override fun setToolbar() {
       bindingToolbar = binding.toolbar
       bindingToolbar.textTitle.text = GlobalVariable.BOOKMARK
    }


}