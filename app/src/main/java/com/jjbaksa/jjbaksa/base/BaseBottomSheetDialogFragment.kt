package com.jjbaksa.jjbaksa.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jjbaksa.jjbaksa.R

abstract class BaseBottomSheetDialogFragment<T : ViewDataBinding> : BottomSheetDialogFragment() {
    @get:LayoutRes
    abstract val layoutResId: Int
    private var _binding: T? = null
    val binding: T get() = _binding!!

    open var widthPercent = 1f
    open var heightPercent = 1f
    open var isDrag = false
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.TopRoundBottomDialog)
            .apply {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
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

    protected fun setLayoutSize(rootView: View) {
        context?.resources?.displayMetrics?.let { metrics ->
            val params = rootView.layoutParams
            params?.height = (metrics.heightPixels * heightPercent).toInt()
            params?.width = (metrics.widthPixels * widthPercent).toInt()
            rootView.layoutParams = params

            val baseDialog = dialog
            if (baseDialog is BottomSheetDialog) {
                val behavior: BottomSheetBehavior<*> = baseDialog.behavior
                params?.height?.let {
                    behavior.peekHeight = it
                    behavior.isDraggable = isDrag
                }
            }
        }
    }
    protected fun setLayoutMaxHeight(rootView: View) {
        context?.resources?.displayMetrics?.let { metrics ->
            val params = rootView.layoutParams
            params?.height = (metrics.heightPixels * heightPercent).toInt()
            params?.width = (metrics.widthPixels * widthPercent).toInt()
            rootView.layoutParams = params

            val baseDialog = dialog
            if (baseDialog is BottomSheetDialog) {
                val behavior: BottomSheetBehavior<*> = baseDialog.behavior
                params?.height?.let {
                    behavior.maxHeight = it
                    behavior.isDraggable = isDrag
                }
            }
        }
    }
}
