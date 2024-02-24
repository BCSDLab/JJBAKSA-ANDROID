package com.android.jj_design.button

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.android.jj_design.R
import com.android.jj_design.utils.px

class JjTrendButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatButton(context, attributeSet, defStyleAttr) {

    init {
        initSetting()
    }

    private fun initSetting() {
        setTextAppearance(R.style.JJ_TextAppearance_Medium_12)
        setPadding(
            HORIZONTAL_PADDING.px,
            VERTICAL_PADDING.px,
            HORIZONTAL_PADDING.px,
            VERTICAL_PADDING.px
        )
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (enabled) {
            setTextColor(context.getColor(R.color.color_FF7F23))
            setBackgroundResource(R.drawable.jj_button_rect_solid_20f6bf54_stroke_ff7f23_radius_100)
        } else {
            setTextColor(context.getColor(R.color.color_C4C4C4))
            setBackgroundResource(R.drawable.jj_button_rect_solid_ffffff_stroke_c4c4c4_radius_100)
        }
    }

    companion object {
        private const val HORIZONTAL_PADDING = 8
        private const val VERTICAL_PADDING = 6
    }
}
