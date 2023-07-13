package com.jjbaksa.jjbaksa.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.JjNoContentViewBinding

open class JjNoContentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: JjNoContentViewBinding

    private val typedArray = context.obtainStyledAttributes(attrs, R.styleable.JjNoContentView)
    private var title = typedArray.getString(R.styleable.JjNoContentView_title)
    private var content = typedArray.getString(R.styleable.JjNoContentView_content)

    init {
        initView()
    }

    private fun initView() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.jj_no_content_view,
            this,
            true
        )
        try {
            setTitle()
            setContent()
        } finally {
            typedArray.recycle()
        }
    }


    private fun setTitle() {
        binding.titleTextView.text = title
    }
    private fun setContent() {
        binding.contentTextView.text = content
    }


}