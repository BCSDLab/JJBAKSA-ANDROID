package com.jjbaksa.jjbaksa.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.jjbaksa.jjbaksa.dialog.LoadingDialog
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.view.JjAppbar

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    @get:LayoutRes
    abstract val layoutId: Int
    private var _binding: T? = null
    private var loadingDialog: LoadingDialog? = null
    val binding: T
        get() = _binding!!

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
        setOnBackPress()
        initState()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding?.unbind()
        _binding = null
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
        onBackPressedCallBack?.remove()
        onBackPressedCallBack = null
        super.onDetach()
    }

    fun setOnBackPress() {
        if (onBackPressedCallBack == null)
            return
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallBack!!
        )
    }

    fun showLoading() {
        loadingDialog = LoadingDialog()
        loadingDialog?.show(parentFragmentManager, LoadingDialog.TAG)
    }

    fun dismissLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
    fun showSnackBar(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).also {
            it.setAction(
                R.string.close,
                object : OnClickListener {
                    override fun onClick(v: View?) {
                        it.dismiss()
                    }
                }
            )
        }.show()
    }
    fun backPressed(backBtn: JjAppbar, context: FragmentActivity, pop: Boolean) {
        if (pop) {
            backBtn.setOnClickListener { findNavController().popBackStack() }
        } else {
            backBtn.setOnClickListener { context.finish() }
        }
    }
}
