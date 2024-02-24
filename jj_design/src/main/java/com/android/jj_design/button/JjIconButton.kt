package com.android.jj_design.button

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.android.jj_design.R
import com.android.jj_design.utils.px

class JjIconButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatButton(context, attributeSet, defStyleAttr) {
    init {
        initSetting()
        context.theme.obtainStyledAttributes(
            attributeSet, R.styleable.JjIconButton, 0, 0
        ).apply {
            this@JjIconButton.let { iconBtnAttr ->
                // text
                iconBtnAttr.text = getString(R.styleable.JjIconButton_jjIconText)
                iconBtnAttr.setTextColor(getColor(R.styleable.JjIconButton_jjIconTextColor, 0))

                // icon
                iconBtnAttr.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    getDrawable(R.styleable.JjIconButton_jjIconDrawableStart), null, null, null
                )
                iconBtnAttr.compoundDrawablePadding = DRAWABLE_PADDING.px

                // background
                iconBtnAttr.setBackgroundResource(R.drawable.jj_button_rect_solid_ffffff_stroke_c4c4c4_radius_100)

                // elevation
                iconBtnAttr.elevation = ELEVATION.px.toFloat()
            }
            recycle()
        }
    }

    private fun initSetting() {
        setPadding(
            HORIZONTAL_PADDING.px,
            VERTICAL_PADDING.px,
            HORIZONTAL_PADDING.px,
            VERTICAL_PADDING.px
        )
        setTextAppearance(R.style.JJ_TextAppearance_Medium_14)
    }

    companion object {
        private const val HORIZONTAL_PADDING = 28
        private const val VERTICAL_PADDING = 10
        private const val ELEVATION = 10
        private const val DRAWABLE_PADDING = 8
    }
}