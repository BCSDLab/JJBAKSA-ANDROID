package com.jjbaksa.jjbaksa.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.view.JjAppbar

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    @get:LayoutRes
    abstract val layoutId: Int
    private lateinit var _binding: T
    val binding: T
        get() = _binding

    protected var onBackPressedCallBack: OnBackPressedCallback? = null
    protected var backPressedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initState()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding.unbind()
    }

    open fun initState() {
        initView()
        initEvent()
        subscribe()
    }

    abstract fun initView()
    abstract fun initEvent()
    abstract fun subscribe()

    override fun onDetach() {
        super.onDetach()
    }

    fun showSnackBar(context: Context, msg: String) {
        Snackbar.make(context, binding.root, msg, Snackbar.LENGTH_SHORT).also {
            it.setAction(R.string.close, object: OnClickListener {
                override fun onClick(v: View?) {
                    it.dismiss()
                }
            })
        }.show()
    }

    fun backPressed(backBtn: JjAppbar, context:FragmentActivity, pop: Boolean){
        if (pop) {
            backBtn.setOnClickListener { findNavController().popBackStack() }
        } else {
            backBtn.setOnClickListener { context.finish() }
        }
    }
}
