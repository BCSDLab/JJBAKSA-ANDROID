package com.jjbaksa.jjbaksa.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.ItemLoadingViewBinding

class JjLoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: ItemLoadingViewBinding
    private var loading: Boolean? = null

    init {
        attrs?.let {
            initAttr(it)
        }
        initView()
    }

    private fun initAttr(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.JjLoadingView,
            0, 0
        ).apply {
            loading = getBoolean(R.styleable.JjLoadingView_loading, false)
        }
    }

    private fun initView() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_loading_view,
            this,
            true
        )

        loading?.let {
            binding.loadingContainer.isVisible = it
        }
    }
}
