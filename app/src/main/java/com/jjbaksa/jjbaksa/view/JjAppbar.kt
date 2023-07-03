package com.jjbaksa.jjbaksa.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.JjAppBarBinding

open class JjAppbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: JjAppBarBinding

    lateinit var onBackPressedListener: OnBackPressedListener

    private val typedArray = context.obtainStyledAttributes(attrs, R.styleable.JjAppbar)

    private var title = typedArray.getString(R.styleable.JjAppbar_title)
    private var titleSize = typedArray.getDimensionPixelSize(
        R.styleable.JjAppbar_titleSize, 18
    )

    init {
        initView()
        setOnClickBackButton()
    }

    private fun initView() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.jj_app_bar,
            this,
            true
        )
        binding.backImageView.setImageResource(R.drawable.ic_back)
        try {
            setTitle()
        } finally {
            typedArray.recycle()
        }
    }

    private fun setTitle() {
        binding.titleTextView.text = title
        binding.titleTextView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            titleSize.toFloat()
        )
        binding.titleTextView.setTextColor(
            typedArray.getColor(R.styleable.JjAppbar_titleTextColor, 0)
        )
    }

    private fun setOnClickBackButton() {
        binding.backImageView.setOnClickListener {
            onBackPressedListener.onClick(it)
        }
    }

    interface OnBackPressedListener {
        fun onClick(view: View)
    }

    inline fun setOnClickListener(crossinline onBack: (View) -> Unit) {
        this.onBackPressedListener = object : OnBackPressedListener {
            override fun onClick(view: View) {
                onBack(view)
            }
        }
    }
}
