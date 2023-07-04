package com.jjbaksa.jjbaksa.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment<T : ViewDataBinding> : DialogFragment() {
    @get:LayoutRes
    abstract val layoutResId: Int

    private var _binding: T? = null
    val binding: T get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initState(view)
    }

    open fun initState(view: View) {
        initView(view)
        initEvent()
        subscribe()
        initData()
    }

    override fun dismiss() {
        super.dismiss()
        _binding?.unbind()
        _binding = null
    }

    abstract fun initView(view: View)
    abstract fun initEvent()
    abstract fun subscribe()
    abstract fun initData()

}