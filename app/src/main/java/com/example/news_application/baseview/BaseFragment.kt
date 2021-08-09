package com.example.news_application.baseview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<viewBinding : ViewBinding> : Fragment() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> viewBinding
    private var toolbar: Toolbar? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: viewBinding
        get() = _binding as viewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        setToolbar()
    }

    abstract fun setup()

    abstract fun setToolbar()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
