package com.jjbaksa.jjbaksa.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.JjCheckBoxBinding

open class JjCheckBox constructor(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    lateinit var onClickListener: OnClickListener

    private lateinit var binding: JjCheckBoxBinding

    private val typedArray = context.obtainStyledAttributes(attrs, R.styleable.JjCheckBox)
    private var title = typedArray.getString(R.styleable.JjCheckBox_title)
    private var moreInfo = typedArray.getString(R.styleable.JjCheckBox_moreInfo)

    private var checkBoxTextSize = typedArray.getDimensionPixelSize(
        R.styleable.JjCheckBox_checkBox_textSize, 14
    )

    private var hasMoreInfo = typedArray.getBoolean(R.styleable.JjCheckBox_has_moreInfo, false)

    private var moreInfoTextSize = typedArray.getDimensionPixelSize(
        R.styleable.JjCheckBox_moreInfo_textSize, 11
    )

    private var isMoreInfoOpened = false

    var isChecked: Boolean
        get() = binding.checkBoxJjCheckBox.isChecked
        set(value) {
            binding.checkBoxJjCheckBox.isChecked = value
        }

    init {
        initView()
        setTitle()
        setOnClickListener()
    }

    private fun initView() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.jj_check_box,
            this,
            true
        )

        val scale = binding.checkBoxJjCheckBox.resources.displayMetrics.density

        binding.checkBoxJjCheckBox.setPadding(
            paddingLeft + (8.0f * scale + 0.5f).toInt(),
            paddingTop,
            paddingRight,
            paddingBottom
        )

        binding.checkBoxJjCheckBox.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            checkBoxTextSize.toFloat()
        )

        binding.textViewJjCheckBoxMoreInfo.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            moreInfoTextSize.toFloat()
        )

        if (hasMoreInfo) {
            enableMoreInfo()
        }
    }

    private fun enableMoreInfo() {
        setMoreInfo()

        binding.imageButtonJjCheckBoxMoreInfo.visibility = View.VISIBLE

        binding.imageButtonJjCheckBoxMoreInfo.setOnClickListener {
            openCloseMoreInfo()
        }
        binding.constraintLayoutBaseTermCheckBox.setOnClickListener {
            openCloseMoreInfo()
        }
        binding.textViewJjCheckBoxMoreInfo.setOnClickListener {
        }
    }

    private fun setTitle() {
        binding.checkBoxJjCheckBox.text = title
    }

    private fun setMoreInfo() {
        binding.textViewJjCheckBoxMoreInfo.text = moreInfo
    }

    private fun setOnClickListener() {
        binding.checkBoxJjCheckBox.setOnClickListener {
            onClickListener.onClick(it)
        }
    }

    private fun openCloseMoreInfo() {
        isMoreInfoOpened = !isMoreInfoOpened

        binding.textViewJjCheckBoxMoreInfo.visibility = if (isMoreInfoOpened) {
            View.VISIBLE
        } else {
            View.GONE
        }

        val moreInfoDrawable = if (isMoreInfoOpened) {
            R.drawable.sel_jj_check_box_more_info_opened
        } else {
            R.drawable.sel_jj_check_box_more_info_closed
        }

        binding.imageButtonJjCheckBoxMoreInfo.setImageResource(moreInfoDrawable)
    }

    interface OnClickListener {
        fun onClick(view: View)
    }

    inline fun setOnClickListener(crossinline onClick: (View) -> Unit) {
        this.onClickListener = object : OnClickListener {
            override fun onClick(view: View) {
                onClick(view)
            }
        }
    }
}
