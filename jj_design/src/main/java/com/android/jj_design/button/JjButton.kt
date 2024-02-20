package com.android.jj_design.button

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import com.android.jj_design.R
import com.android.jj_design.utils.px

class JjButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatButton(context, attributeSet, defStyleAttr) {
    init {
        initSetting()
        context.theme.obtainStyledAttributes(
            attributeSet, R.styleable.JjButton, defStyleAttr, 0
        ).apply {
            this@JjButton.let { btnAttr ->
                // text
                btnAttr.text = getString(R.styleable.JjButton_jjText)
                btnAttr.setTextColor(getColor(R.styleable.JjButton_jjTextColor, 0))

                // background
                btnAttr.setBackgroundResource(R.drawable.jj_button_selector)

                // elevation
                btnAttr.elevation = ELEVATION.px.toFloat()
            }
            recycle()
        }
    }

    private fun initSetting() {
        setTextAppearance(R.style.JJ_TextAppearance_Medium_16) // TextStyle
        minWidth = MIN_WIDTH.px
        gravity = Gravity.CENTER
        setPadding(0, VERTICAL_PADDING.px, 0, VERTICAL_PADDING.px)
    }

    fun setErrorBackground() {
        setTextColor(context.getColor(R.color.color_FF7F23))
        setBackgroundResource(R.drawable.jj_button_rect_solid_ffffff_stroke_ff7f23_radius_100)
        elevation = 0f
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        setBackgroundResource(R.drawable.jj_button_selector)
        setTextColor(context.getColor(R.color.color_white))
        elevation = ELEVATION.px.toFloat()
    }

    companion object {
        private const val MIN_WIDTH = 170
        private const val VERTICAL_PADDING = 10
        private const val ELEVATION = 10
    }
}
